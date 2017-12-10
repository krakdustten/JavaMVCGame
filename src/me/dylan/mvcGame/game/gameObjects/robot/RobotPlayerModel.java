package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.GameModel;
import me.dylan.mvcGame.game.gameObjects.Tiles;

import java.util.ArrayList;

public class RobotPlayerModel {
    private GameModel parent;

    private float x, y, rotation;
    private float moterLPos, moterRPos;//1 rot = 1 tile
    private float moterLSpeed, moterRSpeed;//1 rot/s = 1 tile/s

    private ArrayList<Sensor> sensors = new ArrayList<>();
    private ArrayList<DebugActuators> debugActuators = new ArrayList<>();

    private boolean change = true;

    public RobotPlayerModel(GameModel parent){
        this.parent = parent;

        //TODO set position to start coordinates
        x = 5;
        y = 26;
    }

    public boolean isChanged(){return change;}
    public void changesDone() {change = false; }

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
        moterLPos = (moterLPos + moterLSpeed / 25 + 1.0f) % 1.0f;
        moterRPos = (moterRPos + moterRSpeed / 25 + 1.0f) % 1.0f;

        float mov = (moterLSpeed + moterRSpeed) / 100;
        float rot = (float) Math.atan2((moterLSpeed - moterRSpeed), 50);
        float dx = mov * (float) Math.cos(rotation);
        float dy = mov * (float) Math.sin(rotation);

        calculateHit(dx, dy, rot);


        rotation += rot;
        change = true;
    }

    private void calculateHit(float dx, float dy, float rot) {
        float newX = x + dx;
        float newY = y + dy;
        float newRot = rotation + rot;

        float sin = (float) Math.sin(newRot);
        float cos = (float) Math.cos(newRot);
        float xT = newX - (newX + 0.5f);
        float yT = newY - (newY + 0.5f);

        float[] outers = new float[8];
        outers[0] =  xT      * cos -  yT      * sin + (newX + 0.5f);
        outers[4] =  xT      * sin +  yT      * cos + (newY + 0.5f);
        outers[1] = (xT + 1) * cos -  yT      * sin + (newX + 0.5f);
        outers[5] = (xT + 1) * sin +  yT      * cos + (newY + 0.5f);
        outers[2] = (xT + 1) * cos - (yT + 1) * sin + (newX + 0.5f);
        outers[6] = (xT + 1) * sin + (yT + 1) * cos + (newY + 0.5f);
        outers[3] =  xT      * cos - (yT + 1) * sin + (newX + 0.5f);
        outers[7] =  xT      * sin + (yT + 1) * cos + (newY + 0.5f);

        float xMin = outers[0];
        float xMax = outers[0];
        float yMin = outers[4];
        float yMax = outers[4];

        int finishX = parent.getFinishX();
        int finishY = parent.getFinishY();
        boolean finish = true;

        for(int i = 0; i < 4; i++){
            if(xMin > outers[i])xMin = outers[i];
            if(xMax < outers[i])xMax = outers[i];
            if(yMin > outers[i + 4])yMin = outers[i + 4];
            if(yMax < outers[i + 4])yMax = outers[i + 4];

            //Check for finish
            if(!(finishX < outers[i] && finishX + 3 > outers[i] && finishY < outers[i + 4] && finishY + 3 > outers[i + 4]))
                finish = false;
        }

        if(finish) {
            parent.setWon(true);
            System.out.println(parent.getGameTime());
        }

        //TODO make more precise hit detection
        //X part
        if(parent.getTileID((int) xMin, (int) y) == Tiles.WALL_ID) newX = newX + ((int) xMin + 1 - xMin);
        if(parent.getTileID((int) xMax, (int) y) == Tiles.WALL_ID) newX = newX + ((int) xMax - xMax);

        //Y part
        if(parent.getTileID((int) x, (int) yMin) == Tiles.WALL_ID) newY = newY + ((int) yMin + 1 - yMin);
        if(parent.getTileID((int) x, (int) yMax) == Tiles.WALL_ID) newY = newY + ((int) yMax - yMax);

        x = newX;
        y = newY;
    }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        change = true;
    }

    public ArrayList<Sensor> getAllSensors() {
        return sensors;
    }


    //TODO show debug on screen (leds, small text)
    //TODO make code runner
    //TODO update robot on code runner
}
