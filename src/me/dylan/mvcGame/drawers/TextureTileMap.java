package me.dylan.mvcGame.drawers;

/**
 * A class that holds a texture as a map of multiple textures.
 * This way you can easily draw the texture you want in a tilemap.
 *
 * @author Dylan Gybels
 */
public class TextureTileMap {
    private int texture_id;
    /**The x block size.*/
    protected float xBlocksize;
    /**The y block size.*/
    protected float yBlocksize;

    /**
     * Create a Texture Tile Map with a texture id created by the Texture class and the map size.
     *
     * @param texture_id The texture id created by the Texture class.
     * @param xBlocks The amount of blocks in the x direction.
     * @param yBlocks The amount of blocks in the y direction.
     */
    public TextureTileMap(int texture_id, int xBlocks, int yBlocks){
        this.texture_id = texture_id;
        this.xBlocksize = 1.0f / xBlocks;
        this.yBlocksize = 1.0f / yBlocks;
    }

    /**
     * Create a new Texture Tile Map with the path to the texture and the map size.
     *
     * @param texturePath The path to the Tile Map Texture.
     * @param xBlocks The amount of blocks in the x direction.
     * @param yBlocks The amount of blocks in the y direction.
     */
    public TextureTileMap(String texturePath, int xBlocks, int yBlocks){
        this(Texture.createImageId(texturePath), xBlocks, yBlocks);
    }

    /**
     * Draw a texture to the VBO array with a given position, size, color and Tile Map coordinates.
     *
     * @param array The array for the data.
     * @param offset The pre-offset of the data array.
     * @param x The x position of the tile drawn.
     * @param y The y position of the tile drawn.
     * @param dx The x size of the tile drawn.
     * @param dy The y size of the tile drawn.
     * @param r The red factor of the tile drawn.
     * @param g The green factor of the tile drawn.
     * @param b The blue factor of the tile drawn.
     * @param a The opacity factor of the tile drawn.
     * @param xBlock The x coordinate of the texture in the tile map.
     * @param yBlock The y coordinate of the texture in the tile map.
     * @return The new offset of the data array.
     */
    public int drawTile2D(float[] array, int offset, float x, float y, float dx, float dy, float r, float g, float b, float a, int xBlock, int yBlock){
        float tx = xBlock * xBlocksize;
        float ty = yBlock * yBlocksize;
        return VBODrawer2D.draw2DSquare(array, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, x, y, dx, dy, r, g, b, a, tx, ty, xBlocksize, yBlocksize);
    }

    /**
     * Get the texture id of the hole tile map.
     *
     * @return The texture id of the hole tile map.
     */
    public int getTexture_id() { return texture_id; }

    /**
     * Distroy this class, cleanup all variables.
     */
    public void distroy() {
        Texture.deleteImage(texture_id);
    }
}
