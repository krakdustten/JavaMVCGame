package me.dylan.mvcGame.drawers;

import me.dylan.mvcGame.other.ResourceHandling;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture {

    public static int createImageId(String filePath){
        BufferedImage image;
        try {
            image = ImageIO.read(ResourceHandling.getFileOrResource(filePath));

            return createImageIdWithImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static int createImageIdWithImage(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();

        int[] raw;
        raw = image.getRGB(0, 0, width, height, null, 0, width);

        ByteBuffer pixel = BufferUtils.createByteBuffer(width * height * 4);

        for(int i = 0; i < width * height; i++){
            pixel.put((byte)((raw[i] >> 16) & 0xFF ));//red
            pixel.put((byte)((raw[i] >> 8 ) & 0xFF ));//green
            pixel.put((byte)(raw[i]         & 0xFF ));//blue
            byte temp = (byte)((raw[i] >> 24) & 0xFF );
            pixel.put(temp);//alpha
        }
        pixel.flip();

        int id = GL11.glGenTextures();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D,GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixel);

        return id;
    }

    public static void bindTextureId(int id){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public static void bindTextureWithSampler(int id, int sampler){
        if(sampler < 0 || sampler >= 32)return;
        bindTextureId(id);
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + sampler);
    }

    public static void deleteImage(int id){
        GL11.glDeleteTextures(id);
    }
}
