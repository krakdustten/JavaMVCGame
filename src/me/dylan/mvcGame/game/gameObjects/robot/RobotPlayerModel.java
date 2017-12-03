package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.robot.Sensor;

import java.util.ArrayList;

public class RobotPlayerModel {
    private float x, y, rotation;

    private ArrayList<Sensor> sensors = new ArrayList<>();
    private ArrayList<DebugActuators> debugActuators = new ArrayList<>();

    private boolean change = true;

    public RobotPlayerModel(){
        x = 10;
        y = 10;
    }

    public boolean isChanged(){if(change){change = false; return true;} return false;}

    public float getX() { return x; }
    public float getY() { return y; }
    public float getRotation() { return rotation; }

//TODO load method (Controller)
    //TODO make sensor calculator (Controller)
    //TODO show debug on screen (leds, small text)
    //TODO make code runner
    //TODO update robot on code runner
    //TODO make robot drawer
    //TODO make drawer for sensors
}
