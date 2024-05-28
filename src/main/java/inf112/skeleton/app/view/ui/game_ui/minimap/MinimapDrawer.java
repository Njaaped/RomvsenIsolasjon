package inf112.skeleton.app.view.ui.game_ui.minimap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import inf112.skeleton.app.model.entity.alien.Alien;
import inf112.skeleton.app.model.entity.player.Player;
import inf112.skeleton.app.model.entity.powerup.PowerUp;
import inf112.skeleton.app.model.entity.Entity;
import inf112.skeleton.app.view.Media;
import inf112.skeleton.app.model.mapfactories.Map;
import inf112.skeleton.app.model.mapfactories.Tile;
import inf112.skeleton.app.model.mapfactories.TileType;


/**
 * This class is responsible for drawing a minimap representation of the game map.
 * It uses various textures to represent different elements such as walls, floors, holes, and entities.
 */
public class MinimapDrawer {

    private final Array<Array<Tile>> map;
    private final int width;
    private final int height;

    private final Texture blackTexture;
    private final Texture redTexture;
    private final Texture grayTexture;
    private final Texture greenTexture;

    private final Texture wallTexture;
    private final Texture holeTexture;

    /**
     * Constructs a new MinimapDrawer with the specified map.
     *
     * @param m the Map instance to draw the minimap for
     */
    public MinimapDrawer(Map m) {
        this.map = m.getTiles();
        this.width = m.getWidth();
        this.height = m.getHeight();
        this.blackTexture = Media.getTexture("black");
        this.redTexture = Media.getTexture("red");
        this.grayTexture = Media.getTexture("gray");
        this.greenTexture = Media.getTexture("green");
        this.wallTexture = Media.getTexture("wallTop");
        this.holeTexture = Media.getTexture("hole");
    }

    /**
     * Draws the minimap on the screen using the provided SpriteBatch, camera settings, player position, and entity list.
     * The minimap includes a representation of the map, player, and enemies.
     *
     * @param batch the SpriteBatch used for drawing the textures
     * @param camera the OrthographicCamera that provides the current view settings
     * @param playerpos the current position of the player in world coordinates, used as the center point of the minimap
     * @param entites the Array of Entity instances currently active in the game
     * @param size the size of the minimap in tiles
     */
    public void draw(SpriteBatch batch, OrthographicCamera camera, Vector2 playerpos, Array<Entity> entites, int size) {

        float tileSize = 2f / size;

        float minimapX = camera.position.x + camera.zoom * camera.viewportWidth / 2f - size * tileSize;
        float minimapY = camera.position.y + camera.zoom * camera.viewportHeight / 2f - size * tileSize;

        int px = (int) playerpos.x;
        int py = (int) playerpos.y;

        float pxReal = camera.position.x;
        float pyReal = camera.position.y;

        minimapX = minimapX + (px - pxReal) * tileSize;
        minimapY = minimapY + (py - pyReal) * tileSize;

        int newsize = (int) (double) (size >> 1);

        for (int row = py - newsize ; row < py + newsize; row++) {
            for (int col = px - newsize; col < px + newsize; col++) {
                int newRow = row - py + newsize;
                int newCol = col - px + newsize;
                if (newRow < 0 || newRow >= size || newCol < 0 || newCol >= size) {
                    continue;
                }
                if (col < 0 || row < 0 || col >= width || row >= height) {
                    continue;
                }
                TextureRegion textureRegion = new TextureRegion(getTileTexture(map.get(row).get(col).getTiletype()));
          
                batch.draw(textureRegion, minimapX + newCol * tileSize, minimapY + newRow * tileSize, tileSize, tileSize);
            
            }
        }

        for (Entity e : entites) {
            float row =  e.getPosition().y;
            float col =  e.getPosition().x;
            if (col - px + newsize < 0 || row - py + newsize < 0 || col - px + newsize >= size || row - py + newsize >= size) {
                continue;
            }
            if (e instanceof Alien) {
                TextureRegion textureRegion = new TextureRegion(Media.redDot);
                batch.draw(textureRegion, minimapX + (col - px + newsize) * tileSize - tileSize / 2f, minimapY + (row - py + newsize) * tileSize - tileSize / 2f, tileSize, tileSize);
            } else if (e instanceof Player) {
                TextureRegion textureRegion = new TextureRegion(Media.greenDot);
                batch.draw(textureRegion, minimapX + (col - px + newsize) * tileSize - tileSize / 2f, minimapY + (row - py + newsize) * tileSize - tileSize / 2f, tileSize, tileSize);
            } else if (e instanceof PowerUp) {
                TextureRegion textureRegion = new TextureRegion(e.getPrimaryTexture());
                batch.draw(textureRegion, minimapX + (col - px + newsize) * tileSize - tileSize / 2f, minimapY + (row - py + newsize) * tileSize - tileSize / 2f, tileSize, tileSize);
            }
        }
    }

    /**
     * Returns the texture associated with the specified TileType.
     *
     * @param type the TileType to get the texture for
     * @return the Texture associated with the TileType
     */

    private Texture getTileTexture(TileType type) {
        return switch (type) {
            case FLOOR, ENEMY_SPAWN, POWERUP_SPAWN -> grayTexture;
            case PLAYER_SPAWN -> greenTexture;
            case WALL -> wallTexture;
            case HOLE -> holeTexture;
            default -> blackTexture;
        };
    }

    /**
     * Disposes of the textures used by the minimap drawer.
     */
    public void dispose() {
        blackTexture.dispose();
        redTexture.dispose();
        grayTexture.dispose();
    }
}
