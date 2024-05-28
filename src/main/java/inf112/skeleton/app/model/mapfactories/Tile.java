package inf112.skeleton.app.model.mapfactories;

import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.model.entity.Interactable;
import inf112.skeleton.app.view.Media;

import java.util.Random;

public class Tile implements Interactable {
    private TileType tiletype;
    private Texture primaryTexture;
    private Texture secondaryTexture;

    public Tile(TileType type) {
        this.secondaryTexture = Media.space5;
        this.tiletype = type;
    }

    public Texture getSecondaryTexture() {
        return secondaryTexture;
    }

    public void setSecondaryTexture(Texture secondaryTexture) {
        this.secondaryTexture = secondaryTexture;
    }

    public TileType getTiletype() {
        return tiletype;
    }

    public void setTiletype(TileType tiletype) {
        this.tiletype = tiletype;
    }

    public Texture getPrimaryTexture() {
        return primaryTexture;
    }

    public void setPrimaryTexture(Texture texture) {
        this.primaryTexture = texture;
    }

    @Override
    public void interact(Interactable target, boolean isStart) {
    }

    @Override
    public Class<?> getOwnerType() {
        return this.getClass();
    }

    public String getWallType(int[][] neighbours) {
        StringBuilder type = new StringBuilder();

        // Simple directional checks
        if (isFloor(neighbours[1][0])) type.append("E"); // East
        if (isFloor(neighbours[1][2])) type.append("W"); // West
        if (isFloor(neighbours[0][1])) type.append("N"); // North
        if (isFloor(neighbours[2][1])) type.append("S"); // South

        if (type.length() == 1 || type.toString().equals("NS")) {
            if (type.toString().equals("N") || type.toString().equals("NS")) {
                Random rand = new Random();
                if (rand.nextInt(100) < 20) return "N3";
                else if (rand.nextInt(100) < 20) return "N2";
                else return "N1";
            }
            else return type.toString();
        }
        if (type.toString().equals("EW")) return "EW";
        // Check for inside corners
        if (isFloor(neighbours[0][1]) && isFloor(neighbours[1][0])) return "SEI"; // North-West Inside
        if (isFloor(neighbours[0][1]) && isFloor(neighbours[1][2])) return "SWI"; // North-East Inside
        if (isFloor(neighbours[2][1]) && isFloor(neighbours[1][0])) return "NEI"; // South-West Inside
        if (isFloor(neighbours[2][1]) && isFloor(neighbours[1][2])) return "NWI"; // South-East Inside

        // Check for outside corners
        if (isFloor(neighbours[0][0])) return "NWO"; // North-West Outside
        if (isFloor(neighbours[0][2])) return "NEO"; // North-East Outside
        if (isFloor(neighbours[2][0])) return "SWO"; // South-West Outside
        if (isFloor(neighbours[2][2])) return "SEO"; // South-East Outside

        return type.toString();
    }

    private boolean isFloor(int tileValue) {
        return tileValue == 1 || tileValue == 7 || tileValue == 8 || tileValue == 9;
    }
}
