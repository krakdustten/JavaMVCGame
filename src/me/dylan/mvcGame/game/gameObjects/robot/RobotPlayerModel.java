package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.GameModel;
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
        float rot = (float) Math.atan2((motorLSpeed - motorRSpeed), 50);
        float dx = mov * (float) Math.cos(rotation);
        float dy = mov * (float) Math.sin(rotation);

        calculateHit(dx, dy, rot);


        rotation += rot;
        change = true;
    }

    private void calculateHit(float dx, float dy, float rot) {
        //TODO redo hitboxes (circle of 0.5)

        float newX = x + dx;
        float newY = y + dy;
        float newRot = rotation + rot;

        float[] outers = calculateOuterPoints(newX, newY, newRot);
        if(oldOuters == null) oldOuters = outers;

        int finishX = parent.getFinishX();
        int finishY = parent.getFinishY();
        boolean finish = true;
        float xChange = 0.0f;
        float yChange = 0.0f;

        for(int i = 0; i < 8; i++){
            float xTest = outers[i] + xChange;
            float yTest = outers[i + 8] + yChange;
            float xOld = oldOuters[i];
            float yOld = oldOuters[i + 8];

            if(parent.getTileID((int) xTest, (int) yOld) == Tiles.WALL_ID){
                if((x + 0.5f) < xTest) {
                    if(Math.abs((int) xTest - xTest) < Math.abs(xTest - xOld))xChange = ((int) xTest - xTest);
                }else {
                    if(Math.abs((int) xTest + 1 - xTest) < Math.abs(xTest - xOld))xChange = ((int) xTest + 1 - xTest);
                }
            }


            if(parent.getTileID((int) xOld, (int) yTest) == Tiles.WALL_ID){
                if((y + 0.5f) < yTest)
                    yChange = ((int) yTest - yTest);
                else
                    yChange = ((int) yTest + 1 - yTest);
            }

            //Check for finish
            if(!(finishX < xTest && finishX + 3 > xTest && finishY < yTest && finishY + 3 > yTest))
                finish = false;
        }

        if(finish) {
            parent.setWon(true);
            System.out.println(parent.getGameTime());
        }


        x = newX + xChange;
        y = newY + yChange;
        oldOuters = calculateOuterPoints(newX + xChange * 1.1f, newY + yChange * 1.1f, newRot);
    }

    private float[] calculateOuterPoints(float x, float y, float rot){
        float sin = (float) Math.sin(rot);
        float cos = (float) Math.cos(rot);
        float xT = x - (x + 0.5f);
        float yT = y - (y + 0.5f);

        float[] outers = new float[16];
        outers[ 0] = (xT +  7.0f/32) * cos - (yT +  0.0f/32) * sin + (x + 0.5f);// 7,  0
        outers[ 8] = (xT +  7.0f/32) * sin + (yT +  0.0f/32) * cos + (y + 0.5f);// 7,  0
        outers[ 1] = (xT + 25.0f/32) * cos - (yT +  0.0f/32) * sin + (x + 0.5f);//25,  0
        outers[ 9] = (xT + 25.0f/32) * sin + (yT +  0.0f/32) * cos + (y + 0.5f);//25,  0
        outers[ 2] = (xT +  7.0f/32) * cos - (yT + 32.0f/32) * sin + (x + 0.5f);// 7, 32
        outers[10] = (xT +  7.0f/32) * sin + (yT + 32.0f/32) * cos + (y + 0.5f);// 7, 32
        outers[ 3] = (xT + 25.0f/32) * cos - (yT + 32.0f/32) * sin + (x + 0.5f);//25, 32
        outers[11] = (xT + 25.0f/32) * sin + (yT + 32.0f/32) * cos + (y + 0.5f);//25, 32
        outers[ 4] = (xT +  0.0f/32) * cos - (yT + 13.0f/32) * sin + (x + 0.5f);// 0, 13
        outers[12] = (xT +  0.0f/32) * sin + (yT + 13.0f/32) * cos + (y + 0.5f);// 0, 13
        outers[ 5] = (xT +  0.0f/32) * cos - (yT + 21.0f/32) * sin + (x + 0.5f);// 0, 21
        outers[13] = (xT +  0.0f/32) * sin + (yT + 21.0f/32) * cos + (y + 0.5f);// 0, 21
        outers[ 6] = (xT + 32.0f/32) * cos - (yT + 13.0f/32) * sin + (x + 0.5f);//32, 13
        outers[14] = (xT + 32.0f/32) * sin + (yT + 13.0f/32) * cos + (y + 0.5f);//32, 13
        outers[ 7] = (xT + 32.0f/32) * cos - (yT + 21.0f/32) * sin + (x + 0.5f);//32, 21
        outers[15] = (xT + 32.0f/32) * sin + (yT + 21.0f/32) * cos + (y + 0.5f);//32, 21

        return outers;
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

    //TODO show debug on screen (leds, small text)
    //TODO update robot on code runner
}
