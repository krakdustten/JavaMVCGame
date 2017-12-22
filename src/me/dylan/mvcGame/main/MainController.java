package me.dylan.mvcGame.main;

import me.dylan.mvcGame.state.StateHandler;
import org.lwjgl.glfw.GLFW;

/**
 * This class is the main controller of the base code.
 * This handles all of the event handler of the keyboard and mouse.
 *
 * @author Dylan Gybels
 */
public class MainController{
    private StateHandler stateHandler;
    private MainModel mainModel;

    /**
     * Create a new main controller.
     *
     * @param mainModel The main model.
     * @param stateHandler The main state handler.
     */
    public MainController(MainModel mainModel, StateHandler stateHandler){
        this.mainModel = mainModel;

        //event handlers
        GLFW.glfwSetKeyCallback(mainModel.getWindow(), this::keyboardEvent);
        GLFW.glfwSetCursorPosCallback(mainModel.getWindow(), this::mousePosEvent);
        GLFW.glfwSetMouseButtonCallback(mainModel.getWindow(), this::mouseButtonEvent);
        GLFW.glfwSetScrollCallback(mainModel.getWindow(), this::scrollEvent);

        this.stateHandler = stateHandler;
    }

    /**
     * Update the main model.
     */
    public void update() {
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        GLFW.glfwPollEvents();
    }

    /**
     * De init the main model.
     */
    public void deInit() {
    }

    private void keyboardEvent(long window, int key, int scancode, int action, int mods){
        stateHandler.keyboardEvent(window, key, scancode, action, mods);
    }

    private void mousePosEvent(long window, double xPos, double yPos) {
        mainModel.setMouseX(xPos);
        mainModel.setMouseY(yPos);
        stateHandler.mousePosEvent(window);
    }

    private void mouseButtonEvent(long window, int button, int action, int mods) {
        stateHandler.mouseButtonEvent(window, button, action, mods);
    }

    private void scrollEvent(long window, double xOffset, double yOffset) {
        stateHandler.scrollEvent(window, xOffset, yOffset);
    }
}


