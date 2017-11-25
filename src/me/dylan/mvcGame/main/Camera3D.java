package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera3D {
    private int shader;

    private int height;
    private int width;
    private boolean screenSizeChanged = true;

    private float xPos;
    private float yPos;
    private boolean positionChanged = true;

    private float zoom;
    private boolean zoomChanged = true;

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;

    private Matrix4f projection, position, all;

    public Camera3D(int shader, int height, int width){
        this(shader, height, width, 0, 0, 1);
    }

    public Camera3D(int shader, int width, int height, float xPos, float yPos, float zoom){
        this.shader = shader;

        this.height = height;
        this.width = width;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zoom = zoom;
        update();
    }

    public void update(){
        boolean runtrue = screenSizeChanged;
        if(runtrue){
            projection = new Matrix4f().perspective(FOV, (float)(width)/ height, Z_NEAR, Z_FAR);
            screenSizeChanged = false;
        }
        runtrue = runtrue || positionChanged;
        if(runtrue){
            position = new Matrix4f().setTranslation(new Vector3f(xPos, yPos, 0));
            positionChanged = false;
            all = new Matrix4f();
            all = projection.mul(position, all);
        }
        runtrue = runtrue || zoomChanged;
        if(runtrue){
            //all = all.rotateXYZ(0.2f,0.5f,0.0f);
            all.scale(zoom);
            zoomChanged = false;
            Shader.bind(shader);
            Shader.setUniform(shader, "projection", all);
        }
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

    public int getWidth(){return width;}
    public int getHeight() { return height; }
    public float getxPos() { return xPos; }
    public float getyPos() { return yPos; }
    public float getZoom() { return zoom; }

    public Matrix4f getProjection() {
        return all;
    }
}
