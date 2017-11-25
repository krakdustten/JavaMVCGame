package me.dylan.mvcGame.game;

import me.dylan.mvcGame.main.MainModel;

import java.util.HashMap;

public class GameModel {
    private MainModel mainModel;

    private int worldXSize;
    private int worldYSize;

    private int[] underGroundColor;
    private int[] tileID;
    private HashMap<Integer, SpecialTile> specialTiles;

    private RobotPlayer player;


    public int getWorldXSize() { return worldXSize; }
    public int getWorldYSize() { return worldYSize; }
    public int[] getUnderGroundColor() { return underGroundColor; }
    public int[] getTileID() { return tileID; }
    public HashMap<Integer, SpecialTile> getSpecialTiles() { return specialTiles; }

    public void setWorldXSize(int worldXSize) { this.worldXSize = worldXSize; }
    public void setWorldYSize(int worldYSize) { this.worldYSize = worldYSize; }
    public void setUnderGroundColor(int[] underGroundColor) { this.underGroundColor = underGroundColor; }
    public void setTileID(int[] tileID) { this.tileID = tileID; }

    public GameModel(){
        specialTiles = new HashMap<>();
    }

    //TODO make world drawer --> push data once or push in chunks or calc parts that are visible and push that


}
