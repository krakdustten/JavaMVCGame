package me.dylan.mvcGame.drawers;

import me.dylan.mvcGame.other.ResourceHandling;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.*;
import java.nio.FloatBuffer;

/**
 * A class that loads, compiles and binds shaders.
 *
 * @author Dylan Gybels
 * @see "https://www.youtube.com/watch?v=q_dS3JuoeDw"
 */
public class Shader {

    /**
     * Load a shader from the given path and compile it.
     *
     * @param name The name and path to the shader files.
     * @return The id of the shader program.
     */
    public static int compileShader(String name){
        int program = GL20.glCreateProgram();//create a new program

        //load the vertex shader, compile and print the errors (if there are)
        int vs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vs, readFile(name + ".vs"));
        GL20.glCompileShader(vs);
        if(GL20.glGetShaderi(vs, GL20.GL_COMPILE_STATUS) != 1) {
            System.err.println(GL20.glGetShaderInfoLog(vs));
            System.exit(1);
        }

        //load the fragment shader, compile and print the errors (if there are)
        int fs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fs, readFile(name + ".fs"));
        GL20.glCompileShader(fs);
        if(GL20.glGetShaderi(fs, GL20.GL_COMPILE_STATUS) != 1) {
            System.err.println(GL20.glGetShaderInfoLog(fs));
            System.exit(1);
        }

        //add the shaders to the shader program
        GL20.glAttachShader(program, vs);
        GL20.glAttachShader(program, fs);

        //bind the buffer id locations to the buffer program
        GL20.glBindAttribLocation(program, 0, "vertices");
        GL20.glBindAttribLocation(program, 1, "color");
        GL20.glBindAttribLocation(program, 2, "textures");

        //link all of these together and print the errors (if there are)
        GL20.glLinkProgram(program);
        if(GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) != 1){
            System.err.println(GL20.glGetProgramInfoLog(fs));
            System.exit(1);
        }
        GL20.glValidateProgram(program);
        if(GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) != 1){
            System.err.println(GL20.glGetProgramInfoLog(fs));
            System.exit(1);
        }

        return program;
    }

    /**
     * Set a uniform variable in the given program.
     *
     * @param program The id of the program
     * @param name The name of the uniform variable
     * @param value The value to write to the uniform variable
     */
    public static void setUniform(int program, String name, int value){
        int loc = GL20.glGetUniformLocation(program, name);
        if(loc != -1) GL20.glUniform1i(loc, value);
    }

    /**
     * Set a uniform variable in the given program.
     *
     * @param program The id of the program
     * @param name The name of the uniform variable
     * @param value The value to write to the uniform variable
     */
    public static void setUniform(int program, String name, Matrix4f value){
        int loc = GL20.glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4*4);//4 by 4 matrix
        value.get(buffer);
        if(loc != -1) GL20.glUniformMatrix4fv(loc, false, buffer);
    }

    /**
     * Bind a program to the running process on the graphics card.
     * -> Use this program for the next render functions.
     *
     * @param program The id of the program to bind to.
     */
    public static void bind(int program){
        GL20.glUseProgram(program);
    }

    /**
     * Read a file and return the hole file as string.
     *
     * @param filename The path of the file.
     * @return The content of the file as string.
     */
    static private String readFile(String filename){
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(ResourceHandling.getFileOrResource("shaders/" + filename)));

            String line;
            while((line = br.readLine()) != null){
                string.append(line);
                string.append('\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string.toString();
    }

    /**
     * Remove the shader program from the graphics card.
     *
     * @param id The id of the shader program to remove.
     */
    public static void deleteShader(int id){
        GL20.glDeleteProgram(id);
    }
}
