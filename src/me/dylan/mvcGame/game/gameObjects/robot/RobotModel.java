package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.gameObjects.MapModel;

/**
 * The robot class.
 *
 * @author Dylan Gybels
 */
public class RobotModel {
    private MapModel parent;

    private float x, y, rotation;
    private float motorLPos, motorRPos;//1 rot = 1 tile
    private boolean change = true;

    private Sensor[] sensors = new Sensor[0];

    /**
     * Create a new robot model.
     *
     * @param parent The map model that the robot is in.
     */
    public RobotModel(MapModel parent){
        this.parent = parent;

        x = parent.getStartX() + 1;
        y = parent.getStartY() + 1;

        if(parent.getRobot() != null) {
            Sensor[] sensors = parent.getRobot().getSensors();
            this.sensors = new Sensor[sensors.length];
            System.arraycopy(sensors, 0, this.sensors, 0, sensors.length);
        }
    }

    /*GETTERS*/

    /**
     * Get the map the robot is in.
     * @return The map the robot is in.
     */
    public MapModel getParent() { return parent; }

    /**
     * Get the x position of the robot.
     * @return The x position of the robot.
     */
    public float getX() { return x; }

    /**
     * Get the y position of the robot.
     * @return The y position of the robot.
     */
    public float getY() { return y; }

    /**
     * Get the rotation of the robot.
     * @return The rotation of the robot.
     */
    public float getRotation() { return rotation; }

    /**
     * Get the left motor position.
     * @return The left motor position. (0.0 - 1.0)
     */
    public float getMoterLPos() { return motorLPos; }

    /**
     * Get the right motor position.
     * @return The right motor position. (0.0 - 1.0)
     */
    public float getMoterRPos() { return motorRPos; }

    /**
     * Get all the sensors on the robot.
     * @return All the sensors.
     */
    public Sensor[] getSensors() { return sensors; }

    /**
     * Get if the robot was changed.
     * @return If the robot was changed.
     */
    public boolean getChanged(){return change;}


    /*SETTERS*/

    /**
     * Set the x position of the robot.
     * @param x The new x position of the robot.
     */
    public void setX(float x) { this.x = x; change = true;}

    /**
     * Set the y position of the robot.
     * @param y The new y position of the robot.
     */
    public void setY(float y) { this.y = y; change = true;}

    /**
     * Set the rotation of the robot.
     * @param rotation The new rotation of the robot.
     */
    public void setRotation(float rotation) { this.rotation = rotation; change = true;}

    /**
     * Set the left motor position.
     * @param motorLPos The new left motor position.
     */
    public void setMotorLPos(float motorLPos) { this.motorLPos = motorLPos; change = true;}

    /**
     * Set the right motor position.
     * @param motorRPos The new right motor position.
     */
    public void setMotorRPos(float motorRPos) { this.motorRPos = motorRPos; change = true;}

    /*OTHER SMALL LOGIC*/

    /**
     * Add a sensor to the robot.
     * @param sensor The new sensor to add.
     */
    public void addSensor(Sensor sensor){
        //implementation of some sort of array list to make this part faster
        Sensor[] sensors = new Sensor[this.sensors.length + 1];
        System.arraycopy(this.sensors, 0, sensors, 0, this.sensors.length);
        sensors[sensors.length - 1] = sensor;
        this.sensors = sensors;
    }

    /**
     * Remove a sensor at position.
     * @param i The index of the sensor to remove.
     */
    public void removeSensor(int i){
        //implementation of some sort of array list to make this part faster
        Sensor[] sensors = new Sensor[this.sensors.length - 1];
        for(int j = 0, k = 0; k < this.sensors.length; j++, k++) {
            if(j == i) k++;
            if(k >= this.sensors.length || j >= sensors.length) break;
            sensors[j] = this.sensors[k];

        }
        this.sensors = sensors;
    }

    /**
     * Set if the robot was changed.
     * @param change If the robot was changed.
     */
    public void setChange(boolean change) {this.change = change; }
}
