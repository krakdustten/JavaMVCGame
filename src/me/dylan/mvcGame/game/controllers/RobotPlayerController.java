package me.dylan.mvcGame.game.controllers;

import me.dylan.mvcGame.game.GameModel;
import me.dylan.mvcGame.game.gameObjects.robot.DistanceSensor;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;
import me.dylan.mvcGame.game.gameViewers.RobotPlayerView;
import me.dylan.mvcGame.game.gameViewers.RobotSensorViewer;

import java.util.HashMap;

public class RobotPlayerController {
    private RobotPlayerModel model;
    private GameModel gameModel;

    private RobotPlayerView view;
    private RobotSensorViewer senserView;

    public RobotPlayerController(GameModel gameModel){
        this.gameModel = gameModel;

        gameModel.setPlayer(new RobotPlayerModel(gameModel));
        this.model = gameModel.getPlayer();
        view = new RobotPlayerView(gameModel);
        senserView = new RobotSensorViewer(this.gameModel);

        //TODO load sensors from world file or make full default robot
        model.addSensor(new DistanceSensor(this.gameModel, 1.0f * 26.0f/32, 1.0f * 25.0f/32, (float) (Math.PI * 3/2), "Left"));
        model.addSensor(new DistanceSensor(this.gameModel, 1.0f * 26.0f/32, 1.0f * 6.0f/32, (float) (Math.PI * 1/2), "Right"));
        model.addSensor(new DistanceSensor(this.gameModel, 1.0f * 29.0f/32, 1.0f * 16.0f/32, 0, "Front"));
        update();

        model.setMoterLSpeed(0.1f);
        model.setMoterRSpeed(0.2f);
    }

    public void update(){
        model.calculateMovement();
        view.update();
        senserView.update();
        model.changesDone();
    }

    public void render(){
        view.render();
        senserView.render();
    }

    //TODO add the hasmap as seperated values to the jython exendable class
    public HashMap<String, Object> calculateSensorData(){
        HashMap<String, Object> output = new HashMap<>();
        for(Sensor sensor : model.getAllSensors()){
            output.put(sensor.getName(), sensor.calculateOutput());
        }
        return output;
    }
}
