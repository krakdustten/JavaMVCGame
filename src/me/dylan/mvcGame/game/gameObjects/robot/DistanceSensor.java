package me.dylan.mvcGame.game.gameObjects.robot;

import me.dylan.mvcGame.game.GameModel;
import me.dylan.mvcGame.game.gameObjects.Tiles;

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

        float sin = (float) Math.sin(rotation + model.getRotation());
        float cos = (float) Math.cos(rotation + model.getRotation());

        //calc distance to next wall
        int nextX = cos > 0 ? (int) Math.ceil(xSensor) : (int) xSensor;
        int nextY = sin > 0 ? (int) Math.ceil(ySensor) : (int) ySensor;
        int modX = cos > 0 ? 1 : -1;
        int modY = sin > 0 ? 1 : -1;
        float xLength = Math.abs((nextX - xSensor) / cos);
        float yLength = Math.abs((nextY - ySensor) / sin);
        boolean found = false;
        while(!found){
            if(xLength < yLength){
                if(gameModel.getTileID(nextX + (modX > 0 ? 0 : -1), nextY - (modY > 0 ? 1 : 0)) == Tiles.WALL_ID) return xLength;
                nextX += modX;
                xLength = Math.abs((nextX - xSensor) / cos);
            }else{
                if(gameModel.getTileID(nextX - (modX > 0 ? 1: 0), nextY + (modY > 0 ? 0 : -1)) == Tiles.WALL_ID) return yLength;
                nextY += modY;
                yLength = Math.abs((nextY - ySensor) / sin);
            }

            if(xLength > 10 && yLength > 10)found = true;
        }

        return 10.0f;
    }

    public float getRotation() { return rotation; }
}
