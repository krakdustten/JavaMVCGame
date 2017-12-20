package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.Tiles;

import java.util.ArrayList;

public class RobotPlayerModel {
    private GameModel parent;

    private float x, y, rotation;
    private float motorLSpeedTop, motorRSpeedTop;
    private float motorLPos, motorRPos;//1 rot = 1 tile
    private float motorLSpeed, motorRSpeed;//1 rot/s = 1 tile/s

    private float[] oldOuters;

    private ArrayList<Sensor> sensors = new ArrayList<>();
    private String[] sensorNames;
    private float[] sensorValues;
    private ArrayList<DebugActuators> debugActuators = new ArrayList<>();

    private boolean change = true;

    public RobotPlayerModel(GameModel parent){
        this.parent = parent;

        x = parent.getStartX() + 1;
        y = parent.getStartY() + 1;
    }

    public boolean isChanged(){return change;}
    public void changesDone() {change = false; }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getRotation() { return rotation; }

    public float getMoterLPos() { return motorLPos; }
    public float getMoterRPos() { return motorRPos; }

    public float getMoterLSpeed() { return motorLSpeed; }
    public float getMoterRSpeed() { return motorRSpeed; }

    public float getMoterLSpeedTop() { return motorLSpeed; }
    public float getMoterRSpeedTop() { return motorRSpeed; }

    public void setMoterLSpeedTop(float moterLSpeedTop) {
        this.motorLSpeedTop = moterLSpeedTop;
        if(moterLSpeedTop > 2.56f) this.motorLSpeedTop = 2.56f;
        if(moterLSpeedTop < -2.56f) this.motorLSpeedTop = -2.56f;
    }
    public void setMoterRSpeedTop(float moterRSpeedTop) {
        this.motorRSpeedTop = moterRSpeedTop;
        if(moterRSpeedTop > 2.56f) this.motorRSpeedTop = 2.56f;
        if(moterRSpeedTop < -2.56f) this.motorRSpeedTop = -2.56f;
    }

    public void calculateMovement(){
        motorLSpeed += (motorLSpeedTop - motorLSpeed) * 0.05f;
        motorRSpeed += (motorRSpeedTop - motorRSpeed) * 0.05f;

        motorLPos = (motorLPos + motorLSpeed / 25 + 1.0f) % 1.0f;
        motorRPos = (motorRPos + motorRSpeed / 25 + 1.0f) % 1.0f;

        float mov = (motorLSpeed + motorRSpeed) / 100;
        float rot = (float) Math.atan2((motorRSpeed - motorLSpeed), 50);
        float dx = mov * (float) Math.cos(rotation);
        float dy = mov * (float) Math.sin(rotation);

        calculateHit(dx, dy, rot);

        rotation += rot;
        change = true;
    }

    private void calculateHit(float dx, float dy, float rot) {
        float xOut = x + dx;
        float yOut = y + dy;

        int xBase = (int) xOut + 1;
        int yBase = (int) yOut + 1;
        float xSub = xOut - xBase + 0.5f;
        float ySub = yOut - yBase + 0.5f;

        float ySub1 = (float) (Math.sqrt(0.25 - xSub * xSub) + ySub + yBase);
        float ySub2 = (float) (-Math.sqrt(0.25 - xSub * xSub) + ySub + yBase);
        float xSub1 = (float) (Math.sqrt(0.25 - ySub * ySub) + xSub + xBase);
        float xSub2 = (float) (-Math.sqrt(0.25 - ySub * ySub) + xSub + xBase);

        int i = 0;
        float[] hitpoints = new float[4];

        if(parent.getTileID(xBase, (int) ySub1) == Tiles.WALL_ID ^ parent.getTileID(xBase - 1, (int) ySub1) == Tiles.WALL_ID) { //wall on one of the 2 sides
            hitpoints[i++] = xBase;
            hitpoints[i++] = ySub1;
        }

        if(parent.getTileID((int) xSub1, yBase) == Tiles.WALL_ID ^ parent.getTileID((int) xSub1, yBase - 1) == Tiles.WALL_ID) {
            hitpoints[i++] = xSub1;
            hitpoints[i++] = yBase;
        }

        if(parent.getTileID(xBase, (int) ySub2) == Tiles.WALL_ID ^ parent.getTileID(xBase - 1, (int) ySub2) == Tiles.WALL_ID) {
            if(i==4) i = 2;
            hitpoints[i++] = xBase;
            hitpoints[i++] = ySub2;
        }

        if(parent.getTileID((int) xSub2, yBase) == Tiles.WALL_ID ^ parent.getTileID((int) xSub2, yBase - 1) == Tiles.WALL_ID) {
            if(i==4) i = 2;
            hitpoints[i++] = xSub2;
            hitpoints[i++] = yBase;
        }

        if(i==4) {
            float centerOfHitX;
            float centerOfHitY;

            { //check for line
                centerOfHitX = (hitpoints[0] + hitpoints[2]) / 2;
                centerOfHitY = (hitpoints[1] + hitpoints[3]) / 2;

                float distMid = (float) Math.sqrt((xOut + 0.5f - centerOfHitX) * (xOut + 0.5f - centerOfHitX) + (yOut + 0.5f - centerOfHitY) * (yOut + 0.5f - centerOfHitY));
                float distEdge = 0.5f - distMid;

                float xMov = (distEdge * (xOut + 0.5f - centerOfHitX)) / distMid;
                float yMov = (distEdge * (yOut + 0.5f - centerOfHitY)) / distMid;

                xOut += xMov;
                yOut += yMov;
            }
            if(hitpoints[0] != hitpoints[2] && hitpoints[1] != hitpoints[3]){//check for corner
                centerOfHitX = xBase;
                centerOfHitY = yBase;

                float distMid = (float) Math.sqrt((xOut + 0.5f - centerOfHitX) * (xOut + 0.5f - centerOfHitX) + (yOut + 0.5f - centerOfHitY) * (yOut + 0.5f - centerOfHitY));
                float distEdge = 0.5f - distMid;

                float xMov = (distEdge * (xOut + 0.5f - centerOfHitX)) / distMid;
                float yMov = (distEdge * (yOut + 0.5f - centerOfHitY)) / distMid;

                xOut += xMov;
                yOut += yMov;
            }
        }
        x = xOut;
        y = yOut;
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        change = true;
    }

    public ArrayList<Sensor> getAllSensors() {
        return sensors;
    }

    public void setSensorNames(String[] sensorNames) {
        this.sensorNames = sensorNames;
        this.sensorValues = new float[sensorNames.length];
    }

    public float[] getSensorValues(){return sensorValues;}

    public String[] getSensorNames() {
        return sensorNames;
    }
}
