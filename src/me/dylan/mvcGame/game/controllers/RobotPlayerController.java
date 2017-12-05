package me.dylan.mvcGame.game.controllers;

import me.dylan.mvcGame.game.GameModel;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;
import me.dylan.mvcGame.game.gameViewers.RobotPlayerView;

public class RobotPlayerController {
    private RobotPlayerModel model;
    private GameModel gameModel;

    private RobotPlayerView view;

    public RobotPlayerController(GameModel gameModel){
        this.gameModel = gameModel;

        gameModel.setPlayer(new RobotPlayerModel(gameModel));
        this.model = gameModel.getPlayer();
        view = new RobotPlayerView(gameModel);
        model.setMoterLSpeed(1.0f);
        model.setMoterRSpeed(1.0f);
    }

    public void update(){
        model.calculateMovement();
        view.update();
    }

    public void render(){
        view.render();
    }
}
