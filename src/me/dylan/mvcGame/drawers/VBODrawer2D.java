package me.dylan.mvcGame.drawers;

import me.dylan.mvcGame.main.MainModel;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * A class that handles VBO's on the graphics card so we can draw things.
 *
 * @author Dylan Gybels
 */
public class VBODrawer2D {
    /**Only draw white shapes.*/
    public static final int COORDS_TYPE = 0;
    /**Draw shapes with textures.*/
    public static final int COORDS_TEXTURE_TYPE = 1;
    /**Draw shapes with color.*/
    public static final int COORDS_COLOR_TYPE = 2;
    /**Draw shapes with textures and color factors.*/
    public static final int COORDS_COLOR_TEXTURE_TYPE = 3;

    /**
     * Create a new VBO.
     *
     * @return The id of the VBO created.
     */
    public static int createBufferId(){
        return GL15.glGenBuffers();
    }

    /**
     * Calculate the size of the array needed to store the vertexes for a specific amount of squares.
     *
     * @param type The draw type.
     * @param amount The amount of squares.
     * @return The size of the array needed to store the vertexes.
     */
    public static int calcArraySizeForSquares(int type, int amount){
        switch (type){
            case COORDS_TYPE:
                return amount * 3 * 6; //3 vars per point (x, y, z                    ) * 6 points per square
            case COORDS_TEXTURE_TYPE:
                return amount * 5 * 6; //5 vars per point (x, y, z,             tx, ty) * 6 points per square
            case COORDS_COLOR_TYPE:
                return amount * 7 * 6; //7 vars per point (x, y, z, r, g, b, a        ) * 6 points per square
            case COORDS_COLOR_TEXTURE_TYPE:
                return amount * 9 * 6; //9 vars per point (x, y, z, r, g, b, a, tx, ty) * 6 points per square
        }
        return 0;
    }

    /**
     * Calculate the draw amount needed to draw a specific amount of squares.
     * User to pass the amount to the "drawVBO" functions".
     *
     * @param amount The amount of squares to draw.
     * @return The draw amount to pass to the "drawVBO" functions.
     */
    public static int calcDrawAmountForSquares(int amount){
        return 6 * amount;//6 point per square to draw
    }

    /**
     * Draw a square to an array list of floats (the vertex array) with a rotation.
     *
     * @param array The array list of floats (vertex array).
     * @param type The draw type you want to use. (Use the same draw type for the hole VBO.)
     * @param x The x position to draw the square on.
     * @param y The y position to draw the square on.
     * @param dx The x size of the square.
     * @param dy The y size of the square.
     * @param rotation The rotation of the square. (0 - 2 * pi)
     * @param r The red factor of the square.
     * @param g The green factor of the square.
     * @param b The blue factor of the square.
     * @param a The opacity factor of the square.
     * @param tx The x coordinate on the texture.
     * @param ty The y coordinate on the texture.
     * @param dtx The x size on the texture.
     * @param dty The y size on the texture.
     */
    public static void draw2DSquareRot(ArrayList<Float> array, int type, float x, float y, float dx, float dy,
                                       float rotation, float r, float g, float b, float a, float tx, float ty, float dtx, float dty) {
        float[] f = new float[calcArraySizeForSquares(type, 1)];
        draw2DSquareRot(f, 0, type, x, y, dx, dy, rotation, r, g, b, a, tx, ty, dtx, dty);
        for (float aF : f) array.add(aF);
    }

    /**
     * Draw a square to an array of floats (the vertex array) with a rotation.
     *
     * @param array The array of floats (vertex array).
     * @param offset The pre offset on the array.
     * @param type The draw type you want to use. (Use the same draw type for the hole VBO.)
     * @param x The x position to draw the square on.
     * @param y The y position to draw the square on.
     * @param dx The x size of the square.
     * @param dy The y size of the square.
     * @param rotation The rotation of the square. (0 - 2 * pi)
     * @param r The red factor of the square.
     * @param g The green factor of the square.
     * @param b The blue factor of the square.
     * @param a The opacity factor of the square.
     * @param tx The x coordinate on the texture.
     * @param ty The y coordinate on the texture.
     * @param dtx The x size on the texture.
     * @param dty The y size on the texture.
     * @return The new offset of the array.
     */
    public static int draw2DSquareRot(float[] array, int offset, int type, float x, float y, float dx, float dy,
                                      float rotation, float r, float g, float b, float a, float tx, float ty, float dtx, float dty) {
        return draw2DSquareRotCenter(array, offset, type, x, y, dx, dy, rotation, x + dx/2, y + dy/2, r, g, b, a, tx, ty, dtx, dty);
    }

