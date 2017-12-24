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

/**
 * This class is hte main view of the base code.
 * It sets up the openGL and the window the it writes to.
 *
 * @author Dylan Gybels
 * @see "https://www.lwjgl.org/guide"
 */
public class MainViewer {
    private MainModel mainModel;

    /**
     * Create a new main viewer.
     * @param mainModel The main model.
     * @param mainGameThread The main game thread.
     * @param mainShaderName2D The main shader file name.
     */
    public MainViewer(MainModel mainModel, MainGameThread mainGameThread, String mainShaderName2D){
        this.mainModel = mainModel;

        GLFWErrorCallback.createPrint(System.err).set();//init LWJGL
        if ( !GLFW.glfwInit() ) throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwDefaultWindowHints();//set the default window settings
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);//the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);//the window will be resizable

        //create the window
        mainModel.setWindow(GLFW.glfwCreateWindow(600, 400, "Java MVC game", NULL, NULL));
        if ( mainModel.getWindow() == NULL ) throw new RuntimeException("Failed to create the GLFW window");


        GLFW.glfwMakeContextCurrent(mainModel.getWindow());//make the OpenGL context current
        GLFW.glfwShowWindow(mainModel.getWindow());//make the window visible
        GL.createCapabilities();

        GL11.glEnable(GL11.GL_TEXTURE_2D);//enable textures
        GLFW.glfwSwapInterval(1);//turn on V-sync

        GL11.glEnable(GL11.GL_BLEND);//make transparency work
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);//set transparency modes

        GL11.glEnable(GL11.GL_CULL_FACE);//only render one side of the vertexes
        GL11.glCullFace(GL11.GL_BACK);//we don't see the back side


        GL11.glClearColor(0.0f, 0.3f, 0.8f, 0.0f);//set the clear color

        mainModel.setMainShader2D(Shader.compileShader(mainShaderName2D));
        mainModel.setCamera2D(new Camera2D(mainModel.getMainShader2D(), 600, 400));
        mainModel.setTextDrawer(new TextDrawer("img/ASCII-normal.png"));

        GLFW.glfwSetWindowSizeCallback(mainModel.getWindow(), new GLFWWindowSizeCallback(){
            @Override
            public void invoke(long window, int width, int height){
                mainModel.getCamera2D().setSceenSize(width, height);
                GL11.glViewport(0, 0, width, height);
                mainGameThread.screenResizeEvent();
            }
        });
    }

    /**
     * Start rendering to the screen.
     */
    public void startRender() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    }

    /**
     * End rendering to the screen.
     * Put the things rendered on screen.
     */
    public void endRender() {
        GLFW.glfwSwapBuffers(mainModel.getWindow()); // swap the color buffers
    }

    /**
     * update the game model.
     */
    public void update() {
        mainModel.getCamera2D().update();
    }

    /**
     * De init the main viewer.
     */
    public void deInit() {
        Shader.deleteShader(mainModel.getMainShader2D());
        Callbacks.glfwFreeCallbacks(mainModel.getWindow());
        GLFW.glfwDestroyWindow(mainModel.getWindow());

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}
