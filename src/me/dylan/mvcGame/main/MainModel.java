package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.TextDrawer;

//TODO javadoc
public class MainModel {
    private long window;
    private int mainShader2D;
    private Camera2D camera2D;

    private TextDrawer textDrawer;

    private double mouseX, mouseY;
    private float mouseXWorld, mouseYWorld;

    private MainFXContainer fxContainer = new MainFXContainer();

    private String gameFileToLoad = "";

    void setWindow(long window) { this.window = window; }
    void setMainShader2D(int mainShader2D) { this.mainShader2D = mainShader2D; }
    void setCamera2D(Camera2D camera2D) { this.camera2D = camera2D; }
    void setTextDrawer(TextDrawer textDrawer) { this.textDrawer = textDrawer; }

    void setMouseX(double mouseX){
        this.mouseX = mouseX;
        this.mouseXWorld = (float) ((mouseX - camera2D.getWidth() / 2) / camera2D.getZoom() - camera2D.getxPos());
    }
    void setMouseY(double mouseY){
        this.mouseY = mouseY;
        this.mouseYWorld = (float) ((-mouseY + camera2D.getHeight() / 2) / camera2D.getZoom() - camera2D.getyPos());
    }

    public void setGameFileToLoad(String gameFileToLoad){ this.gameFileToLoad = gameFileToLoad; }

    public double getMouseX(){return mouseX; }
    public double getMouseY(){return mouseY; }

    public long getWindow() { return window; }
    public int getMainShader2D() { return mainShader2D; }
    public Camera2D getCamera2D() { return camera2D; }

    public TextDrawer getTextDrawer() { return textDrawer; }

    public float getMouseXWorld() { return mouseXWorld; }
    public float getMouseYWorld() { return mouseYWorld; }

    public MainFXContainer getFxContainer() { return fxContainer; }

    public String getGameFileToLoad() { return gameFileToLoad; }

    public void distroy() {
        fxContainer.distroy();
        fxContainer = null;
    }
}
