package me.dylan.mvcGame.other;

import me.dylan.mvcGame.drawers.Shader;

import java.io.*;
import java.util.regex.Pattern;

//TODO javadoc
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
            ClassLoader classLoader = ResourceHandling.class.getClassLoader();
            return classLoader.getResourceAsStream(name);
        }
        throw new IOException(name + " not found");
    }

    public static String GetExecutionPath(){
        String absolutePath = (new ResourceHandling()).getClass().getProtectionDomain().getCodeSource().getLocation().getPath();//get the path of the class
        absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));//make sure it is the directory not the file
        absolutePath = absolutePath.replaceAll("%20"," "); //replace all of the %20 with spaces
        return absolutePath;//return the path
    }
}
