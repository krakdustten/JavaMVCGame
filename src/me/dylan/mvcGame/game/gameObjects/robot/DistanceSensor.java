package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.GameModel;

public class DistanceSensor extends Sensor<Float>{
    private float rotation;

    public DistanceSensor(GameModel gameModel, float x, float y, float rotation) {
        super(gameModel, x, y);
        this.rotation = rotation;
    }

    @Override
    public Float calculateOutput() {
        return 0.0f;
    }

    public float getRotation() { return rotation; }
}
