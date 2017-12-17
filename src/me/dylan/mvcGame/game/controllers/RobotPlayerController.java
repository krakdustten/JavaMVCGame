package me.dylan.mvcGame.game.controllers;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.robot.DistanceSensor;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;
import me.dylan.mvcGame.game.gameViewers.RobotPlayerView;
import me.dylan.mvcGame.game.gameViewers.RobotSensorViewer;
import me.dylan.mvcGame.jython.JythonRunner;
import org.python.core.PyObject;

import java.util.ArrayList;

public class RobotPlayerController {
    private RobotPlayerModel model;
    private GameModel gameModel;

    private RobotPlayerView view;
    private RobotSensorViewer senserView;

    private JythonRunner codeRunner;

    public RobotPlayerController(GameModel gameModel){
        this.gameModel = gameModel;

        gameModel.setPlayer(new RobotPlayerModel(gameModel));
        this.model = gameModel.getPlayer();
        view = new RobotPlayerView(gameModel);
        senserView = new RobotSensorViewer(this.gameModel);

        //TODO load sensors from world file or make full default robot
        model.addSensor(new DistanceSensor(this.gameModel, 1.0f * 26.0f/32, 1.0f * 25.0f/32, (float) (Math.PI * 1/2), "Distance_Left"));
        model.addSensor(new DistanceSensor(this.gameModel, 1.0f * 26.0f/32, 1.0f * 6.0f/32, (float) (Math.PI * 3/2), "Distance_Right"));
        model.addSensor(new DistanceSensor(this.gameModel, 1.0f * 29.0f/32, 1.0f * 16.0f/32, 0, "Distance_Front"));
        addIDsToSensors();

        update();

        codeRunner = new JythonRunner(gameModel);
    }

    public void update(){
        view.update();
        senserView.update();
        model.changesDone();
    }

    public void updateGame() {
        model.calculateMovement();
        calculateSensorData();
        runCode();
    }

    public void render(){
        view.render();
        senserView.render();
    }

    private void addIDsToSensors() {
        ArrayList<String> sensorNames = new ArrayList<>();
        int totalFloatAmount = 0;
        for (Sensor sensor : model.getAllSensors()) {
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

    public void calculateSensorData(){
        float[] data = model.getSensorValues();
        for (Sensor sensor : model.getAllSensors()) {
            sensor.calculateOutput(data);
        }
    }

    public void distroy() {
        view.distroy();
        view = null;
        senserView.distroy();
        senserView = null;
    }
}
