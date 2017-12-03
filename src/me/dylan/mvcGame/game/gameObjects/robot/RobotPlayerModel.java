package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.robot.Sensor;

import java.util.ArrayList;

public class RobotPlayerModel {
    private float x, y, rotation;
    private float moterLPos, moterRPos;//1 rot = 1 tile
    private float moterLSpeed, moterRSpeed;//1 rot/s = 1 tile/s

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

    public float getMoterLPos() { return moterLPos; }
    public float getMoterRPos() { return moterRPos; }

    public float getMoterLSpeed() { return moterLSpeed; }
    public float getMoterRSpeed() { return moterRSpeed; }

    public void setMoterLSpeed(float moterLSpeed) { this.moterLSpeed = moterLSpeed; }
    public void setMoterRSpeed(float moterRSpeed) { this.moterRSpeed = moterRSpeed; }

    public void calculateMovement(){
        moterLPos = (moterLPos + moterLSpeed / 10 + 1.0f) % 1.0f; //50 = UPS
        moterRPos = (moterRPos + moterRSpeed / 10 + 1.0f) % 1.0f;

        float mov = (moterLSpeed + moterRSpeed) / 100;
        float rot = (float) Math.atan2((moterLSpeed - moterRSpeed), 50);
        x += mov * (float) Math.cos(rotation);
        y += mov * (float) Math.sin(rotation);
        rotation += rot;
        change = true;
    }

    //TODO load method (Controller)
    //TODO make sensor calculator (Controller)
    //TODO show debug on screen (leds, small text)
    //TODO make code runner
    //TODO update robot on code runner
    //TODO make drawer for sensors
}
