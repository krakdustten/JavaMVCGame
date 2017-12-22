package me.dylan.mvcGame.main;

import me.dylan.mvcGame.drawers.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * This class is the camera of the base framework and creates the projection necessary to get a good view.
 *
 * @author Dylan Gybels
 */
public class Camera2D {
    private int shader;

    private int height;
    private int width;
    private float xPos;
    private float yPos;
    private float zoom;
    private boolean changed = true;

    private Matrix4f all;

    /**
     * Create a new camera with the base shader and the width and height of the screen.
     *
     * @param shader The base shader of the game.
     * @param width The width of the screen.
     * @param height The height of the screen.
     */
    public Camera2D(int shader, int width, int height){
        this(shader, width, height, 0, 0, 1);
    }

    /**
     * Create a new camera with the base shader, the width and height of the screen,
     * the position of the camera in the world and the zoom of the camera.
     *
     * @param shader The base shader of the game.
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @param xPos The x position of the camera in world.
     * @param yPos The y position of the camera in world.
     * @param zoom The zoom of the camera.
     */
    public Camera2D(int shader, int width, int height, float xPos, float yPos, float zoom){
        this.shader = shader;

        this.height = height;
        this.width = width;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zoom = zoom;
        update();
    }

    /**
     * Update the camera.
     * Updates the projection if it's necessary.
     */
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

    /*GETTERS*/

    /**
     * Get the screen width.
     * @return The screen width.
     */
    public int getWidth(){return width;}

    /**
     * Get the screen height.
     * @return The screen height.
     */
    public int getHeight() { return height; }

    /**
     * Get the x position of the camera in world.
     * @return The x position.
     */
    public float getxPos() { return xPos; }

    /**
     * Get the y position of the camera in world.
     * @return The y position.
     */
    public float getyPos() { return yPos; }

    /**
     * Get the zoom of the camera.
     * @return The zoom of the camera.
     */
    public float getZoom() { return zoom; }

    /**
     * Get the projection of the camera.
     * @return The projection of the camera.
     */
    public Matrix4f getProjection() {
        return all;
    }

    /*SETTERS*/

    /**
     * Set the screen size/
     * @param width The width of the screen.
     * @param height The height of the screen.
     */
    public void setSceenSize(int width, int height){
        this.width = width;
        this.height = height;
        changed = true;
    }

    /**
     * Set screen position.
     * @param xPos The x position.
     * @param yPos The y position.
     */
    public void setScreenPosition(float xPos, float yPos){
        this.xPos = xPos;
        this.yPos = yPos;
        changed = true;
    }

    /**
     * Set the zoom of the camera.
     * @param zoom The zoom of the camera.
     */
    public void setZoom(float zoom){
        this.zoom = zoom;
        changed = true;
    }

    /**
     * Set the x position of the camera.
     * @param xPos The x position.
     */
    public void setxPos(float xPos) {
        this.xPos = xPos;
        changed = true;
    }

    /**
     * Set the y position of the camera.
     * @param yPos The y position.
     */
    public void setyPos(float yPos) {
        this.yPos = yPos;
        changed = true;
    }
}
