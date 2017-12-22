package me.dylan.mvcGame.drawers;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.other.ResourceHandling;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A class that draws text on the screen.
 *
 * @author Dylan Gybels
 */
public class TextDrawer {
    private int texture = -1;
    private int vbo;
    private ArrayList<Float> bufferData = new ArrayList<>();
    private int nextDrawAmount = 0;

    private static float xTextureSize = 1;
    private static float yTextureSize = 1;

    /**
     * Create a new text drawer with the path and name to the picture that holds the characters.
     *
     * @param imageName The path and name to the picture that holds the characters.
     */
    public TextDrawer(String imageName){
        BufferedImage image;
        try {
            image = ImageIO.read(ResourceHandling.getFileOrResource(imageName));

            xTextureSize = 8.0f / image.getWidth();
            yTextureSize = 8.0f / image.getHeight();

            texture = Texture.createImageIdWithImage(image);
        } catch (IOException e) {
            System.err.println("Image " + imageName + " not found.");
        }
        vbo = VBODrawer2D.createBufferId();
    }

    /**
     * Get the size needed to draw a text.
     *
     * @param text The text to calculate the size on.
     * @param size The characters size.
     * @return The size needed ot draw this text.
     */
    public float getSizeForText(String text, float size){
        return getSizeForText(text.length(), size);
    }

    /**
     * Get the size needed to draw a text.
     *
     * @param charAmount The amount of chars to draw.
     * @param size The characters size.
     * @return The size needed ot draw this text.
     */
    public float getSizeForText(int charAmount, float size){
        return charAmount * size / 8 * 6;
    }

    /**
     * Clear the buffers of the text drawer.
     * So you can restart drawing to it.
     */
    public void restartTextDrawer(){
        bufferData.clear();
    }

    /**
     * Draw a piece of text on the screen at a specific position, with a size and a color.
     *
     * @param text The text to draw on the screen.
     * @param x The x position to draw it to.
     * @param y The y position to draw it to.
     * @param r The red part of the color to draw the text in.
     * @param g The green part of the color to draw the text in.
     * @param b The blue part of the color to draw the text in.
     * @param a The opacity part of the color to draw the text in.
     * @param size The size of the characters to draw.
     */
    public void drawText(String text, float x, float y, float r, float g, float b, float a,float size){
        char[] chars = text.toCharArray();
        size = size / 8.0f;

        for(int i = 0; i < chars.length; i++)
        {
            int character = chars[i] - 32;
            int charx = character % 16;
            int chary = character / 16;

            VBODrawer2D.draw2DSquare(bufferData, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                    x + i * size * 6, y, 8 * size, 8 * size,
                    r, g, b, a, xTextureSize * charx, yTextureSize * chary, xTextureSize, yTextureSize);
        }
    }

    /**
     * Write the buffers to the graphics card.
     */
    public void writeBufToMem(){
        VBODrawer2D.writeBufToMem(vbo, bufferData);
        nextDrawAmount = bufferData.size() / 9;
        restartTextDrawer();
    }

    /**
     * Draw the buffered texts on the screen.
     * @param mainModel The main model.
     */
    public void draw(MainModel mainModel){
        VBODrawer2D.drawVBO(mainModel, vbo, texture, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, nextDrawAmount);
    }
}
