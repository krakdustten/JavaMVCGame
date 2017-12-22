package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.TextDrawer;

/**
 * This class is the main model of the base code.
 * This holds all of the variables needed to draw and move the screen and some input variables.
 *
 * @author Dylan Gybels
 */
public class MainModel {
    private long window;
    private int mainShader2D;
    private Camera2D camera2D;

    private TextDrawer textDrawer;

    private double mouseX, mouseY;
    private float mouseXWorld, mouseYWorld;

    private MainFXContainer fxContainer = new MainFXContainer();

    private String gameFileToLoad = "";

    /*SETTERS*/

    /**
     * Set the window reference of the main window.
     * @param window The window reference.
     */
    void setWindow(long window) { this.window = window; }

    /**
     * Set the main shader.
     * @param mainShader2D The shader
     */
    void setMainShader2D(int mainShader2D) { this.mainShader2D = mainShader2D; }

    /**
     * Set the camera.
     * @param camera2D The camera.
     */
    void setCamera2D(Camera2D camera2D) { this.camera2D = camera2D; }

    /**
     * Set the text drawer.
     * @param textDrawer The text drawer.
     */
    void setTextDrawer(TextDrawer textDrawer) { this.textDrawer = textDrawer; }

    /**
     * Set the mouse x position.
     * @param mouseX The mouse x position.
     */
    void setMouseX(double mouseX){
        this.mouseX = mouseX;
        this.mouseXWorld = (float) ((mouseX - camera2D.getWidth() / 2) / camera2D.getZoom() - camera2D.getxPos());
    }

    /**
     * Set the mouse y position.
     * @param mouseY The mouse Y position
     */
    void setMouseY(double mouseY){
        this.mouseY = mouseY;
        this.mouseYWorld = (float) ((-mouseY + camera2D.getHeight() / 2) / camera2D.getZoom() - camera2D.getyPos());
    }

    /**
     * Set the game file we need to load in the next state.
     * @param gameFileToLoad The game file to load.
     */
    public void setGameFileToLoad(String gameFileToLoad){ this.gameFileToLoad = gameFileToLoad; }

    /*GETTERS*/

    /**
     * Get the mouse x position.
     * @return The mouse x position.
     */
    public double getMouseX(){return mouseX; }

    /**
     * Get the mouse y position.
     * @return The mouse y position.
     */
    public double getMouseY(){return mouseY; }

    /**
     * Get the main window reference.
     * @return The main window reference.
     */
    public long getWindow() { return window; }

    /**
     * Get the main shader reference.
     * @return The main shader reference.
     */
    public int getMainShader2D() { return mainShader2D; }

    /**
     * Get the camera.
     * @return The camera.
     */
    public Camera2D getCamera2D() { return camera2D; }

    /**
     * Get the text drawer.
     * @return The text drawer.
     */
    public TextDrawer getTextDrawer() { return textDrawer; }

    /**
     * Get the mouse x position translated to in world positions.
     * @return The mouse x position in world.
     */
    public float getMouseXWorld() { return mouseXWorld; }

    /**
     * Get the mouse y position translated to in world positions.
     * @return The mouse y position in world.
     */
    public float getMouseYWorld() { return mouseYWorld; }

    /**
     * Get the FX container.
     * @return The FX container.
     */
    public MainFXContainer getFxContainer() { return fxContainer; }

    /**
     * Get the game file to load.
     * @return The game file to load.
     */
    public String getGameFileToLoad() { return gameFileToLoad; }

    /**
     * Clean up the model.
     */
    public void distroy() {
        fxContainer.distroy();
        fxContainer = null;
    }
}
