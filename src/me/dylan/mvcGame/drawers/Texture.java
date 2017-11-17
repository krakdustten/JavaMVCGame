package me.dylan.mvcGame.drawers;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

public class Texture {

    public static int createImageId(String filePath){
        BufferedImage image;

        try {
            image = ImageIO.read(new File(filePath));

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int createImageId2(String filePath){
        try {
            URL url = new File(filePath).toURI().toURL();
            InputStream input = url.openStream();

            PNGDecoder dec = new PNGDecoder(input);

            //set up image dimensions
            int width = dec.getWidth();
            int height = dec.getHeight();

            //we are using RGBA, i.e. 4 components or "bytes per pixel"
            final int bpp = 4;

            //create a new byte buffer which will hold our pixel data
            ByteBuffer buf = BufferUtils.createByteBuffer(bpp * width * height);

            //decode the image into the byte buffer, in RGBA format
            dec.decode(buf, width * bpp, PNGDecoder.Format.RGBA);

            //flip the buffer into "read mode" for OpenGL
            buf.flip();

            //enable textures and generate an ID
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            int id = GL11.glGenTextures();

            //bind texture
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

            //setup unpack mode
            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

            //setup parameters
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

            //pass RGBA data to OpenGL
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

            input.close();
            return id;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static void bindTextureId(int id){
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }
}
