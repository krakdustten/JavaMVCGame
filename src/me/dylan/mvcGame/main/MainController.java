package me.dylan.mvcGame.main;

import me.dylan.mvcGame.state.StateHandler;
import org.lwjgl.glfw.GLFW;

//TODO javadoc
public class MainController{
    private StateHandler stateHandler;
    private MainModel mainModel;

    public MainController(MainModel mainModel, StateHandler stateHandler){
        this.mainModel = mainModel;

        GLFW.glfwSetKeyCallback(mainModel.getWindow(), this::keyboardEvent);
        GLFW.glfwSetCursorPosCallback(mainModel.getWindow(), this::mousePosEvent);
        GLFW.glfwSetMouseButtonCallback(mainModel.getWindow(), this::mouseButtonEvent);
        GLFW.glfwSetScrollCallback(mainModel.getWindow(), this::scrollEvent);

        this.stateHandler = stateHandler;
    }

    public void update() {
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        GLFW.glfwPollEvents();
    }

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


