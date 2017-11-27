package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.Shader;
import me.dylan.mvcGame.drawers.TextDrawer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.system.MemoryUtil.NULL;

public class MainViewer {
    private MainModel mainModel;
    private MainGameThread mainGameThread;

    public MainViewer(MainModel mainModel, MainGameThread mainGameThread, String mainShaderName2D, String mainShaderName3D){
        this.mainModel = mainModel;
        this.mainGameThread = mainGameThread;

        //init LWJGL
        GLFWErrorCallback.createPrint(System.err).set();

        if ( !GLFW.glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // the window will be resizable

        // Create the window
        mainModel.setWindow(GLFW.glfwCreateWindow(600, 400, "Java MVC game", NULL, NULL));
        if ( mainModel.getWindow() == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(mainModel.getWindow());
        // Make the window visible
        GLFW.glfwShowWindow(mainModel.getWindow());

        GL.createCapabilities();

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        //Make transparency work
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        // Set the clear color
        GL11.glClearColor(0.0f, 0.3f, 0.8f, 0.0f);

        mainModel.setMainShader2D(Shader.compileShader(mainShaderName2D));
        mainModel.setMainShader3D(Shader.compileShader(mainShaderName3D));
        mainModel.setCamera2D(new Camera2D(mainModel.getMainShader2D(), 600, 400));
        mainModel.setTextDrawer(new TextDrawer("./img/ASCII-normal.png"));

        GLFW.glfwSetWindowSizeCallback(mainModel.getWindow(), new GLFWWindowSizeCallback(){
            @Override
            public void invoke(long window, int width, int height){
                mainModel.getCamera2D().setSceenSize(width, height);
                GL11.glViewport(0, 0, width, height);
                mainGameThread.screenResizeEvent();
            }
        });
    }

    public void startRender() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    public void endRender() {
        GLFW.glfwSwapBuffers(mainModel.getWindow()); // swap the color buffers
    }

    public void update() {
        mainModel.getCamera2D().update();
    }

    public void deInit() {
        Shader.deleteShader(mainModel.getMainShader2D());
        Shader.deleteShader(mainModel.getMainShader3D());
        Callbacks.glfwFreeCallbacks(mainModel.getWindow());
        GLFW.glfwDestroyWindow(mainModel.getWindow());

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}
