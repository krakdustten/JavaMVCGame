package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.MapModel;

import java.util.ArrayList;

public class RobotModel {
    private MapModel parent;

    private float x, y, rotation;
    private float motorLPos, motorRPos;//1 rot = 1 tile

    private boolean change = true;

    private Sensor[] sensors = new Sensor[0];

    public RobotModel(MapModel parent){
        this.parent = parent;

        x = parent.getStartX() + 1;
        y = parent.getStartY() + 1;

        if(parent.getRobot() != null) {
            Sensor[] sensors = parent.getRobot().getSensors();
            this.sensors = new Sensor[sensors.length];
            for(int i = 0; i < sensors.length; i++)
                this.sensors[i] = sensors[i];
        }
    }

    /*GETTERS*/

    public MapModel getParent() { return parent; }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getRotation() { return rotation; }

    public float getMoterLPos() { return motorLPos; }
    public float getMoterRPos() { return motorRPos; }

    public Sensor[] getSensors() { return sensors; }
    public boolean getChanged(){return change;}


    /*SETTERS*/
    public void setX(float x) { this.x = x; change = true;}
    public void setY(float y) { this.y = y; change = true;}
    public void setRotation(float rotation) { this.rotation = rotation; change = true;}

    public void setMotorLPos(float motorLPos) { this.motorLPos = motorLPos; change = true;}
    public void setMotorRPos(float motorRPos) { this.motorRPos = motorRPos; change = true;}

    /*OTHER SMALL LOGIC*/
    public void addSensor(Sensor sensor){
        Sensor[] sensors = new Sensor[this.sensors.length + 1];
        for(int i = 0; i < this.sensors.length; i++)
            sensors[i] = this.sensors[i];
        sensors[sensors.length - 1] = sensor;
        this.sensors = sensors;
    }

    public void removeSensor(int i){
        Sensor[] sensors = new Sensor[this.sensors.length - 1];
        for(int j = 0, k = 0; k < this.sensors.length; j++, k++) {
            sensors[j] = this.sensors[k];
            if(j == i) k++;
        }
        this.sensors = sensors;
    }

    public void setChange(boolean change) {this.change = change; }
}
