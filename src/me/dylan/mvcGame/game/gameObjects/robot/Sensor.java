package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.MapModel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Sensor {
    protected MapModel mapModel;

    protected float x, y;
    protected String[] name;
    protected int[] ID;

    protected boolean changed = false;

    public Sensor(MapModel mapModel, float x, float y, String[] name){
        this.mapModel = mapModel;

        this.x = x;
        this.y = y;
        this.name = name;
    }

    public abstract void calculateOutput(float[] data);

    public float getX() { return x; }
    public float getY() { return y; }
    public String[] getNames() { return name; }

    public float[] getRealPositionInWorld(){return getRealPositionInWorld(x , y);}
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

    public void setID(int[] ID){this.ID = ID; changed = true;}

    public int[] getID() {
        return ID;
    }

    public void saveSensor(DataOutputStream os) throws IOException {
        os.writeFloat(x);
        os.writeFloat(y);
        os.writeInt(name.length);
        for(int i = 0; i < name.length; i++)
            writeString(os, name[i]);
    }

    public void loadSensor(DataInputStream is) throws IOException {
        x = is.readFloat();
        y = is.readFloat();
        name = new String[is.readInt()];
        for(int i = 0; i < name.length; i++)
            name[i] = readString(is);
    }

    protected void writeString(DataOutputStream os, String s) throws IOException {
        char[] chars = s.toCharArray();
        os.writeInt(chars.length);
        for(int i = 0; i < chars.length; i++) os.writeChar(chars[i]);
    }

    protected String readString(DataInputStream is) throws IOException {
        int chars = is.readInt();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < chars; i++) stringBuilder.append(is.readChar());
        return stringBuilder.toString();
    }

    public static Sensor getNewSensorFromId(int id, MapModel mapModel){
        switch (id){
            case 1: //distance
                return new DistanceSensor(mapModel, 0, 0, 0, "");
            default:
                return null;
        }
    }
    public int getType() { return -1; }
    public String getTypeName() { return "DEFAULT"; }

    public boolean getChanged() { return changed; }

    public void setX(float x) { this.x = x; changed = true;}
    public void setY(float y) { this.y = y; changed = true;}

    public MapModel getMapModel() {
        return mapModel;
    }
    public void setName(int i, String newValue) {
        if(name.length > i)
            name[i] = newValue;
        changed = true;
    }

    public void setChanged(boolean changed) { this.changed = changed; }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }
}
