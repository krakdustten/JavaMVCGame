package me.dylan.mvcGame.other;

import me.dylan.mvcGame.drawers.Shader;

import java.io.*;

public class ResourceHandling {
    private static ClassLoader classLoader = ResourceHandling.class.getClassLoader();

    public static InputStream getFileOrResource(String name) throws IOException {
        File file = new File("./" + name);
        if(file.exists()){
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            ClassLoader classLoader = Shader.class.getClassLoader();
            return classLoader.getResourceAsStream(name);
        }
        throw new IOException(name + " not found");
    }

    /** DO NOT USE ***/
    /*
    public static String getAllResourceNames(String folder){
        return "";
    }

    public static void saveResourceInJar(){

    }*/
}
