package me.dylan.mvcGame.other;

import java.io.*;

/**
 * A class that does different resource handling stuff.
 *
 * @author Dylan Gybels
 */
public class ResourceHandling {

    /**
     * Get a file or a resource from the name and/or path of the resource.
     * This method first looks at the file system.
     * And if we can't find it there we will search in the jar itself.
     * If we can't find it we will throw an IO exception.
     *
     * @param name The name and/or path to the resource.
     * @return The input stream of the resource. (If we found it)
     * @throws IOException When we can't find the resource.
     */
    public static InputStream getFileOrResource(String name) throws IOException {
        File file = new File("./" + name);//on the file system
        if(file.exists()){
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            ClassLoader classLoader = ResourceHandling.class.getClassLoader();//in the jar
            return classLoader.getResourceAsStream(name);
        }
        throw new IOException(name + " not found");
    }

    /**
     * Get the execution path of the program.
     *
     * @return The execution path of the program.
     */
    public static String GetExecutionPath(){
        //code stolen from myself
        String absolutePath = (new ResourceHandling()).getClass().getProtectionDomain().getCodeSource().getLocation().getPath();//get the path of the class
        absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));//make sure it is the directory not the file
        absolutePath = absolutePath.replaceAll("%20"," "); //replace all of the %20 with spaces
        return absolutePath;//return the path
    }
}
