package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.TextDrawer;

public class MainModel {
    private long window;
    private int mainShader;
    private Camera camera;
    private TextDrawer textDrawer;

    private double mouseX, mouseY;

    void setWindow(long window) { this.window = window; }
    void setMainShader(int mainShader) { this.mainShader = mainShader; }
    void setCamera(Camera camera) { this.camera = camera; }
    void setTextDrawer(TextDrawer textDrawer) { this.textDrawer = textDrawer; }

    void setMouseX(double mouseX){this.mouseX = mouseX;}
    void setMouseY(double mouseY){this.mouseY = mouseY;}

    public long getWindow() { return window; }
    public int getMainShader() { return mainShader; }
    public Camera getCamera() { return camera; }
    public TextDrawer getTextDrawer() { return textDrawer; }

    public double getMouseX(){return mouseX; }
    public double getMouseY(){return mouseY; }
}
