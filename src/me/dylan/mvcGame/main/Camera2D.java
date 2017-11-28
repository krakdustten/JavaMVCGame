package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera2D {
    private int shader;

    private int height;
    private int width;
    private boolean screenSizeChanged = true;

    private float xPos;
    private float yPos;
    private boolean positionChanged = true;

    private float zoom;
    private boolean zoomChanged = true;

    private Matrix4f projection, position, all;

    public Camera2D(int shader, int height, int width){
        this(shader, height, width, 0, 0, 1);
    }

    public Camera2D(int shader, int width, int height, float xPos, float yPos, float zoom){
        this.shader = shader;

        this.height = height;
        this.width = width;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zoom = zoom;
        update();
    }

    public void update(){
        //TODO cleanup this mess
        boolean runtrue = screenSizeChanged || zoomChanged || positionChanged;
        if(runtrue){
            projection = new Matrix4f().ortho2D(-width/2, width/2, -height/2, height/2);
            screenSizeChanged = false;
            position = new Matrix4f().setTranslation(new Vector3f(xPos, yPos, 0));
            positionChanged = false;
            all = new Matrix4f();
            projection.scale(zoom);
            all = projection.mul(position, all);
            zoomChanged = false;
            Shader.bind(shader);
            Shader.setUniform(shader, "projection", all);
        }
    }



    public int getWidth(){return width;}
    public int getHeight() { return height; }
    public float getxPos() { return xPos; }
    public float getyPos() { return yPos; }
    public float getZoom() { return zoom; }

    public Matrix4f getProjection() {
        return all;
    }


    public void setSceenSize(int width, int height){
        this.width = width;
        this.height = height;
        screenSizeChanged = true;
    }

    public void setScreenPosition(float xPos, float yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        positionChanged = true;
    }

    public void setZoom(float zoom){
        this.zoom = zoom;
        zoomChanged = true;
    }
    public void setxPos(float xPos) {
        this.xPos = xPos;
        positionChanged = true;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
        positionChanged = true;
    }
}