    /**
     * Draw a square to an array list of floats (the vertex array) with a rotation and the rotation point.
     *
     * @param array The array list of floats (vertex array).
     * @param type The draw type you want to use. (Use the same draw type for the hole VBO.)
     * @param x The x position to draw the square on.
     * @param y The y position to draw the square on.
     * @param dx The x size of the square.
     * @param dy The y size of the square.
     * @param rotation The rotation of the square. (0 - 2 * pi)
     * @param rotCx The x coordinate where we are rotating around.
     * @param rotCy The y coordinate where we are rotating around.
     * @param r The red factor of the square.
     * @param g The green factor of the square.
     * @param b The blue factor of the square.
     * @param a The opacity factor of the square.
     * @param tx The x coordinate on the texture.
     * @param ty The y coordinate on the texture.
     * @param dtx The x size on the texture.
     * @param dty The y size on the texture.
     */
    public static void draw2DSquareRotCenter(ArrayList<Float> array, int type, float x, float y, float dx, float dy,
                                             float rotation, float rotCx, float rotCy, float r, float g, float b, float a, float tx, float ty, float dtx, float dty) {
        float[] f = new float[calcArraySizeForSquares(type, 1)];
        draw2DSquareRotCenter(f, 0, type, x, y, dx, dy, rotation, rotCx, rotCy, r, g, b, a, tx, ty, dtx, dty);
        for (float aF : f) array.add(aF);
    }

    /**
     * Draw a square to an array of floats (the vertex array) with a rotation and the rotation point.
     *
     * @param array The array of floats (vertex array).
     * @param offset The pre offset of the array.
     * @param type The draw type you want to use. (Use the same draw type for the hole VBO.)
     * @param x The x position to draw the square on.
     * @param y The y position to draw the square on.
     * @param dx The x size of the square.
     * @param dy The y size of the square.
     * @param rotation The rotation of the square. (0 - 2 * pi)
     * @param rotCx The x coordinate where we are rotating around.
     * @param rotCy The y coordinate where we are rotating around.
     * @param r The red factor of the square.
     * @param g The green factor of the square.
     * @param b The blue factor of the square.
     * @param a The opacity factor of the square.
     * @param tx The x coordinate on the texture.
     * @param ty The y coordinate on the texture.
     * @param dtx The x size on the texture.
     * @param dty The y size on the texture.
     * @return The new offset of the array.
     */
    public static int draw2DSquareRotCenter(float[] array, int offset, int type, float x, float y, float dx, float dy,
                                            float rotation, float rotCx, float rotCy, float r, float g, float b, float a, float tx, float ty, float dtx, float dty){
        float sin = (float) Math.sin(rotation);
        float cos = (float) Math.cos(rotation);
        float xT = x - rotCx;
        float yT = y - rotCy;

        float x0 =  xT       * cos -  yT       * sin + rotCx;
        float y0 =  xT       * sin +  yT       * cos + rotCy;
        float x1 = (xT + dx) * cos -  yT       * sin + rotCx;
        float y1 = (xT + dx) * sin +  yT       * cos + rotCy;
        float x2 = (xT + dx) * cos - (yT + dy) * sin + rotCx;
        float y2 = (xT + dx) * sin + (yT + dy) * cos + rotCy;
        float x3 =  xT       * cos - (yT + dy) * sin + rotCx;
        float y3 =  xT       * sin + (yT + dy) * cos + rotCy;

        return draw2DSquare(array, offset, type, x0, y0, x1, y1, x2, y2, x3, y3, r, g, b, a, tx, ty, dtx, dty);
    }

    /**
     * Draw a square list to an array of floats (the vertex array).
     *
     * @param array The array list of floats (vertex array).
     * @param type The draw type you want to use. (Use the same draw type for the hole VBO.)
     * @param x The x position to draw the square on.
     * @param y The y position to draw the square on.
     * @param dx The x size of the square.
     * @param dy The y size of the square.
     * @param r The red factor of the square.
     * @param g The green factor of the square.
     * @param b The blue factor of the square.
     * @param a The opacity factor of the square.
     * @param tx The x coordinate on the texture.
     * @param ty The y coordinate on the texture.
     * @param dtx The x size on the texture.
     * @param dty The y size on the texture.
     */
    public static void draw2DSquare(ArrayList<Float> array, int type, float x, float y, float dx, float dy,
                                    float r, float g, float b, float a, float ty, float tx, float dty, float dtx){
        float[] f = new float[calcArraySizeForSquares(type, 1)];
        draw2DSquare(f, 0, type, x, y, dx, dy, r, g, b, a, ty, tx, dty, dtx);
        for (float aF : f) array.add(aF);
    }

