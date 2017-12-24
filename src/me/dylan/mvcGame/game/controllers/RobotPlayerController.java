package me.dylan.mvcGame.game.controllers;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;
import me.dylan.mvcGame.game.gameViewers.RobotPlayerView;
import me.dylan.mvcGame.game.gameViewers.RobotSensorViewer;
import me.dylan.mvcGame.jython.JythonRunner;
import org.python.core.PyObject;

import java.util.ArrayList;

/**
 * The player controller for the robot.
 *
 * @author Dylan Gybels
 */
public class RobotPlayerController {
    private RobotPlayerModel model;
    private GameModel gameModel;
    private RobotPlayerView view;
    private RobotSensorViewer senserView;

    private JythonRunner codeRunner;

    /**
     * Create a new robot player controller.
     *
     * @param gameModel The game model.
     */
    public RobotPlayerController(GameModel gameModel){
        this.gameModel = gameModel;

        gameModel.setRobot(new RobotPlayerModel(gameModel.getRobot(), gameModel));
        this.model = (RobotPlayerModel) gameModel.getRobot();
        view = new RobotPlayerView(gameModel);
        senserView = new RobotSensorViewer(this.gameModel);

        addIDsToSensors();
        update();
    }

    /**
     * Update the robot player controller.
     */
    public void update(){
        if(codeRunner == null) codeRunner = new JythonRunner(gameModel);
        view.update();
        senserView.update();
        model.setChange(false);
    }

    /**
     * Update the time controlled parts of the robot player controller.
     */
    public void updateGame() {
        model.calculateMovement();
        calculateSensorData();
        runCode();
    }

    /**
     * Render the robot player controller.
     */
    public void render(){
        view.render();
        senserView.render();
    }

    private void addIDsToSensors() {
        ArrayList<String> sensorNames = new ArrayList<>();
        int totalFloatAmount = 0;
        for (Sensor sensor : model.getSensors()) {
            String[] sNames = sensor.getNames();
            int[] ids = new int[sNames.length];
            for(int i = 0; i < sNames.length; i++){
                sensorNames.add(sNames[i]);
                ids[i] = totalFloatAmount++;
            }
            sensor.setID(ids);
        }

        model.setSensorNames(sensorNames.toArray(new String[0]));
    }

    private void runCode() {
        if(gameModel.getCodeChanged()){
            if(!codeRunner.compileCode(gameModel.getCode()))
                gameModel.setShouldGameReset(true);
            gameModel.setCodeChanged(false);
        }
        codeRunner.setVarArray(model.getSensorNames(), model.getSensorValues());
        codeRunner.setVar("MotorL", model.getMoterLSpeed());
        codeRunner.setVar("MotorR", model.getMoterRSpeed());
        PyObject ret = codeRunner.runMethod("tick");
        if(ret == null){
            gameModel.setShouldGameReset(true);
        }
        model.setMoterLSpeedTop(codeRunner.getVar("MotorL", float.class));
        model.setMoterRSpeedTop(codeRunner.getVar("MotorR", float.class));
    }

    /**
     * Calculate the sensor data.
     */
    public void calculateSensorData(){
        float[] data = model.getSensorValues();
        for (Sensor sensor : model.getSensors()) {
            sensor.calculateOutput(data);
        }
    }

    /**
     * Distroy the object and clear all variables.
     */
    public void distroy() {
        view.distroy();
        view = null;
        senserView.distroy();
        senserView = null;
        codeRunner.distroy();
    }
}
