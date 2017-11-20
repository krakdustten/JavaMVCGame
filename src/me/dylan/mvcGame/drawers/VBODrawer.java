package me.dylan.mvcGame.drawers;

import me.dylan.mvcGame.main.MainModel;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class VBODrawer {
    public static final int COORDS_TYPE = 0;
    public static final int COORDS_TEXTURE_TYPE = 1;
    public static final int COORDS_COLOR_TYPE = 2;
    public static final int COORDS_COLOR_TEXTURE_TYPE = 3;

    public static int createBufferId(){
        return GL15.glGenBuffers();
    }

    public static int calcArraySizeForSquares(int type, int amount){
        switch (type){
            case COORDS_TYPE:
                return amount * 3 * 6;
            case COORDS_TEXTURE_TYPE:
                return amount * 5 * 6;
            case COORDS_COLOR_TYPE:
                return amount * 7 * 6;
            case COORDS_COLOR_TEXTURE_TYPE:
                return amount * 9 * 6;
        }
        return 0;
    }
    public static int calcDrawAmountForSquares(int amount){
        return 6 * amount;
    }

    public static void draw2DSquare(ArrayList<Float> array, int type, float x, float y, float dx, float dy, float r, float g, float b, float a, float ty, float tx, float dty, float dtx){
        float[] f = new float[calcArraySizeForSquares(type, 1)];
        draw2DSquare(f, 0, type, x, y, dx, dy, r, g, b, a, ty, tx, dty, dtx);
        for(int i = 0; i < f.length; i++) array.add(f[i]);
    }

    public static int draw2DSquare(float[] array, int offset, int type, float x, float y, float dx, float dy, float r, float g, float b, float a, float tx, float ty, float dtx, float dty){
        switch (type){
            case COORDS_TYPE:
                addAll(array, offset,
                        x, y, 0.0f,
                        x + dx, y     , 0.0f,
                        x     , y + dy, 0.0f,
                        x + dx, y + dy, 0.0f,
                        x     , y + dy, 0.0f,
                        x + dx, y     , 0.0f
                        );
                offset += 3 * 6;
                break;
            case COORDS_TEXTURE_TYPE:
                addAll(array, offset,
                        x, y, 0.0f, tx      , ty + dty,
                        x + dx, y     , 0.0f, tx + dtx, ty + dty,
                        x     , y + dy, 0.0f, tx      , ty      ,
                        x + dx, y + dy, 0.0f, tx + dtx, ty      ,
                        x     , y + dy, 0.0f, tx      , ty      ,
                        x + dx, y     , 0.0f, tx + dtx, ty + dty
                );
                offset += 5 * 6;
                break;
            case COORDS_COLOR_TYPE:
                addAll(array, offset,
                        x, y, 0.0f, r, g, b, a,
                        x + dx, y     , 0.0f, r, g, b, a,
                        x     , y + dy, 0.0f, r, g, b, a,
                        x + dx, y + dy, 0.0f, r, g, b, a,
                        x     , y + dy, 0.0f, r, g, b, a,
                        x + dx, y     , 0.0f, r, g, b, a
                );
                offset += 7 * 6;
                break;
            case COORDS_COLOR_TEXTURE_TYPE:
                addAll(array, offset,
                        x, y, 0.0f, r, g, b, a, tx      , ty + dty,
                        x + dx, y     , 0.0f, r, g, b, a, tx + dtx, ty + dty,
                        x     , y + dy, 0.0f, r, g, b, a, tx      , ty      ,
                        x + dx, y + dy, 0.0f, r, g, b, a, tx + dtx, ty      ,
                        x     , y + dy, 0.0f, r, g, b, a, tx      , ty      ,
                        x + dx, y     , 0.0f, r, g, b, a, tx + dtx, ty + dty
                );
                offset += 9 * 6;
                break;
        }
        return offset;
    }

    public static void writeBufToMem(int vbo, ArrayList<Float> data){
        float[] f = new float[data.size()];
        int i = 0;
        for(float f2 : data){
            f[i++] = f2;

        }
        writeBufToMem(vbo, f);
    }

    public static void writeBufToMem(int vbo, float[] data){
        System.out.println();
        System.out.println();

        FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
        buf.put(data);
        buf.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    static void addAll(float[] arr, int offset, float ... elements){
        for (float f : elements) {
            arr[offset++] = f;
        }
    }

    public static void drawVBO(MainModel mainModel, int vbo, int type, int amount){
        if( type == COORDS_TEXTURE_TYPE || type == COORDS_COLOR_TEXTURE_TYPE ) throw new NullPointerException("no texture given!");
        drawVBO(mainModel, vbo, -1, type, amount);
    }

    public static void drawVBO(MainModel mainModel, int vbo, int pic, int type, int amount){
        drawVBOWithShaderAll(null, vbo, mainModel.getMainShader(), pic, 0, type, amount);
    }

    public static void drawVBOWithShader(int vbo, int shader, int pic, int type, int amount){
        drawVBOWithShaderAll(null, vbo, shader, pic, 0, type, amount);
    }

    public static void drawVBOWithSampler(MainModel mainModel, int vbo, int sampler, int type, int amount){
        drawVBOWithShaderAll(null, vbo, mainModel.getMainShader(), -1, sampler, type, amount);
    }

    public static void drawVBOWithShaderAll(Matrix4f projection, int vbo, int shader, int pic, int sampler, int type, int amount){
        boolean texture = type == COORDS_TEXTURE_TYPE || type == COORDS_COLOR_TEXTURE_TYPE ;
        boolean color = type == COORDS_COLOR_TYPE || type == COORDS_COLOR_TEXTURE_TYPE ;
        int stride = (color ? 4 : 0) + (texture ? 2 : 0) + 3;

        if(pic != -1)Texture.bindTextureWithSampler(pic, sampler);
        Shader.bind(shader);
        Shader.setUniform(shader, "sampler", sampler);
        if(projection != null)Shader.setUniform(shader, "projection", projection);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        GL20.glEnableVertexAttribArray(0);//coords
        if(color)GL20.glEnableVertexAttribArray(1);//color
        if(texture)GL20.glEnableVertexAttribArray(2);//textures

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, stride * 4, 0);
        if(color)GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, stride * 4, 3 * 4);
        if(texture)GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, stride * 4, (color ? 7 : 3) * 4);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, amount);

        GL20.glDisableVertexAttribArray(0);//coords
        if(color)GL20.glDisableVertexAttribArray(1);//color
        if(texture)GL20.glDisableVertexAttribArray(2);//textures
    }

    public static void deleteVBO(int vbo){
        //TODO delete VBO
    }
}
