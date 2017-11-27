package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.TextDrawer;

public class MainModel {
    private long window;
    private int mainShader2D;
    private int mainShader3D;
    private Camera2D camera2D;
    private Camera3D camera3D;

    private TextDrawer textDrawer;

    private double mouseX, mouseY;
    private float mouseXWorld, mouseYWorld;

    void setWindow(long window) { this.window = window; }
    void setMainShader2D(int mainShader2D) { this.mainShader2D = mainShader2D; }
    void setMainShader3D(int mainShader3D) { this.mainShader3D = mainShader3D; }
    void setCamera2D(Camera2D camera2D) { this.camera2D = camera2D; }
    void setCamera3D(Camera3D camera3D) { this.camera3D = camera3D; }
    void setTextDrawer(TextDrawer textDrawer) { this.textDrawer = textDrawer; }

    void setMouseX(double mouseX){
        this.mouseX = mouseX;
        this.mouseXWorld = (float) ((mouseX - camera2D.getWidth() / 2 - camera2D.getxPos()) / camera2D.getZoom());
 }
    void setMouseY(double mouseY){
        this.mouseY = mouseY;
        this.mouseYWorld = (float) ((-mouseY + camera2D.getHeight() / 2 - camera2D.getyPos()) / camera2D.getZoom());
    }

    public double getMouseX(){return mouseX; }
    public double getMouseY(){return mouseY; }

    public long getWindow() { return window; }
    public int getMainShader2D() { return mainShader2D; }
    public int getMainShader3D() { return mainShader3D; }
    public Camera2D getCamera2D() { return camera2D; }
    public Camera3D getCamera3D() { return camera3D; }

    public TextDrawer getTextDrawer() { return textDrawer; }

    public float getMouseXWorld() { return mouseXWorld; }
    public float getMouseYWorld() { return mouseYWorld; }
}
