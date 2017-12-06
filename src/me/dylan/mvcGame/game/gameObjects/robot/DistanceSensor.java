package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.GameModel;

public class DistanceSensor extends Sensor<Float>{
    private float rotation;

    public DistanceSensor(GameModel gameModel, float x, float y, float rotation, String name) {
        super(gameModel, x, y, name);
        this.rotation = rotation;
    }

    @Override
    public Float calculateOutput() {
        float[] sensor = getRealPositionInWorld();
        float xSensor = sensor[0];
        float ySensor = sensor[1];

        float sin = (float) Math.sin(rotation);
        float cos = (float) Math.cos(rotation);

        //calc distance to next wall
        //TODO calc distance to next wall

        return 0.0f;
    }

    public float getRotation() { return rotation; }
}
