package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.GameModel;

public abstract class Sensor<T> {
    protected GameModel gameModel;
    protected RobotPlayerModel model;

    protected float x, y;
    protected String name;

    public Sensor(GameModel gameModel, float x, float y, String name){
        this.gameModel = gameModel;
        this.model = gameModel.getPlayer();

        this.x = x;
        this.y = y;
        this.name = name;
    }

    public abstract T calculateOutput();

    public float getX() { return x; }
    public float getY() { return y; }
    public String getName() { return name; }

    public float[] getRealPositionInWorld(){return getRealPositionInWorld(x , y);}
    protected float[] getRealPositionInWorld(float xRel, float yRel){
        float[] output = new float[2];
        float xRob = model.getX();
        float yRob = model.getY();
        float rotRob = model.getRotation();

        float sin = (float) Math.sin(rotRob);
        float cos = (float) Math.cos(rotRob);
        float xT = xRob + xRel - (xRob + 0.5f);
        float yT = yRob + yRel - (yRob + 0.5f);

        output[0] = xT * cos - yT * sin + (xRob + 0.5f);
        output[1] = xT * sin + yT * cos + (yRob + 0.5f);

        return output;
    }
}
