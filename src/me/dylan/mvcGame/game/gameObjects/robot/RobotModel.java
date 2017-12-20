package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.MapModel;

import java.util.ArrayList;

public class RobotModel {
    private MapModel parent;

    private float x, y, rotation;
    private float motorLPos, motorRPos;//1 rot = 1 tile

    private ArrayList<Sensor> sensors = new ArrayList<>();

    public RobotModel(MapModel parent){
        this.parent = parent;

        x = parent.getStartX() + 1;
        y = parent.getStartY() + 1;
    }

    /*GETTERS*/

    public MapModel getParent() { return parent; }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getRotation() { return rotation; }

    public float getMoterLPos() { return motorLPos; }
    public float getMoterRPos() { return motorRPos; }

    public Sensor[] getSensors() { return sensors.toArray(new Sensor[0]); }

    /*SETTERS*/
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setRotation(float rotation) { this.rotation = rotation; }

    public void setMotorLPos(float motorLPos) { this.motorLPos = motorLPos; }
    public void setMotorRPos(float motorRPos) { this.motorRPos = motorRPos; }

    /*OTHER SMALL LOGIC*/
    public void addSensor(Sensor sensor){ sensors.add(sensor); }
}
