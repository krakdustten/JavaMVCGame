package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.MapModel;
import me.dylan.mvcGame.game.gameObjects.Tiles;

/**
 * The robot as a player.
 *
 * @author Dylan Gybels
 */
public class RobotPlayerModel extends RobotModel{
    private float motorLSpeedTop, motorRSpeedTop;
    private float motorLSpeed, motorRSpeed;//1 rot/s = 1 tile/s

    private String[] sensorNames;
    private float[] sensorValues;

    /**
     * Create a new robot player model.
     *
     * @param parent The map model the robot is in.
     */
    public RobotPlayerModel(GameModel parent){ super(parent); }

    /**
     * Create a new robot player model.
     *
     * @param robot The robot model to copy into this object.
     * @param mapModel The map model the robot is in.
     */
    public RobotPlayerModel(RobotModel robot, MapModel mapModel) {
        super(mapModel);
    }

    /**
     * Get the speed of the left motor.
     * @return The speed of the left motor.
     */
    public float getMoterLSpeed() { return motorLSpeed; }

    /**
     * Get the speed of the right motor.
     * @return The speed of the right motor.
     */
    public float getMoterRSpeed() { return motorRSpeed; }

    /**
     * Get the sensor values.
     * @return The sensor values as a float array.
     */
    public float[] getSensorValues(){return sensorValues;}

    /**
     * Get the sensor names.
     * @return The sensor names as s String array.
     */
    public String[] getSensorNames() { return sensorNames; }

    /**
     * Set the maximum left motor speed.
     * This way we can simulate a base motor functionality.
     * @param moterLSpeedTop The new maximum left motor speed.
     */
    public void setMoterLSpeedTop(float moterLSpeedTop) {
        this.motorLSpeedTop = moterLSpeedTop;
        if(moterLSpeedTop > 2.56f) this.motorLSpeedTop = 2.56f;
        if(moterLSpeedTop < -2.56f) this.motorLSpeedTop = -2.56f;
    }

    /**
     * Set the maximum right motor speed.
     * This way we can simulate a base motor functionality.
     * @param moterRSpeedTop The new maximum right motor speed.
     */
    public void setMoterRSpeedTop(float moterRSpeedTop) {
        this.motorRSpeedTop = moterRSpeedTop;
        if(moterRSpeedTop > 2.56f) this.motorRSpeedTop = 2.56f;
        if(moterRSpeedTop < -2.56f) this.motorRSpeedTop = -2.56f;
    }

    /**
     * Set the sensor names.
     * @param sensorNames The new sensor name array.
     */
    public void setSensorNames(String[] sensorNames) {
        this.sensorNames = sensorNames;
        this.sensorValues = new float[sensorNames.length];
    }

    /**
     * Calculate one tick of movement with hit detection.
     */
    public void calculateMovement(){
        //calc motor speeds
        motorLSpeed += (motorLSpeedTop - motorLSpeed) * 0.05f;
        motorRSpeed += (motorRSpeedTop - motorRSpeed) * 0.05f;

        //calc motor positions
        setMotorLPos((getMoterLPos() + motorLSpeed / 25 + 1.0f) % 1.0f);
        setMotorRPos((getMoterRPos() + motorRSpeed / 25 + 1.0f) % 1.0f);

        //calc diff in movement and rotation
        float mov = (motorLSpeed + motorRSpeed) / 100;
        float rot = (float) Math.atan2((motorRSpeed - motorLSpeed), 50);
        float dx = mov * (float) Math.cos(getRotation());
        float dy = mov * (float) Math.sin(getRotation());

        calculateHit(dx, dy);//hit detection

        setRotation(getRotation() + rot);//set rotation
        setChange(true);//robot changed
    }

    private void calculateHit(float dx, float dy) {
        //temp new positions
        float xOut = getX() + dx;
        float yOut = getY() + dy;

        //get the grid lines that cut the robot
        int xBase = (int) xOut + 1;
        int yBase = (int) yOut + 1;
        float xSub = xOut - xBase + 0.5f;
        float ySub = yOut - yBase + 0.5f;

        //calculate the hit points of the grid lines and the hit circle of the robot
        float ySub1 = (float) (Math.sqrt(0.25 - xSub * xSub) + ySub + yBase);
        float ySub2 = (float) (-Math.sqrt(0.25 - xSub * xSub) + ySub + yBase);
        float xSub1 = (float) (Math.sqrt(0.25 - ySub * ySub) + xSub + xBase);
        float xSub2 = (float) (-Math.sqrt(0.25 - ySub * ySub) + xSub + xBase);

        //check for finish
        float finishX = getParent().getFinishX();
        float finishY = getParent().getFinishY();
        if(finishX < xSub2 && finishX + 3 > xSub1 &&
                finishY < ySub2 && finishY + 3 > ySub1)
            ((GameModel)getParent()).setWon(true);

        //check if we hit a wall
        int i = 0;
        float[] hitpoints = new float[4];
        if(getParent().getTileID(xBase, (int) ySub1) == Tiles.WALL_ID ^ getParent().getTileID(xBase - 1, (int) ySub1) == Tiles.WALL_ID) { //wall on one of the 2 sides
            hitpoints[i++] = xBase;
            hitpoints[i++] = ySub1;
        }
        if(getParent().getTileID((int) xSub1, yBase) == Tiles.WALL_ID ^ getParent().getTileID((int) xSub1, yBase - 1) == Tiles.WALL_ID) {
            hitpoints[i++] = xSub1;
            hitpoints[i++] = yBase;
        }
        if(getParent().getTileID(xBase, (int) ySub2) == Tiles.WALL_ID ^ getParent().getTileID(xBase - 1, (int) ySub2) == Tiles.WALL_ID) {
            if(i==4) i = 2;
            hitpoints[i++] = xBase;
            hitpoints[i++] = ySub2;
        }
        if(getParent().getTileID((int) xSub2, yBase) == Tiles.WALL_ID ^ getParent().getTileID((int) xSub2, yBase - 1) == Tiles.WALL_ID) {
            if(i==4) i = 2;
            hitpoints[i++] = xSub2;
            hitpoints[i++] = yBase;
        }

        //move the robot back to a place without walls
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
            if(getParent().getLoseOnWallHit()) ((GameModel)getParent()).setLost(true);
        }
        //set the new values
        setX(xOut);
        setY(yOut);
        setChange(true);
    }
}
