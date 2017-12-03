package me.dylan.mvcGame.drawers;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.other.ResourceHandling;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextDrawer {
    private int texture = -1;
    private int vbo = -1;
    private ArrayList<Float> bufferData = new ArrayList<>();
    private int nextDrawAmount = 0;

    private static float xtextsize = 1;
    private static float ytextsize = 1;

    public TextDrawer(String imageName){
        BufferedImage image = null;
        try {
            image = ImageIO.read(ResourceHandling.getFileOrResource(imageName));

            xtextsize = 8.0f / image.getWidth();
            ytextsize = 8.0f / image.getHeight();

            texture = Texture.createImageIdWithImage(image);
        } catch (IOException e) {
            System.err.println("Image " + imageName + " not found.");
        }
        vbo = VBODrawer2D.createBufferId();
    }

    public float getSizeForText(String text, float size){
        return getSizeForText(text.length(), size);
    }

    public float getSizeForText(int charAmount, float size){
        return charAmount * size / 8 * 6;
    }

    public void restartTextDrawer(){
        bufferData.clear();
    }

    public void drawText(String text, float x, float y, float r, float g, float b, float a,float size){
        char[] chars = text.toCharArray();
        size = size / 8.0f;

        for(int i = 0; i < chars.length; i++)
        {
            int character = chars[i] - 32;
            int charx = character % 16;
            int chary = character / 16;

            VBODrawer2D.draw2DSquare(bufferData, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, x + i * size * 6, y, 8 * size, 8 * size, r, g, b, a, xtextsize * charx, ytextsize * chary, xtextsize, ytextsize);
        }
    }

    public void writeBufToMem(){
        VBODrawer2D.writeBufToMem(vbo, bufferData);
        nextDrawAmount = bufferData.size() / 9;
        restartTextDrawer();
    }

    public void draw(MainModel mainModel){
        VBODrawer2D.drawVBO(mainModel, vbo, texture, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, nextDrawAmount);
    }
}
