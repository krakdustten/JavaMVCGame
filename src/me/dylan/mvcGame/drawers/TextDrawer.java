package me.dylan.mvcGame.drawers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextDrawer {
    private int texture = -1;

    private static float xtextsize = 1;
    private static float ytextsize = 1;

    public TextDrawer(String imageName){
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imageName));

            xtextsize = image.getWidth() / 8.0f;
            ytextsize = image.getHeight() / 8.0f;

            texture = Texture.createImageIdWithImage(image);
        } catch (IOException e) {
            System.err.println("Image " + imageName + " not found.");
        }
    }

    public int calcArraySizeForChars(String text){
        calcArraySizeForChars(text.length());
    }

    public int calcArraySizeForChars(int amountOfChars){
        return VBODrawer.calcArraySizeForSquares(VBODrawer.COORDS_COLOR_TEXTURE_TYPE, amountOfChars);
    }

    public int drawText(float[] array, int offset, String text, float x, float y, float r, float g, float b, float a,float size){
        char[] chars = text.toCharArray();

        for(int i = 0; i < chars.length; i++)
        {
            int character = chars[i] - 32;

            int charx = character % 16;
            int chary = character / 16;

            offset = VBODrawer.draw2DSquare(array, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, x + i * size, y, );
        }

        return offset;
    }
}
