package me.dylan.mvcGame.game.gameObjects;

import me.dylan.mvcGame.game.gameObjects.robot.RobotModel;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.worldEditor.WorldEditorModel;

import java.util.ArrayList;

//TODO javadoc
public class MapModel {
    private MainModel mainModel;
    //player stuff
    private RobotModel robot;

    //world stuff
    private int worldXSize;
    private int worldYSize;
    private int[] underGroundColor;
    private byte[] tileID;
    private boolean mapChanged = true;
    private int startX, startY;
    private int finishX, finishY;

    //code
    private String code;
    private boolean codeChanged = true;
    private boolean loseOnWallHit = true;

    public MapModel(MainModel mainModel, int worldXSize, int worldYSize, int[] underGroundColor, byte[] tileID){
        this.mainModel = mainModel;

        this.worldXSize = worldXSize;
        this.worldYSize = worldYSize;

        int totalArraySize = worldXSize * worldYSize;
        if(underGroundColor == null) underGroundColor = new int[totalArraySize];
        if(tileID == null) tileID = new byte[totalArraySize];
        if(underGroundColor.length != totalArraySize) return;
        if(tileID.length != totalArraySize) return;
        this.underGroundColor = underGroundColor;
        this.tileID = tileID;

        findStartAndFinish();

        code = "\n" +
                "def tick():\n" +
                "     global MotorL\n" +
                "     global MotorR\n";
    }

    /*GETTERS*/
    public MainModel getMainModel() { return mainModel; }
    public int getWorldXSize() { return worldXSize; }
    public int getWorldYSize() { return worldYSize; }
    public int getUnderGroundColor(int x, int y) {
        if(x < 0 || x >= worldXSize || y < 0 || y >= worldYSize) return -1;
        return underGroundColor[x + y * worldXSize];
    }
    public int getTileID(int x, int y) {
        if(x < 0 || x >= worldXSize || y < 0 || y >= worldYSize) return -1;
        return tileID[x + y * worldXSize];
    }
    public boolean getMapChanged(){if(mapChanged){mapChanged = false; return true;} return false;}

    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getFinishX() { return finishX; }
    public int getFinishY() { return finishY; }

    public String getCode() { return code; }
    public boolean getCodeChanged() { return codeChanged; }

    public boolean getLoseOnWallHit() { return loseOnWallHit; }

    public RobotModel getRobot() { return robot; }

    /*SETTERS*/
    public void setWorldXSize(int worldXSize) {
        changeActualMapSize(worldXSize, worldYSize, 0, 0);
        this.worldXSize = worldXSize;
        mapChanged = true;
    }
    public void setWorldYSize(int worldYSize) {
        changeActualMapSize(worldXSize, worldYSize, 0, 0);
        this.worldYSize = worldYSize;
        mapChanged = true;
    }
    public void setUnderGroundColor(int underGroundColor, int x, int y) { this.underGroundColor[x + y * worldXSize] = underGroundColor; mapChanged = true; }
    public void setTileID(byte tileID, int x, int y) { this.tileID[x + y * worldXSize] = tileID; mapChanged = true;}

    public void setCode(String code) { this.code = code;codeChanged = true; }
    public void setCodeChanged(boolean codeChanged){ this.codeChanged = codeChanged; }

    public void setLoseOnWallHit(boolean loseOnWallHit) { this.loseOnWallHit = loseOnWallHit; }

    public void setRobot(RobotModel robot) { this.robot = robot; }

    /*OTHER SMALL LOGIC*/
    public void changeActualMapSize(int newXSize, int newYSize, int xOffset, int yOffset){
        int[] underGroundColor = new int[newXSize * newYSize];
        byte[] tileID = new byte[newXSize * newYSize];

        int xSize;
        int ySize;
        int negXOffset = 0;
        int negYOffset = 0;

        if(xOffset < 0){
            xSize = worldXSize < newXSize ? worldXSize : newXSize;
            negXOffset = -xOffset;
            xOffset = 0;
        }else xSize = worldXSize < (newXSize + xOffset) ? worldXSize : newXSize + xOffset;

        if(yOffset < 0){
            ySize = worldYSize < newYSize ? worldYSize : newXSize;
            negYOffset = -yOffset;
            yOffset = 0;
        }else ySize = worldYSize < (newYSize + yOffset) ? worldYSize : newYSize + yOffset;


        for(int i = 0; i < xSize; i++){
            for(int j = 0; j < ySize; j++){
                underGroundColor[i + (j + yOffset) * newXSize + xOffset] = this.underGroundColor[i + (j + negYOffset) * worldXSize + negXOffset];
                tileID[i + (j + yOffset) * newXSize + xOffset] = this.tileID[i + (j + negYOffset) * worldXSize + negXOffset];
            }
        }
        this.underGroundColor = underGroundColor;
        this.tileID = tileID;
        this.worldXSize = newXSize;
        this.worldYSize = newYSize;
    }

    public void checkIfMapCanBeSmaller(WorldEditorModel model) {
        if(worldXSize <= 0 || worldYSize <= 0)return;

        boolean tileHereTop = false;
        boolean tileHereBottom = false;
        boolean tileHereLeft = false;
        boolean tileHereRight = false;

        for(int i = 0; i < worldXSize; i++){
            if(!tileHereTop){
                if(getTileID(i, worldYSize - 1) != Tiles.NO_TILE) tileHereTop = true;
            }
            if(!tileHereBottom){
                if(getTileID(i, 0) != Tiles.NO_TILE) tileHereBottom = true;
            }
            if(tileHereTop && tileHereBottom) break;
        }
        for(int i = 0; i < worldYSize; i++){
            if(!tileHereLeft){
                if(getTileID(0, i) != Tiles.NO_TILE) tileHereLeft = true;
            }
            if(!tileHereRight){
                if(getTileID(worldXSize - 1, i) != Tiles.NO_TILE) tileHereRight = true;
            }
            if(tileHereLeft && tileHereRight) break;
        }

        if(!tileHereBottom || !tileHereTop || !tileHereLeft || !tileHereRight){
            changeActualMapSize(
                    worldXSize - (!tileHereLeft ? 1 : 0) - (!tileHereRight ? 1 : 0),
                    worldYSize - (!tileHereTop ? 1 : 0) - (!tileHereBottom ? 1 : 0),
                    !tileHereLeft ? -1 : 0,
                    !tileHereBottom ? -1 : 0);

            model.moveView(!tileHereLeft ? 64f : 0f, !tileHereBottom ? 64f : 0f);

            checkIfMapCanBeSmaller(model);
        }
    }

    public void findStartAndFinish(){
        //find start and finish
        boolean startFound = false;
        boolean finishFound = false;
        for(int i = 0; i < worldXSize - 3; i++) {
            for (int j = 0; j < worldYSize - 3; j++) {
                if(!startFound){
                    if(getTileID(i, j) == Tiles.START_ID){
                        startX = i;
                        startY = j;
                        startFound = true;
                    }
                }else if(!finishFound){
                    if(getTileID(i, j) == Tiles.END_ID){
                        finishX = i;
                        finishY = j;
                        finishFound = true;
                    }
                }else{
                    break;
                }
            }
        }
    }
}
