package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera3D {
    private int shader3D;

    private int height;
    private int width;
    private boolean screenSizeChanged = true;

    private float xPos;
    private float yPos;
    private float zPos;
    private float xRot;
    private float yRot;
    private float zRot;
    private boolean positionChanged = true;

    private float zoom;
    private boolean zoomChanged = true;

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;

    private Matrix4f projection, position, all;

    public Camera3D(int shader, int height, int width){
        this(shader, height, width, 0, 0, 0, 0, 0, 0, 1);
    }

    public Camera3D(int shader3D, int width, int height, float xPos, float yPos, float zPos, float xRot, float yRot, float zRot, float zoom){
        this.shader3D = shader3D;

        this.height = height;
        this.width = width;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
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
            position = new Matrix4f().setTranslation(new Vector3f(xPos, yPos, zPos));
            positionChanged = false;
            all = new Matrix4f();
            all = projection.mul(position, all);
            all = all.rotateXYZ(xRot,yRot,zRot);
        }
        runtrue = runtrue || zoomChanged;
        if(runtrue){
            all.scale(zoom);
            zoomChanged = false;
            Shader.bind(shader3D);
            Shader.setUniform(shader3D, "projection", all);
        }
    }

    public void setSceenSize(int width, int height){
        this.width = width;
        this.height = height;
        screenSizeChanged = true;
    }

    public void setCameraPosition(float xPos, float yPos, float zPos){
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        positionChanged = true;
    }

    public void setCameraRotation(float xRot, float yRot, float zRot){
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
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
    public float getzPos() { return zPos; }
    public float getxRot() { return xRot; }
    public float getyRot() { return yRot; }
    public float getzRot() { return zRot; }
    public float getZoom() { return zoom; }

    public Matrix4f getProjection() {
        return all;
    }
}