    /**
     * Draw a square to an array of floats (the vertex array).
     *
     * @param array The array of floats (vertex array).
     * @param offset The pre offset of the array.
     * @param type The draw type you want to use. (Use the same draw type for the hole VBO.)
     * @param x The x position to draw the square on.
     * @param y The y position to draw the square on.
     * @param dx The x size of the square.
     * @param dy The y size of the square.
     * @param r The red factor of the square.
     * @param g The green factor of the square.
     * @param b The blue factor of the square.
     * @param a The opacity factor of the square.
     * @param tx The x coordinate on the texture.
     * @param ty The y coordinate on the texture.
     * @param dtx The x size on the texture.
     * @param dty The y size on the texture.
     * @return The new offset of the array.
     */
    public static int draw2DSquare(float[] array, int offset, int type, float x, float y, float dx, float dy,
                                   float r, float g, float b, float a, float tx, float ty, float dtx, float dty){
        return draw2DSquare(array, offset, type, x, y, x + dx, y, x + dx, y + dy, x, y + dy, r, g, b, a, tx, ty, dtx, dty);
    }

    /**
     * Draw a square to an array of floats (the vertex array) with avery point of the square.
     *
     * @param array The array of floats (vertex array).
     * @param offset The pre offset of the array.
     * @param type The draw type you want to use. (Use the same draw type for the hole VBO.)
     * @param x0 The x coordinate of the first point of the square.
     * @param y0 The y coordinate of the first point of the square.
     * @param x1 The x coordinate of the second point of the square.
     * @param y1 The y coordinate of the second point of the square.
     * @param x2 The x coordinate of the third point of the square.
     * @param y2 The y coordinate of the third point of the square.
     * @param x3 The x coordinate of the fourth point of the square.
     * @param y3 The y coordinate of the fourth point of the square.
     * @param r The red factor of the square.
     * @param g The green factor of the square.
     * @param b The blue factor of the square.
     * @param a The opacity factor of the square.
     * @param tx The x coordinate on the texture.
     * @param ty The y coordinate on the texture.
     * @param dtx The x size on the texture.
     * @param dty The y size on the texture.
     * @return The new offset of the array.
     */
    public static int draw2DSquare(float[] array, int offset, int type, float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3,
                                   float r, float g, float b, float a, float tx, float ty, float dtx, float dty){
        switch (type){
            case COORDS_TYPE://x, y, z
                addAll(array, offset,
                        x0, y0, 0.0f,
                        x1, y1, 0.0f,
                        x3, y3, 0.0f,
                        x2, y2, 0.0f,
                        x3, y3, 0.0f,
                        x1, y1, 0.0f
                );
                offset += 3 * 6;
                break;
            case COORDS_TEXTURE_TYPE://x, y, z, tx, ty
                addAll(array, offset,
                        x0, y0, 0.0f, tx      , ty + dty,
                        x1, y1, 0.0f, tx + dtx, ty + dty,
                        x3, y3, 0.0f, tx      , ty      ,
                        x2, y2, 0.0f, tx + dtx, ty      ,
                        x3, y3, 0.0f, tx      , ty      ,
                        x1, y1, 0.0f, tx + dtx, ty + dty
                );
                offset += 5 * 6;
                break;
            case COORDS_COLOR_TYPE://x, y, z, r, g, b, a
                addAll(array, offset,
                        x0, y0, 0.0f, r, g, b, a,
                        x1, y1, 0.0f, r, g, b, a,
                        x3, y3, 0.0f, r, g, b, a,
                        x2, y2, 0.0f, r, g, b, a,
                        x3, y3, 0.0f, r, g, b, a,
                        x1, y1, 0.0f, r, g, b, a
                );
                offset += 7 * 6;
                break;
            case COORDS_COLOR_TEXTURE_TYPE://x, y, z, r, g, b, a, tx, ty
                addAll(array, offset,
                        x0, y0, 0.0f, r, g, b, a, tx      , ty + dty,
                        x1, y1, 0.0f, r, g, b, a, tx + dtx, ty + dty,
                        x3, y3, 0.0f, r, g, b, a, tx      , ty      ,
                        x2, y2, 0.0f, r, g, b, a, tx + dtx, ty      ,
                        x3, y3, 0.0f, r, g, b, a, tx      , ty      ,
                        x1, y1, 0.0f, r, g, b, a, tx + dtx, ty + dty
                );
                offset += 9 * 6;
                break;
        }
        return offset;
    }

