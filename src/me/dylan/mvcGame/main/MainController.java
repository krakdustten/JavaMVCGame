package me.dylan.mvcGame.main;

import me.dylan.mvcGame.state.StateHandler;
import org.lwjgl.glfw.GLFW;


public class MainController{
    private StateHandler stateHandler;

    public MainController(long window2, StateHandler stateHandler){
        GLFW.glfwSetKeyCallback(window2, this::keyboardEvent);
        GLFW.glfwSetCursorPosCallback(window2, this::mousePosEvent);
        GLFW.glfwSetMouseButtonCallback(window2, this::mouseButtonEvent);
        GLFW.glfwSetScrollCallback(window2, this::scrollEvent);

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
        stateHandler.mousePosEvent(window, xPos, yPos);
    }

    private void mouseButtonEvent(long window, int button, int action, int mods) {
        stateHandler.mouseButtonEvent(window, button, action, mods);
    }

    private void scrollEvent(long window, double xOffset, double yOffset) {
        stateHandler.scrollEvent(window, xOffset, yOffset);
    }
}


