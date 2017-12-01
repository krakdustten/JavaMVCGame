package me.dylan.mvcGame.drawers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class AdvancedTextureTileMap extends TextureTileMap {
    private int[] image;
    private int imageHeight;
    private int imageWidth;

    public AdvancedTextureTileMap(String texturePath, int xBlocks, int yBlocks) {
        super(texturePath, xBlocks, yBlocks);

        try {
            BufferedImage image = ImageIO.read(new File(texturePath));
            this.imageHeight = image.getHeight();
            this.imageWidth = image.getWidth();
            byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            this.image = new int[pixels.length / 4];

            for(int i = 0; i < pixels.length; i+=4){
                int pixel = pixels[i + 1] & 0xff;//blue
                pixel += (pixels[i + 2] & 0xff) << 8;//green
                pixel += (pixels[i + 3] & 0xff) << 16;//red
                this.image[i / 4] = pixel;
            }

            //TODO error not eneugh pixels (PLEASE FIX)

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBaseColorInBlock(int blockX, int blockY, float x, float y){
        float xImage = (blockX + x) * xBlocksize;
        float yImage = (blockY + y) * yBlocksize;

        int xI = (int) (xImage * imageWidth);
        int yI = (int) (yImage * imageHeight);

        return image[xI + yI * imageWidth];
    }

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
