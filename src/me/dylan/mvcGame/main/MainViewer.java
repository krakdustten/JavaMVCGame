package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.Shader;
import org.joml.Matrix4f;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.system.MemoryUtil.NULL;

public class MainViewer {
    private long window;
    private int mainShader;
    private Camera camera;

    public MainViewer(String mainShaderName){
        //init LWJGL
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !GLFW.glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // the window will be resizable

        // Create the window
        window = GLFW.glfwCreateWindow(600, 400, "Java MVC game", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);
        // Make the window visible
        GLFW.glfwShowWindow(window);

        GL.createCapabilities();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        //Make transparency work
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Set the clear color
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);


        mainShader = Shader.compileShader(mainShaderName);
        camera = new Camera(mainShader, 600, 400);

        GLFW.glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback(){
            @Override
            public void invoke(long window, int width, int height){
                camera.setSceenSize(width, height);
                GL11.glViewport(0, 0, width, height);
            }
        });
    }


    public void startRender() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    public void endRender() {
        GLFW.glfwSwapBuffers(window); // swap the color buffers
    }

    public void update() {
        camera.update();
    }

    public void deInit() {
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public long getWindow() { return window; }
    public int getMainShader(){return mainShader;}
    public Matrix4f getProjection(){return camera.getProjection();}

}
