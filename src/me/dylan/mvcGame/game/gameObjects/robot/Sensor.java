package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.GameModel;

public abstract class Sensor<T> {
    protected GameModel gameModel;
    protected RobotPlayerModel model;

    protected float x, y;

    public Sensor(GameModel gameModel, float x, float y){
        this.gameModel = gameModel;
        this.model = gameModel.getPlayer();

        this.x = x;
        this.y = y;
    }

    public abstract T calculateOutput();

    public float getX() { return x; }
    public float getY() { return y; }
}
