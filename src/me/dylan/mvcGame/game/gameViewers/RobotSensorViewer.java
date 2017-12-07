package me.dylan.mvcGame.game.gameViewers;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.game.GameModel;
import me.dylan.mvcGame.game.gameObjects.robot.DistanceSensor;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;

import java.util.ArrayList;

public class RobotSensorViewer {
    private GameModel gameModel;
    private RobotPlayerModel model;

    private int vbo;
    private int texture;

    public RobotSensorViewer(GameModel gameModel){
        this.gameModel = gameModel;
        this.model = gameModel.getPlayer();

        vbo = VBODrawer2D.createBufferId();
        texture = Texture.createImageId("img/Sensors.png");
        update();
    }

    public void update(){
        //if(!model.isChanged())return;

        ArrayList<Sensor> sensors = model.getAllSensors();

        float[] vertexes = new float[VBODrawer2D.calcArraySizeForSquares(VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, sensors.size() + 1)];
        int offset = 0;

        float xStart = model.getX();
        float yStart = model.getY();
        for(Sensor sensor : sensors){
            int texbX;
            int texbY;
            int rot;
            if(sensor instanceof DistanceSensor){
                texbX = 0;
                texbY = 0;
                rot = (int)(((DistanceSensor) sensor).getRotation() * 8/Math.PI);

                //TEST
                float length = ((DistanceSensor) sensor).calculateOutput();
                //length = 0;
                float xTest = (float) (Math.cos(rot + model.getRotation()) * length);
                float yTest = (float) (Math.sin(rot + model.getRotation()) * length);

                System.out.println(length + " " + xTest + " " + yTest);


                float sin = (float) Math.sin(model.getRotation());
                float cos = (float) Math.cos(model.getRotation());
                float xT = sensor.getX() -0.5f;
                float yT = sensor.getY() -0.5f;
                float x0 =  xT       * cos -  yT       * sin + xStart + 0.5f;
                float y0 =  xT       * sin +  yT       * cos + yStart + 0.5f;

                offset = VBODrawer2D.draw2DSquare(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                        (x0 + xTest) * 64, (y0 + yTest) * 64, 2, 2,
                        1, 0, 1, 1,
                        0, 0, 1, 1);

                //--TEST
            }else{
                texbX = 1;
                texbY = 0;
                rot = 0;
            }

            offset = VBODrawer2D.draw2DSquareRotCenter(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                    (xStart + sensor.getX() - 0.0625f) * 64, (yStart + sensor.getY() - 0.0625f) * 64, 16, 16,
                    model.getRotation(), xStart * 64 + 32, yStart * 64 + 32,1, 1, 1, 1,
                    texbX * 0.125f + (rot % 4) * 0.03125f, texbY * 0.125f + (rot / 4) * 0.03125f, 0.03125f, 0.03125f);
        }

        VBODrawer2D.writeBufToMem(vbo, vertexes);
    }

    public void render(){
        VBODrawer2D.drawVBO(gameModel.getMainModel(), vbo, texture, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, VBODrawer2D.calcDrawAmountForSquares(model.getAllSensors().size()+1));
    }
}
