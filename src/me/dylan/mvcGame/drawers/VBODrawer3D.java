package me.dylan.mvcGame.drawers;

import me.dylan.mvcGame.main.MainModel;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL15;

import java.util.ArrayList;

public class VBODrawer3D {
    public static final int COORDS_TYPE = 100;
    public static final int COORDS_TEXTURE_TYPE = 101;
    public static final int COORDS_COLOR_TYPE = 102;
    public static final int COORDS_COLOR_TEXTURE_TYPE = 103;

    public static int createBufferId(){
        return GL15.glGenBuffers();
    }

    public static int calcArraySizeForSquares(int type, int amount){
        return VBODrawer2D.calcArraySizeForSquares(type - COORDS_TYPE, amount);
    }
    public static int calcDrawAmountForSquares(int amount){
        return 6 * amount;
    }

    public static void draw3DCube(ArrayList<Float> array, int type, float x0, float y0, float z0,  float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float r, float g, float b, float a, float ty, float tx, float dty, float dtx){
        float[] f = new float[calcArraySizeForSquares(type, 1)];
        draw3DQuad(f, 0, type, x0, y0, z0, x1, y1, z1, x2, y2, z2, x3, y3, z3, r, g, b, a, ty, tx, dty, dtx);
        for(int i = 0; i < f.length; i++) array.add(f[i]);
    }

    /**draw quad in 3D space 13
     *                       02
     * */
    public static int draw3DQuad(float[] array, int offset, int type, float x0, float y0, float z0,  float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float r, float g, float b, float a, float tx, float ty, float dtx, float dty){
        switch (type){
            case COORDS_TYPE:
                addAll(array, offset,
                        x0, y0, z0,
                        x2, y2, z2,
                        x1, y1, z1,
                        x3, y3, z3,
                        x1, y1, z1,
                        x2, y2, z2
                        );
                offset += 3 * 6;
                break;
            case COORDS_TEXTURE_TYPE:
                addAll(array, offset,
                        x0, y0, z0, tx      , ty + dty,
                        x2, y2, z2, tx + dtx, ty + dty,
                        x1, y1, z1, tx      , ty      ,
                        x3, y3, z3, tx + dtx, ty      ,
                        x1, y1, z1, tx      , ty      ,
                        x2, y2, z2, tx + dtx, ty + dty
                );
                offset += 5 * 6;
                break;
            case COORDS_COLOR_TYPE:
                addAll(array, offset,
                        x0, y0, z0, r, g, b, a,
                        x2, y2, z2, r, g, b, a,
                        x1, y1, z1, r, g, b, a,
                        x3, y3, z3, r, g, b, a,
                        x1, y1, z1, r, g, b, a,
                        x2, y2, z2, r, g, b, a
                );
                offset += 7 * 6;
                break;
            case COORDS_COLOR_TEXTURE_TYPE:
                addAll(array, offset,
                        x0, y0, z0, r, g, b, a, tx      , ty + dty,
                        x2, y2, z2, r, g, b, a, tx + dtx, ty + dty,
                        x1, y1, z1, r, g, b, a, tx      , ty      ,
                        x3, y3, z3, r, g, b, a, tx + dtx, ty      ,
                        x1, y1, z1, r, g, b, a, tx      , ty      ,
                        x2, y2, z2, r, g, b, a, tx + dtx, ty + dty
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
        VBODrawer2D.writeBufToMem(vbo, data);
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
        drawVBOWithShaderAll(null, vbo, mainModel.getMainShader3D(), pic, 0, type, amount);
    }

    public static void drawVBOWithShader(int vbo, int shader2D, int pic, int type, int amount){
        drawVBOWithShaderAll(null, vbo, shader2D, pic, 0, type, amount);
    }

    public static void drawVBOWithSampler(MainModel mainModel, int vbo, int sampler, int type, int amount){
        drawVBOWithShaderAll(null, vbo, mainModel.getMainShader3D(), -1, sampler, type, amount);
    }

    public static void drawVBOWithShaderAll(Matrix4f projection, int vbo, int shader3D, int pic, int sampler, int type, int amount){
        VBODrawer2D.drawVBOWithShaderAll(projection, vbo, shader3D, pic, sampler, type, amount);
    }

    public static void deleteVBO(int vbo){
        GL15.glDeleteBuffers(vbo);
    }
}
