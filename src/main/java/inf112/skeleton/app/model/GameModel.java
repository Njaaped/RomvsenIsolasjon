package inf112.skeleton.app.model;

import inf112.skeleton.app.event.GlobalEventBus;
import inf112.skeleton.app.event.IEventBus;
import inf112.skeleton.app.model.audio.MusicHandler;
import inf112.skeleton.app.model.entity.powerup.ILootDropper;
import inf112.skeleton.app.model.entity.powerup.LootDropper;
import inf112.skeleton.app.model.mapfactories.DefaultTileBodyCreator;
import inf112.skeleton.app.model.mapfactories.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import inf112.skeleton.app.controller.ControllableModel;
import inf112.skeleton.app.model.entity.alien.AlienBomber;
import inf112.skeleton.app.model.entity.alien.AlienOne;
import inf112.skeleton.app.model.entity.alien.AlienTwo;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.DeferredEntityFactory;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.model.mapfactories.MapFactory;
import inf112.skeleton.app.model.mapfactories.Tile;
import inf112.skeleton.app.model.mapfactories.TileType;
import inf112.skeleton.app.model.entity.powerup.*;


import inf112.skeleton.app.model.audio.SoundEffectHandler;
import inf112.skeleton.app.model.box2d.Box2DWorldStatic;
import inf112.skeleton.app.model.gamestate.GameState;
import inf112.skeleton.app.model.gamestate.LevelManager;
import inf112.skeleton.app.model.score.ScoreEvent;
import inf112.skeleton.app.model.score.ScoreManager;
import inf112.skeleton.app.view.Media;
import inf112.skeleton.app.view.ViewableModel;
import inf112.skeleton.app.view.screens.ScreenManager;

import java.util.Random;

public class GameModel implements ControllableModel, ViewableModel {
    MapFactory mapFactory;
    Map map;
    Player player;
    LevelManager levelManager;
    private boolean debugging;
    private Random rand;
    private ILootDropper lootDropper;

    public static GameState state;
    public ScoreManager scoreManager;
    public SoundEffectHandler soundEffectHandler;
    public MusicHandler musicHandler;

    public GameModel(MapFactory mapFactory){
        Media.load_assets();
        Box2DWorldStatic.startBox2DWorld();
        levelManager = new LevelManager();
        this.mapFactory = mapFactory;
        this.rand = new Random(mapFactory.getSeed());
        this.map = mapFactory.generate(levelManager.getMapWidth(), levelManager.getMapHeight(), levelManager.getDifficulty());
        Box2DWorldStatic.loadMap(map, new DefaultTileBodyCreator());
        initializePlayerAndAlien();
        GameModel.state = GameState.MAIN_MENU;
        this.scoreManager = new ScoreManager();
        this.soundEffectHandler = new SoundEffectHandler();
        this.lootDropper = new LootDropper();
        this.musicHandler = new MusicHandler();
    }

