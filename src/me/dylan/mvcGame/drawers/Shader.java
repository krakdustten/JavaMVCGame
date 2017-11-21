package me.dylan.mvcGame.drawers;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public class Shader {
    //CODE FROM https://www.youtube.com/watch?v=q_dS3JuoeDw

    public static int compileShader(String name){
        int program = GL20.glCreateProgram();

        int vs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vs, readFile(name + ".vs"));
        GL20.glCompileShader(vs);
        if(GL20.glGetShaderi(vs, GL20.GL_COMPILE_STATUS) != 1) {
            System.err.println(GL20.glGetShaderInfoLog(vs));
            System.exit(1);
        }

        int fs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fs, readFile(name + ".fs"));
        GL20.glCompileShader(fs);
        if(GL20.glGetShaderi(fs, GL20.GL_COMPILE_STATUS) != 1) {
            System.err.println(GL20.glGetShaderInfoLog(fs));
            System.exit(1);
        }

        GL20.glAttachShader(program, vs);
        GL20.glAttachShader(program, fs);

        GL20.glBindAttribLocation(program, 0, "vertices");
        GL20.glBindAttribLocation(program, 1, "color");
        GL20.glBindAttribLocation(program, 2, "textures");

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

    public static void setUniform(int program, String name, int value){
        int loc = GL20.glGetUniformLocation(program, name);
        if(loc != -1) GL20.glUniform1i(loc, value);
    }

    public static void setUniform(int program, String name, Matrix4f value){
        int loc = GL20.glGetUniformLocation(program, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4*4);//4 by 4 matrix
        value.get(buffer);
        if(loc != -1) GL20.glUniformMatrix4fv(loc, false, buffer);
    }

    public static void bind(int program){
        GL20.glUseProgram(program);
    }

    static private String readFile(String filename){
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(new File("./shaders/" + filename)));
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

    public static void deleteShader(int id){
        GL20.glDeleteProgram(id);
    }
}
