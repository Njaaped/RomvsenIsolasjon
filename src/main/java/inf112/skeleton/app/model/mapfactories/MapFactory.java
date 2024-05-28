package inf112.skeleton.app.model.mapfactories;


public interface MapFactory {
    /**
     * Generates a new map.
     * @param width Number of tiles in horizontal direction
     * @param height Number of tiles in vertical direction
     * @param difficulty Difficulty of the map
     * @return A new map
     */
    Map generate(int width, int height, int difficulty);

    /**
     * Sets the seed for the random number generator.
     * @param seed The seed
     */
    void setSeed(long seed);

    /**
     * get the seed
     */
    int getSeed();
    
}
