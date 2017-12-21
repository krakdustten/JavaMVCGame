package me.dylan.mvcGame.drawers;

import me.dylan.mvcGame.other.ResourceHandling;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * A class that holds a texture as a map of multiple textures.
 * This way you can easily draw the texture you want in a tilemap.
 *
 * You can also get the pixel colour of a specific position on a specific tile.
 *
 * @author Dylan Gybels
 */
public class AdvancedTextureTileMap extends TextureTileMap {
    private int[] image;
    private int imageHeight;
    private int imageWidth;

    /**
     * Create a new Advanced Texture Tile Map with the path to the texture and the map size.
     *
     * @param texturePath The path to the Tile Map Texture.
     * @param xBlocks The amount of blocks in the x direction.
     * @param yBlocks The amount of blocks in the y direction.
     */
    public AdvancedTextureTileMap(String texturePath, int xBlocks, int yBlocks) {
        super(texturePath, xBlocks, yBlocks);

        try {
            BufferedImage image = ImageIO.read(ResourceHandling.getFileOrResource(texturePath));
            this.imageHeight = image.getHeight();
            this.imageWidth = image.getWidth();
            this.image = image.getRGB(0, 0, imageWidth, imageHeight, null, 0 ,imageWidth);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the color in a specific position on a specific tile.
     *
     * @param blockX The x coordinate of the tilemap.
     * @param blockY The y coordinate of the tilemap.
     * @param x The x coordinate in the tile texture you want (0 - 1).
     * @param y The y coordinate in the tile texture you want (0 - 1).
     * @return The color on the coordinates above (MSB /RRRRRRRR/GGGGGGGG/BBBBBBBB/ LSB).
     */
    public int getBaseColorInBlock(int blockX, int blockY, float x, float y){
        float xImage = (blockX + x) * xBlocksize;
        float yImage = (blockY + y) * yBlocksize;

        int xI = (int) (xImage * imageWidth);
        int yI = (int) (yImage * imageHeight);

        return image[xI + yI * imageWidth];
    }

    /**
     * Get the color in a specific position on a specific tile with a base color.
     *
     * @param blockX The x coordinate of the tilemap.
     * @param blockY The y coordinate of the tilemap.
     * @param x The x coordinate in the tile texture you want (0 - 1).
     * @param y The y coordinate in the tile texture you want (0 - 1).
     * @param r The red part of the base color (0 - 1).
     * @param g The green part of the base color (0 - 1).
     * @param b The blue part of the base color (0 - 1).
     * @return The color on the coordinates above (MSB /RRRRRRRR/GGGGGGGG/BBBBBBBB/ LSB).
     */
    public int getAdvancedColorInBlock(int blockX, int blockY, float x, float y, float r, float g, float b){
        int color = getBaseColorInBlock(blockX, blockY, x, y);

        int blue = (int) ((color & 0xff) * b);
        int green = (int) (((color >> 8) & 0xff) * g);
        int red = (int) (((color >> 16) & 0xff) * r);

        int nColor = blue & 0xff;
        nColor += (green & 0xff) << 8;
        nColor += (red & 0xff) << 16;
        return nColor;
    }
}
