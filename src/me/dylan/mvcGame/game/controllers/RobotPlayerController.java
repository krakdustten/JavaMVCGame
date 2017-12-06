package me.dylan.mvcGame.game.controllers;

import me.dylan.mvcGame.game.GameModel;
import me.dylan.mvcGame.game.gameObjects.robot.DistanceSensor;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;
import me.dylan.mvcGame.game.gameViewers.RobotPlayerView;
import me.dylan.mvcGame.game.gameViewers.RobotSensorViewer;

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
        model.setMoterLSpeed(0.1f);
        model.setMoterRSpeed(0.2f);

        model.addSensor(new DistanceSensor(this.gameModel, 1.0f * 6.0f/8, 1.0f * 6.0f/8, 0f));
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
}
