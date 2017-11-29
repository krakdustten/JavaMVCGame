package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera2D {
    private int shader;

    private int height;
    private int width;
    private float xPos;
    private float yPos;
    private float zoom;
    private boolean changed = true;

    private Matrix4f all;

    public Camera2D(int shader, int width, int height){
        this(shader, width, height, 0, 0, 1);
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
        if(changed){
            Matrix4f projection = new Matrix4f().ortho2D(-width/2, width/2, -height/2, height/2);
            projection.scale(zoom);
            Matrix4f position = new Matrix4f().setTranslation(new Vector3f(xPos, yPos, 0));
            all = new Matrix4f();
            all = projection.mul(position, all);

            Shader.bind(shader);
            Shader.setUniform(shader, "projection", all);
            changed = false;
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
        changed = true;
    }

    public void setScreenPosition(float xPos, float yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        changed = true;
    }

    public void setZoom(float zoom){
        this.zoom = zoom;
        changed = true;
    }
    public void setxPos(float xPos) {
        this.xPos = xPos;
        changed = true;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
        changed = true;
    }
}