    private void initializePlayerAndAlien() {
        int[] playerPos = map.getSpawnPoint();
        this.player = new Player(new Vector2(playerPos[1], playerPos[0]));
        for (int row = 0; row < map.getHeight(); row++) {
            for (int col = 0; col < map.getWidth(); col++) {
                if (map.getTile(row, col).getTiletype() == TileType.ENEMY_SPAWN) {
                    int random = rand.nextInt(3);
                    if (random == 0) {
                        AlienOne alien = new AlienOne(new Vector2(col, row));
                    } else if (random == 2) {
                        AlienTwo alien = new AlienTwo(new Vector2(col, row));
                    } else {
                        AlienBomber alien = new AlienBomber(new Vector2(col, row), 49);
                    }
                }
                if (map.getTile(row, col).getTiletype() == TileType.POWERUP_SPAWN) {
                    int random = rand.nextInt(3);
                    switch (random) {
                        case 0:
                            DeferredEntityFactory.getInstance().createEntity("Star", new Vector2(col, row));
                            break;
                        case 1:
                            DeferredEntityFactory.getInstance().createEntity("HealthPacket", new Vector2(col, row));
                            break;
                        case 2:
                            DeferredEntityFactory.getInstance().createEntity("RayGunPickup", new Vector2(col, row));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    /**
     * Level up the game
     */
    
     private void levelup() {
        levelManager.nextLevel();
        ScreenManager.getInstance().showLevelUpScreen(levelManager.getCurrentLevel());
        Box2DWorldStatic.startBox2DWorld();
        this.map = mapFactory.generate(levelManager.getMapWidth(), levelManager.getMapHeight(), levelManager.getDifficulty());
        Box2DWorldStatic.loadMap(map, new DefaultTileBodyCreator());
        initializePlayerAndAlien();
        GameModel.state = GameState.PAUSED;
        IEventBus bus = GlobalEventBus.getInstance(1);
        bus.trigger("LevelCompleted", new ScoreEvent(this, 100));
    }

    /**
     * !!!!!!! Underlying methods are for ViewableModel !!!!!!!
     * |
     * |
     * |
     * |
     * V
     */

    @Override
    public Array<Entity> getEntities() {
        /*ArrayList<Entity> a = new ArrayList<>();
        a.add(player);
        return a;*/
        Array<Body> bodies = new Array<>();
        Box2DWorldStatic.world.getBodies(bodies);
        Array<Entity> entities = new Array<>();
        for (Body b : bodies) {
            if (b.getUserData() instanceof Entity e) {
                entities.add(e);
            }
        }
        return entities;
    }



    @Override
    public Array<Array<Tile>> getTiles() {
        return map.getTiles();
    }

    @Override
    public Map getMap() {
        return map;

    }

    @Override
    public Vector2 getPlayerPosition() {
        return player.getPosition();
    }

    @Override
    public float getPlayerHealthPercentage(){
        return this.player.getHealthPercentage();
    }

    @Override
    public void drawHitboxes(OrthographicCamera camera) {
        if (debugging) {
            Box2DWorldStatic.drawHitboxes(camera);
        }
    }

    /**
     * !!!!!!! Underlying methods are for Controllable model !!!!!!!
     * |
     * |
     * |
     * |
     * V
     */

    @Override
    public void moveDirection(int x, int y) {

        Vector2 direction = new Vector2(x, y);
        // Update the player's movement direction based on the current input states.
        player.setMovementDirection(direction);
    }

    @Override
    public void attackDirection(int x, int y) {
        Vector2 attackVector = new Vector2(x, y);
        player.setAttacking(!attackVector.equals(Vector2.Zero), attackVector);

    }

    
    @Override
    public void isSprinting(boolean sprinting) {
        player.setSprintState(sprinting);
    }


    @Override
    public void update(float deltaTime) {
        if (GameModel.state == GameState.RUNNING) {
            Box2DWorldStatic.stepWorld(deltaTime);
            if (LevelManager.isLevelUp(player, map, map.getSpawnPoint()[1], map.getSpawnPoint()[0])) {
                levelup();
            }
            if(player.getHealthPercentage() <= 0){
                gameOver();
            }
        }
    }

    @Override
    public void toggleDebug() {
        debugging = !debugging;
    }

    @Override
    public void setAttacking(boolean b) {
    }

    @Override
    public void pauseGame() {
        if (GameModel.state == GameState.RUNNING) {
            GameModel.state = GameState.PAUSED;
        } else {
            GameModel.state = GameState.RUNNING;
        }
    }

    @Override
    public void gameOver() {
        GameModel.state = GameState.GAME_OVER;
    }

    @Override
    public void restartGame() {
        Media.load_assets();
        Box2DWorldStatic.startBox2DWorld();
        this.levelManager = new LevelManager();
        mapFactory.setSeed(new Random().nextInt());
        this.map = mapFactory.generate(levelManager.getMapWidth(), levelManager.getMapHeight(), levelManager.getDifficulty());
        Box2DWorldStatic.loadMap(map, new DefaultTileBodyCreator());
        initializePlayerAndAlien();
        GameModel.state = GameState.RUNNING;
        scoreManager.resetScore();
    }

    @Override
    public void stopGame() {
        restartGame();
        GameModel.state = GameState.MAIN_MENU;
        System.out.println("Game stopped");
    }

    @Override
    public int getScore() {
        return scoreManager.getScore();
    }

}

