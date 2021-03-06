package me.dylan.mvcGame.game.gameViewers;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.robot.DistanceSensor;
import me.dylan.mvcGame.game.gameObjects.robot.RobotModel;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;

/**
 * The viewer for the sensors on the robot.
 *
 * @author Dylan Gybels
 */
public class RobotSensorViewer {
    private GameModel gameModel;
    private RobotModel model;

    private int vbo;
    private int texture;

    /**
     * Create a new sensor viewer.
     *
     * @param gameModel The game model.
     */
    public RobotSensorViewer(GameModel gameModel){
        this.gameModel = gameModel;
        this.model = gameModel.getRobot();

        vbo = VBODrawer2D.createBufferId();
        texture = Texture.createImageId("img/Sensors.png");
        update();
    }

    /**
     * Update the buffers on the graphics card if needed.
     */
    public void update(){
        //if(!model.isChanged())return;

        Sensor[] sensors = model.getSensors();

        float[] vertexes = new float[VBODrawer2D.calcArraySizeForSquares(VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, sensors.length)];
        int offset = 0;

        float xStart = model.getX();
        float yStart = model.getY();
        for(Sensor sensor : sensors){
            int texbX;
            int texbY;
            int rot;
            if(sensor instanceof DistanceSensor){
                texbX = 0;
                texbY = 0;
                rot = (int)(((DistanceSensor) sensor).getRotation() * 8/Math.PI);
            }else{
                texbX = 1;
                texbY = 0;
                rot = 0;
            }

            offset = VBODrawer2D.draw2DSquareRotCenter(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                    (xStart + sensor.getX() - 0.09375f) * 64, (yStart + sensor.getY() - 0.09375f) * 64, 16, 16,
                    model.getRotation(), xStart * 64 + 32, yStart * 64 + 32,1, 1, 1, 1,
                    texbX * 0.125f + (rot % 4) * 0.03125f, texbY * 0.125f + (rot / 4) * 0.03125f, 0.03125f, 0.03125f);
        }

        VBODrawer2D.writeBufToMem(vbo, vertexes);
    }

    /**
     * Render the buffers on the graphics card.
     */
    public void render(){
        VBODrawer2D.drawVBO(gameModel.getMainModel(), vbo, texture, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, VBODrawer2D.calcDrawAmountForSquares(model.getSensors().length));
    }

    /**
     * Clear all vars and distroy.
     */
    public void distroy() {
        VBODrawer2D.deleteVBO(vbo);
        Texture.deleteImage(texture);
    }
}
