package me.dylan.mvcGame.game;

import me.dylan.mvcGame.game.gameObjects.RobotPlayer;
import me.dylan.mvcGame.game.gameObjects.specialTiles.SpecialTile;
import me.dylan.mvcGame.main.MainModel;

import java.util.HashMap;

public class GameModel {
    private MainModel mainModel;

    private int worldXSize;
    private int worldYSize;

    private int[] underGroundColor;
    private int[] tileID;
    private HashMap<Integer, SpecialTile> specialTiles;
    private boolean mapChanged = true;

    private RobotPlayer player;


    public GameModel(MainModel mainModel, int worldXSize, int worldYSize){
        this(mainModel,worldXSize, worldYSize, null, null, new HashMap<Integer, SpecialTile>());
    }

    public GameModel(MainModel mainModel, int worldXSize, int worldYSize, int[] underGroundColor, int[] tileID, HashMap<Integer, SpecialTile> specialTiles){
        this.mainModel = mainModel;

        this.worldXSize = worldXSize;
        this.worldYSize = worldYSize;

        int totalArraySize = worldXSize * worldYSize;
        if(underGroundColor == null) underGroundColor = new int[totalArraySize];
        if(tileID == null) tileID = new int[totalArraySize];
        if(underGroundColor.length != totalArraySize) return;
        if(tileID.length != totalArraySize) return;
        this.underGroundColor = underGroundColor;
        this.tileID = tileID;
        this.specialTiles = specialTiles;
    }

    public MainModel getMainModel() { return mainModel; }
    public int getWorldXSize() { return worldXSize; }
    public int getWorldYSize() { return worldYSize; }
    public int getUnderGroundColor(int x, int y) { return underGroundColor[x + y * worldXSize]; }
    public int getTileID(int x, int y) { return tileID[x + y * worldXSize]; }
    public HashMap<Integer, SpecialTile> getSpecialTiles() { return specialTiles; }
    public boolean isMapChanged(){if(mapChanged){mapChanged = false; return true;} return false;}

    public void setWorldXSize(int worldXSize) {
        this.worldXSize = worldXSize;
        //TODO change size of array
        mapChanged = true;
    }
    public void setWorldYSize(int worldYSize) {
        this.worldYSize = worldYSize;
        //TODO change size of array
        mapChanged = true;
    }
    public void setUnderGroundColor(int underGroundColor, int x, int y) { this.underGroundColor[x + y * worldXSize] = underGroundColor; mapChanged = true; }
    public void setTileID(int tileID, int x, int y) { this.tileID[x + y * worldYSize] = tileID; mapChanged = true;}



    //TODO make world drawer --> push data once or push in chunks or calc parts that are visible and push that


}
