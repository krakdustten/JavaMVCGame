package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.MapModel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The base class of every sensor.
 *
 * @author Dylan Gybels
 */
public abstract class Sensor {
    /**The map of the game we are in.*/
    protected MapModel mapModel;

    /**The position of the sensor relative to the robot.*/
    protected float x, y;
    /**The names of the sensor. There can be multiple sensors in one object. (1 sensor per variable)*/
    protected String[] name;
    /**The ID of every sensor. These will be set automatically on startup. (1 ID per name)*/
    protected int[] ID;

    /**Something changed about the sensor.*/
    protected boolean changed = false;

    /**
     * Create a new sensor.
     *
     * @param mapModel The map the sensor is in.
     * @param x The x position relative to the robot.
     * @param y The y position relative to the robot.
     * @param name The names of the sensors.
     */
    public Sensor(MapModel mapModel, float x, float y, String[] name){
        this.mapModel = mapModel;

        this.x = x;
        this.y = y;
        this.name = name;
    }

    /**
     * Calculate the output of the sensor.
     * The data of this sensor will be set in the data array given with as index the IDs.
     *
     * @param data The data array that has to filled with sensor data.
     */
    public abstract void calculateOutput(float[] data);

    /*GETTERS*/

    /**
     * Get the x position relative to the robot.
     * @return The x position relative to the robot.
     */
    public float getX() { return x; }

    /**
     * Get the y position relative to the robot.
     * @return The y position relative to the robot.
     */
    public float getY() { return y; }

    /**
     * Get all of the sensor names.
     * @return All of the sensor names.
     */
    public String[] getNames() { return name; }

    /**
     * Get the position of the sensor in the world.
     * @return The position of the sensor in a float array as [x, y].
     */
    public float[] getRealPositionInWorld(){return getRealPositionInWorld(x , y);}

    /**
     * Get the position of the sensor in the world.
     * @param xRel The x position relative to the robot.
     * @param yRel The y position relative to the robot.
     * @return The position of the sensor in a float array as [x, y].
     */
    protected float[] getRealPositionInWorld(float xRel, float yRel){
        float[] output = new float[2];
        float xRob = mapModel.getRobot().getX();
        float yRob = mapModel.getRobot().getY();
        float rotRob = mapModel.getRobot().getRotation();

        float sin = (float) Math.sin(rotRob);
        float cos = (float) Math.cos(rotRob);
        float xT = xRob + xRel - (xRob + 0.5f);
        float yT = yRob + yRel - (yRob + 0.5f);

        output[0] = xT * cos - yT * sin + (xRob + 0.5f);
        output[1] = xT * sin + yT * cos + (yRob + 0.5f);

        return output;
    }

    /**
     * Get the IDs of the sensor.
     * @return The IDs of the sensor.
     */
    public int[] getID() { return ID; }

    /**
     * Get a new sensor from the sensor type id.
     * 1 -> distance sensor.
     *
     * @param typeId The type id of the new sensor.
     * @param mapModel The map model used.
     * @return The newly created sensor.
     */
    public static Sensor getNewSensorFromId(int typeId, MapModel mapModel){
        switch (typeId){
            case 1: //distance
                return new DistanceSensor(mapModel, 0, 0, 0, "");
            default:
                return null;
        }
    }

    /**
     * Get the type id of the current sensor.
     * @return The type id.
     */
    public int getType() { return -1; }

    /**
     * Get the name of the type.
     * @return The name of the type.
     */
    public String getTypeName() { return "DEFAULT"; }

    /**
     * Get if the sensor changed somehow.
     * @return Dit the sensor change.
     */
    public boolean getChanged() { return changed; }

    /**
     * Get the map model this sensor uses.
     * @return The map model this sensor uses.
     */
    public MapModel getMapModel() {
        return mapModel;
    }

    /*SETTER*/

    /**
     * Set the x position of a sensor relative to the robot.
     * @param x The new relative x position.
     */
    public void setX(float x) { this.x = x; changed = true;}

    /**
     * Set the y position of a sensor relative to the robot.
     * @param y The new relative y position.
     */
    public void setY(float y) { this.y = y; changed = true;}

    /**
     * Set the IDs of the sensor.
     * @param ID The new IDs of the sensor.
     */
    public void setID(int[] ID){this.ID = ID; changed = true;}

    /**
     * Set the name of one of the sensors.
     * @param i The index of the name.
     * @param newValue The new name.
     */
    public void setName(int i, String newValue) {
        if(name.length > i)
            name[i] = newValue;
        changed = true;
    }

    /**
     * Set the changed parameter.
     * @param changed The new changed value.
     */
    public void setChanged(boolean changed) { this.changed = changed; }

    /**
     * Set the map model user by this sensor.
     * @param mapModel The new map model.
     */
    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    /*EXTRA FUNCTIONS*/

    /**
     * Save the sensor to a data output stream.
     * @param os The data output stream to write to.
     * @throws IOException When there was an IO exception.
     */
    public void saveSensor(DataOutputStream os) throws IOException {
        os.writeFloat(x);
        os.writeFloat(y);
        os.writeInt(name.length);
        for (String aName : name) writeString(os, aName);
    }

    /**
     * Load the sensor from a data input stream.
     * @param is The data input stream to read from.
     * @throws IOException When there was an IO exception.
     */
    public void loadSensor(DataInputStream is) throws IOException {
        x = is.readFloat();
        y = is.readFloat();
        name = new String[is.readInt()];
        for(int i = 0; i < name.length; i++)
            name[i] = readString(is);
    }

    /**
     * Write a string to a data output stream.
     * @param os The data output stream to write to.
     * @param s The string to write.
     * @throws IOException When there was an IO exception.
     */
    protected void writeString(DataOutputStream os, String s) throws IOException {
        char[] chars = s.toCharArray();
        os.writeInt(chars.length);
        for (char aChar : chars) os.writeChar(aChar);
    }

    /**
     * Read a string from a data input stream.
     * @param is The data input stream to read from.
     * @return The string read.
     * @throws IOException When there was an IO exception.
     */
    protected String readString(DataInputStream is) throws IOException {
        int chars = is.readInt();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < chars; i++) stringBuilder.append(is.readChar());
        return stringBuilder.toString();
    }
}
