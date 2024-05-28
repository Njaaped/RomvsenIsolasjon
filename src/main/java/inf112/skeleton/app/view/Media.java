package inf112.skeleton.app.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Media {
    public static Texture player;

    public static Texture ground;
    public static Texture space1, space2, space3, space4, space5;
    public static Texture wallTop, wallTop2, wallTop3, wallBottom, wallLeft, wallRight;
    public static Texture wallTopLeftInside, wallTopRightInside, wallBottomLeftInside, wallBottomRightInside;
    public static Texture wallTopLeftOutside, wallTopRightOutside, wallBottomLeftOutside, wallBottomRightOutside;
    public static Texture wallVertical;
    public static Texture alien, alienTwo;
    public static Texture bullet;
    public static Texture black, red, gray, green, brightGreen;
    public static Texture redPlayer, orangePlayer, yellowPlayer, greenPlayer, bluePlayer, indigoPlayer, purplePlayer, star, healthPacket, raygun;
    public static Texture alienBomber1, alienBomber2, alienBomber3, alienBomber4, explosion;
    public static Texture redDot, greenDot;
    public static Sound laser1, laser2, laser3, laser4, laser5, laser6, laser7, laser8, laser9;
    public static Sound punch1, punch2, punch3, punch4;
    public static Sound explode;
    public static Music loadingMusic, battleMusic;
    private static Texture startBackground, buttonOffStart, buttonOnStart, buttonOffExit, buttonOnExit;

    public static void load_assets() {
        player = new Texture(Gdx.files.internal("images/player.png"));

        ground = new Texture(Gdx.files.internal("images/floor_tile.png"));

        space1 = new Texture(Gdx.files.internal("images/black.png"));
        space2 = new Texture(Gdx.files.internal("images/black.png"));
        space3 = new Texture(Gdx.files.internal("images/black.png"));
        space4 = new Texture(Gdx.files.internal("images/black.png"));
        space5 = new Texture(Gdx.files.internal("images/black.png"));

        // Wall textures retrieved from https://opengameart.org/content/warped-top-down-tech-lab

        wallTop = new Texture(Gdx.files.internal("images/wall_tiles/simple_walls/wall_tile_north_1.png"));
        wallTop2 = new Texture(Gdx.files.internal("images/wall_tiles/simple_walls/wall_tile_north_2.png"));
        wallTop3 = new Texture(Gdx.files.internal("images/wall_tiles/simple_walls/wall_tile_north_3.png"));
        wallBottom = new Texture(Gdx.files.internal("images/wall_tiles/simple_walls/wall_tile_south.png"));
        wallLeft = new Texture(Gdx.files.internal("images/wall_tiles/simple_walls/wall_tile_east.png"));
        wallRight = new Texture(Gdx.files.internal("images/wall_tiles/simple_walls/wall_tile_west.png"));

        wallTopLeftInside = new Texture(Gdx.files.internal("images/wall_tiles/inside_corners/wall_tile_NW_inside.png"));
        wallTopRightInside = new Texture(Gdx.files.internal("images/wall_tiles/inside_corners/wall_tile_NE_inside.png"));
        wallBottomLeftInside = new Texture(Gdx.files.internal("images/wall_tiles/inside_corners/wall_tile_SW_inside.png"));
        wallBottomRightInside = new Texture(Gdx.files.internal("images/wall_tiles/inside_corners/wall_tile_SE_inside.png"));

        wallTopLeftOutside = new Texture(Gdx.files.internal("images/wall_tiles/outside_corners/wall_tile_NW_outside.png"));
        wallTopRightOutside = new Texture(Gdx.files.internal("images/wall_tiles/outside_corners/wall_tile_NE_outside.png"));
        wallBottomLeftOutside = new Texture(Gdx.files.internal("images/wall_tiles/outside_corners/wall_tile_SW_outside.png"));
        wallBottomRightOutside = new Texture(Gdx.files.internal("images/wall_tiles/outside_corners/wall_tile_SE_outside.png"));

        wallVertical = new Texture(Gdx.files.internal("images/wall_tiles/vertical_wall/wall_tile_vertical.png"));

        alien = new Texture(Gdx.files.internal("images/alien.png"));
        alienTwo = new Texture(Gdx.files.internal("images/alienTwo.png"));
        bullet = new Texture(Gdx.files.internal("images/bullet.png"));

        black = new Texture(Gdx.files.internal("images/black.png"));
        red = new Texture(Gdx.files.internal("images/red.png"));
        gray = new Texture(Gdx.files.internal("images/gray.png"));
        green = new Texture(Gdx.files.internal("images/green.png"));
        brightGreen = new Texture(Gdx.files.internal("images/bright_green.png"));

        startBackground = new Texture(Gdx.files.internal("images/start_screen_bg.png"));
        buttonOffStart = new Texture(Gdx.files.internal("images/buttons/button_off_start.png"));
        buttonOnStart = new Texture(Gdx.files.internal("images/buttons/button_on_start.png"));
        buttonOffExit = new Texture(Gdx.files.internal("images/buttons/button_off_exit.png"));
        buttonOnExit = new Texture(Gdx.files.internal("images/buttons/button_on_exit.png"));

        redDot = new Texture(Gdx.files.internal("images/redDot.png"));
        greenDot = new Texture(Gdx.files.internal("images/greenDot.png"));

        redPlayer = new Texture(Gdx.files.internal("images/powerup/redPlayer.png"));
        orangePlayer = new Texture(Gdx.files.internal("images/powerup/orangePlayer.png"));
        yellowPlayer = new Texture(Gdx.files.internal("images/powerup/yellowPlayer.png"));
        greenPlayer = new Texture(Gdx.files.internal("images/powerup/greenPlayer.png"));
        bluePlayer = new Texture(Gdx.files.internal("images/powerup/bluePlayer.png"));
        indigoPlayer = new Texture(Gdx.files.internal("images/powerup/indigoPlayer.png"));
        purplePlayer = new Texture(Gdx.files.internal("images/powerup/purplePlayer.png"));
        star = new Texture(Gdx.files.internal("images/powerup/star.png"));
        healthPacket = new Texture(Gdx.files.internal("images/powerup/HealthPacket.png"));
        raygun = new Texture(Gdx.files.internal("images/powerup/raygun.png"));

        alienBomber1 = new Texture(Gdx.files.internal("images/alienBomber/alienBomber.png"));
        alienBomber2 = new Texture(Gdx.files.internal("images/alienBomber/alienBomber1.png"));
        alienBomber3 = new Texture(Gdx.files.internal("images/alienBomber/alienBomber2.png"));
        alienBomber4 = new Texture(Gdx.files.internal("images/alienBomber/alienBomber3.png"));
        explosion = new Texture(Gdx.files.internal("images/alienBomber/explosion.png"));

        laser1 = Gdx.audio.newSound(Gdx.files.internal("audio/laser1.mp3"));
        laser2 = Gdx.audio.newSound(Gdx.files.internal("audio/laser2.mp3"));
        laser3 = Gdx.audio.newSound(Gdx.files.internal("audio/laser3.mp3"));
        laser4 = Gdx.audio.newSound(Gdx.files.internal("audio/laser4.mp3"));
        laser5 = Gdx.audio.newSound(Gdx.files.internal("audio/laser5.mp3"));
        laser6 = Gdx.audio.newSound(Gdx.files.internal("audio/laser6.mp3"));
        laser7 = Gdx.audio.newSound(Gdx.files.internal("audio/laser7.mp3"));
        laser8 = Gdx.audio.newSound(Gdx.files.internal("audio/laser8.mp3"));
        laser9 = Gdx.audio.newSound(Gdx.files.internal("audio/laser9.mp3"));

        punch1 = Gdx.audio.newSound(Gdx.files.internal("audio/hit01.ogg"));
        punch2 = Gdx.audio.newSound(Gdx.files.internal("audio/hit02.ogg"));
        punch3 = Gdx.audio.newSound(Gdx.files.internal("audio/hit03.ogg"));
        punch4 = Gdx.audio.newSound(Gdx.files.internal("audio/hit04.ogg"));

        explode = Gdx.audio.newSound(Gdx.files.internal("audio/Chunky Explosion.mp3"));

        loadingMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/loading.wav"));
        battleMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/battle.ogg"));

    }

    public static Sound[] getPunchSounds() {
        return new Sound[]{punch1, punch2, punch3, punch4};
    }

    public static Sound[] getLaserSounds() {
        return new Sound[]{laser1, laser2, laser3, laser4, laser5, laser6, laser7, laser8, laser9};
    }

    public static Texture[] getGodModeColors() {
        return new Texture[]{redPlayer, orangePlayer, yellowPlayer, greenPlayer, bluePlayer, indigoPlayer, purplePlayer};
    }

    public static Texture[] getAlienBomber() {
        return new Texture[]{alienBomber1, alienBomber2, alienBomber3, alienBomber4};
    }

    public static Texture getTexture(String texture) {
        return switch (texture) {
            case "player" -> player;
            case "ground" -> ground;
            case "space1" -> space1;
            case "space2" -> space2;
            case "space3" -> space3;
            case "space4" -> space4;
            case "space5" -> space5;
            case "wallTop" -> wallTop;
            case "wallTop2" -> wallTop2;
            case "wallTop3" -> wallTop3;
            case "wallBottom" -> wallBottom;
            case "wallLeft" -> wallLeft;
            case "wallRight" -> wallRight;
            case "wallTopLeftInside" -> wallTopLeftInside;
            case "wallTopRightInside" -> wallTopRightInside;
            case "wallBottomLeftInside" -> wallBottomLeftInside;
            case "wallBottomRightInside" -> wallBottomRightInside;
            case "wallTopLeftOutside" -> wallTopLeftOutside;
            case "wallTopRightOutside" -> wallTopRightOutside;
            case "wallBottomLeftOutside" -> wallBottomLeftOutside;
            case "wallBottomRightOutside" -> wallBottomRightOutside;
            case "wallVertical" -> wallVertical;
            case "alien" -> alien;
            case "alienTwo" -> alienTwo;
            case "bullet" -> bullet;
            case "black" -> black;
            case "red" -> red;
            case "gray" -> gray;
            case "green" -> green;
            case "brightGreen" -> brightGreen;
            case "startBackground" -> startBackground;
            case "buttonOffStart" -> buttonOffStart;
            case "buttonOnStart" -> buttonOnStart;
            case "buttonOffExit" -> buttonOffExit;
            case "buttonOnExit" -> buttonOnExit;
            default -> null;
        };
    }

    public static void dispose() {
        player.dispose();
        ground.dispose();
        space1.dispose();
        space2.dispose();
        space3.dispose();
        space4.dispose();
        space5.dispose();
        wallTop.dispose();
        wallTop2.dispose();
        wallTop3.dispose();
        wallBottom.dispose();
        wallLeft.dispose();
        wallRight.dispose();
        wallTopLeftInside.dispose();
        wallTopRightInside.dispose();
        wallBottomLeftInside.dispose();
        wallBottomRightInside.dispose();
        wallTopLeftOutside.dispose();
        wallTopRightOutside.dispose();
        wallBottomLeftOutside.dispose();
        wallBottomRightOutside.dispose();
        wallVertical.dispose();
        alien.dispose();
        alienTwo.dispose();
        bullet.dispose();
        black.dispose();
        red.dispose();
        gray.dispose();
        green.dispose();
        brightGreen.dispose();
        startBackground.dispose();
        buttonOffStart.dispose();
        buttonOnStart.dispose();
        buttonOffExit.dispose();
        buttonOnExit.dispose();
        redDot.dispose();
        greenDot.dispose();
        redPlayer.dispose();
        orangePlayer.dispose();
        yellowPlayer.dispose();
        greenPlayer.dispose();
        bluePlayer.dispose();
        indigoPlayer.dispose();
        purplePlayer.dispose();
        star.dispose();
        healthPacket.dispose();
        raygun.dispose();
        alienBomber1.dispose();
        alienBomber2.dispose();
        alienBomber3.dispose();
        alienBomber4.dispose();
        explosion.dispose();
        laser1.dispose();
        laser2.dispose();
        laser3.dispose();
        laser4.dispose();
        laser5.dispose();
        laser6.dispose();
        laser7.dispose();
        laser8.dispose();
        laser9.dispose();
        punch1.dispose();
        punch2.dispose();
        punch3.dispose();
        punch4.dispose();
        explode.dispose();
        loadingMusic.dispose();
        battleMusic.dispose();
    }
}


//https://opengameart.org/content/63-digital-sound-effects-lasers-phasers-space-etc
//Attribution Instructions:
//Credit "Kenney.nl" or "www.kenney.nl", this is not mandatory.

//https://opengameart.org/content/loading-screen-loop