    /**
     * Write a array list of floats (vertex array) to the VBO buffer on the graphics card.
     *
     * @param vbo The ID od the vbo to write to.
     * @param data The data to write to the VBO buffer.
     */
    public static void writeBufToMem(int vbo, ArrayList<Float> data){
        float[] f = new float[data.size()];
        int i = 0;
        for(float f2 : data) f[i++] = f2;
        writeBufToMem(vbo, f);
    }

    /**
     * Write a array of floats (vertex array) to the VBO buffer on the graphics card.
     *
     * @param vbo The ID od the vbo to write to.
     * @param data The data to write to the VBO buffer.
     */
    public static void writeBufToMem(int vbo, float[] data){
        FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
        buf.put(data);
        buf.flip();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_DYNAMIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    /**
     * Add all elements to array.
     *
     * @param arr The array to add the elements to.
     * @param offset The offset for writing items to the array.
     * @param elements The elements to add to the array.
     */
    static void addAll(float[] arr, int offset, float ... elements){
        for (float f : elements) arr[offset++] = f;
    }

    /**
     * Draw VBO without texture.
     *
     * @param mainModel The main model.
     * @param vbo The VBO to draw.
     * @param type The draw type you want to use. (The same as u used when creating the buffers)
     * @param amount The amount of vertexes to draw.
     */
    public static void drawVBO(MainModel mainModel, int vbo, int type, int amount){
        if( type == COORDS_TEXTURE_TYPE || type == COORDS_COLOR_TEXTURE_TYPE ) throw new NullPointerException("no texture given!");
        drawVBO(mainModel, vbo, -1, type, amount);
    }

    /**
     * Draw VBO with texture.
     *
     * @param mainModel The main model.
     * @param vbo The VBO to draw.
     * @param pic The texture to use.
     * @param type The draw type you want to use. (The same as u used when creating the buffers)
     * @param amount The amount of vertexes to draw.
     */
    public static void drawVBO(MainModel mainModel, int vbo, int pic, int type, int amount){
        drawVBOWithShaderAll(null, vbo, mainModel.getMainShader2D(), pic, 0, type, amount);
    }

    /**
     * Draw VBO with texture and custom shader.
     *
     * @param vbo The VBO to draw.
     * @param shader2D The shader to use.
     * @param pic The texture to use.
     * @param type The draw type you want to use. (The same as u used when creating the buffers)
     * @param amount The amount of vertexes to draw.
     */
    public static void drawVBOWithShader(int vbo, int shader2D, int pic, int type, int amount){
        drawVBOWithShaderAll(null, vbo, shader2D, pic, 0, type, amount);
    }

    /**
     * Draw VBO with sampler.
     *
     * @param mainModel The main model.
     * @param vbo The VBO to draw.
     * @param sampler The sampler to use.
     * @param type The draw type you want to use. (The same as u used when creating the buffers)
     * @param amount The amount of vertexes to draw.
     */
    public static void drawVBOWithSampler(MainModel mainModel, int vbo, int sampler, int type, int amount){
        drawVBOWithShaderAll(null, vbo, mainModel.getMainShader2D(), -1, sampler, type, amount);
    }

    /**
     * Draw VBO with projection, shader and picture or sampler.
     *
     * @param projection The projection to use. (null for default)
     * @param vbo The VBO to draw.
     * @param shader2D The shader to use.
     * @param pic The texture to use. (-1 to not use a texture)
     * @param sampler The sampler to use.
     * @param type The draw type you want to use. (The same as u used when creating the buffers)
     * @param amount The amount of vertexes to draw.
     */
    public static void drawVBOWithShaderAll(Matrix4f projection, int vbo, int shader2D, int pic, int sampler, int type, int amount){
        boolean texture = type == COORDS_TEXTURE_TYPE || type == COORDS_COLOR_TEXTURE_TYPE ;
        boolean color = type == COORDS_COLOR_TYPE || type == COORDS_COLOR_TEXTURE_TYPE ;
        int stride = (color ? 4 : 0) + (texture ? 2 : 0) + 3;

        if(pic != -1)Texture.bindTextureWithSampler(pic, sampler);
        Shader.bind(shader2D);
        Shader.setUniform(shader2D, "sampler", sampler);
        if(projection != null)Shader.setUniform(shader2D, "projection", projection);
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

    /**
     * Remove the VBO from the graphics card.
     *
     * @param vbo The VBO to clear.
     */
    public static void deleteVBO(int vbo){
        GL15.glDeleteBuffers(vbo);
    }
}
