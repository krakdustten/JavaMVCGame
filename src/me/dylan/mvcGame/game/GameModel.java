package me.dylan.mvcGame.game;

import me.dylan.mvcGame.game.gameObjects.RobotPlayer;
import me.dylan.mvcGame.game.gameObjects.specialTiles.SpecialTile;
import me.dylan.mvcGame.main.MainModel;

import java.util.HashMap;
import java.util.Map;

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

    /*****GETTERS*****/

    public MainModel getMainModel() { return mainModel; }
    public int getWorldXSize() { return worldXSize; }
    public int getWorldYSize() { return worldYSize; }
    public int getUnderGroundColor(int x, int y) { return underGroundColor[x + y * worldXSize]; }
    public int getTileID(int x, int y) { return tileID[x + y * worldXSize]; }
    public HashMap<Integer, SpecialTile> getSpecialTiles() { return specialTiles; }
    public boolean isMapChanged(){if(mapChanged){mapChanged = false; return true;} return false;}

    public float getViewX() { return -mainModel.getCamera2D().getxPos(); }
    public float getViewY() { return -mainModel.getCamera2D().getyPos(); }
    public float getViewZoom() { return mainModel.getCamera2D().getZoom(); }

    /****SETTERS*****/

    public void setWorldXSize(int worldXSize) {
        changeActualMapSize(this.worldXSize, this.worldYSize, worldXSize, worldYSize);
        this.worldXSize = worldXSize;
        mapChanged = true;
    }
    public void setWorldYSize(int worldYSize) {
        changeActualMapSize(this.worldXSize, this.worldYSize, worldXSize, worldYSize);
        this.worldYSize = worldYSize;
        mapChanged = true;
    }
    public void setUnderGroundColor(int underGroundColor, int x, int y) { this.underGroundColor[x + y * worldXSize] = underGroundColor; mapChanged = true; }
    public void setTileID(int tileID, int x, int y) { this.tileID[x + y * worldYSize] = tileID; mapChanged = true;}

    public void setViewX(float viewX) {
        if(viewX > (worldXSize * 64)) viewX = worldXSize * 64;
        if(viewX < 0) viewX = 0;
        mainModel.getCamera2D().setxPos(-viewX);
    }
    public void setViewY(float viewY) {
        if(viewY > (worldYSize * 64)) viewY = worldYSize * 64;
        if(viewY < 0) viewY = 0;
        mainModel.getCamera2D().setyPos(-viewY);
    }
    public void setViewZoom(float viewZoom) {
        if(viewZoom < 1) viewZoom = 1;
        if(viewZoom > 16) viewZoom = 16;
        mainModel.getCamera2D().setZoom(viewZoom);
    }

    /*****OTHER SMALL LOGIC*****/

    public void moveView(float dx, float dy){
        setViewX(getViewX() - dx);
        setViewY(getViewY() - dy);
    }

    private void changeActualMapSize(int oldXSize, int oldYSize, int newXSize, int newYSize){
        int[] underGroundColor = new int[newXSize * newYSize];
        int[] tileID = new int[newXSize * newYSize];
        HashMap<Integer, SpecialTile> specialTiles = new HashMap<>();

        int xSize = oldXSize < newXSize ? oldXSize : newXSize;
        int ySize = oldYSize < newYSize ? oldYSize : newYSize;

        for(int i = 0; i < xSize; i++){
            for(int j = 0; j < ySize; j++){
                underGroundColor[i + j * xSize] = this.underGroundColor[i + j * xSize];
                tileID[i + j * xSize] = this.tileID[i + j * xSize];
            }
        }
        for(Map.Entry<Integer, SpecialTile> sTile : this.specialTiles.entrySet()){
            int loc = sTile.getKey();
            int x = loc % oldXSize;
            int y = loc / oldXSize;
            if(x < newXSize && y < newYSize)
                specialTiles.put(loc, sTile.getValue());
        }
        this.underGroundColor = underGroundColor;
        this.tileID = tileID;
        this.specialTiles = specialTiles;
        //TODO test this method
    }
}
