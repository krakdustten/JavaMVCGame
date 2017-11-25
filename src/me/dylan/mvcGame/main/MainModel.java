package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.TextDrawer;

public class MainModel {
    private long window;
    private int mainShader;
    private Camera3D camera;
    private TextDrawer textDrawer;

    private double mouseX, mouseY;
    private float mouseXWorld, mouseYWorld;

    void setWindow(long window) { this.window = window; }
    void setMainShader(int mainShader) { this.mainShader = mainShader; }
    void setCamera(Camera3D camera) { this.camera = camera; }
    void setTextDrawer(TextDrawer textDrawer) { this.textDrawer = textDrawer; }

    void setMouseX(double mouseX){
        this.mouseX = mouseX;
        this.mouseXWorld = (float) ((mouseX - camera.getWidth() / 2 - camera.getxPos()) / camera.getZoom());
 }
    void setMouseY(double mouseY){
        this.mouseY = mouseY;
        this.mouseYWorld = (float) ((-mouseY + camera.getHeight() / 2 - camera.getyPos()) / camera.getZoom());
    }

    public double getMouseX(){return mouseX; }
    public double getMouseY(){return mouseY; }

    public long getWindow() { return window; }
    public int getMainShader() { return mainShader; }
    public Camera3D getCamera() { return camera; }
    public TextDrawer getTextDrawer() { return textDrawer; }

    public float getMouseXWorld() { return mouseXWorld; }
    public float getMouseYWorld() { return mouseYWorld; }
}
