package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.MapModel;
import me.dylan.mvcGame.game.gameObjects.Tiles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The distance sensor.
 *
 * @author Dylan Gybels
 */
public class DistanceSensor extends Sensor{
    private float rotation;

    /**
     * Create a new distance sensor.
     *
     * @param mapModel The map model.
     * @param x The x position relative to the robot.
     * @param y The y position relative to the robot.
     * @param rotation The rotation relative to the robot.
     * @param name The name of the sensors.
     */
    public DistanceSensor(MapModel mapModel, float x, float y, float rotation, String name) {
        super(mapModel, x, y, new String[]{name});
        this.rotation = rotation;
    }

    /*GETTERS*/

    /**
     * Get the rotation of the sensor relative to the robot.
     * @return The rotation of the sensor relative to the robot.
     */
    public float getRotation() { return rotation; }

    /**
     * Get the type id of the current sensor.
     * @return The type id. (1)
     */
    @Override
    public int getType() { return 1; }

    /**
     * Get the name of the type.
     * @return The name of the type. ("Distance")
     */
    @Override
    public String getTypeName() { return "Distance"; }

    /*SETTERS*/

    /**
     * Set the rotation of the sensor relative to the robot.
     * @param rotation The new rotation of the sensor relative to the robot.
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
        changed = true;
    }

    /*EXTRA FUNCTIONS*/

    /**
     * Calculate the output of the sensor.
     * The data of this sensor will be set in the data array given with as index the IDs.
     *
     * @param data The data array that has to filled with sensor data.
     */
    @Override
    public void calculateOutput(float[] data) {
        float[] sensor = getRealPositionInWorld();
        float xSensor = sensor[0];
        float ySensor = sensor[1];

        float sin = (float) Math.sin(rotation + mapModel.getRobot().getRotation());
        float cos = (float) Math.cos(rotation + mapModel.getRobot().getRotation());

        //calc distance to next wall
        int nextX = cos > 0 ? (int) Math.ceil(xSensor) : (int) xSensor;
        int nextY = sin > 0 ? (int) Math.ceil(ySensor) : (int) ySensor;
        int modX = cos > 0 ? 1 : -1;
        int modY = sin > 0 ? 1 : -1;
        float xLength = Math.abs((nextX - xSensor) / cos);
        float yLength = Math.abs((nextY - ySensor) / sin);
        boolean found = false;
        while(!found){
            if(xLength < yLength){
                if(mapModel.getTileID(nextX + (modX > 0 ? 0 : -1), nextY - (modY > 0 ? 1 : 0)) == Tiles.WALL_ID) {
                    data[ID[0]] = xLength;
                    return;
                }
                nextX += modX;
                xLength = Math.abs((nextX - xSensor) / cos);
            }else{
                if(mapModel.getTileID(nextX - (modX > 0 ? 1: 0), nextY + (modY > 0 ? 0 : -1)) == Tiles.WALL_ID) {
                    data[ID[0]] = yLength;
                    return;
                }
                nextY += modY;
                yLength = Math.abs((nextY - ySensor) / sin);
            }

            if(xLength > 10 && yLength > 10)found = true;
        }
        data[ID[0]] = 10.0f;
    }

    /**
     * Save the sensor to a data output stream.
     * @param os The data output stream to write to.
     * @throws IOException When there was an IO exception.
     */
    @Override
    public void saveSensor(DataOutputStream os) throws IOException {
        super.saveSensor(os);
        os.writeFloat(rotation);
    }

    /**
     * Load the sensor from a data input stream.
     * @param is The data input stream to read from.
     * @throws IOException When there was an IO exception.
     */
    @Override
    public void loadSensor(DataInputStream is) throws IOException {
        super.loadSensor(is);
        rotation = is.readFloat();
    }
}

