package me.dylan.mvcGame.game.gameObjects;

import me.dylan.mvcGame.game.gameObjects.specialTiles.SpecialTile;
import me.dylan.mvcGame.main.MainModel;

import java.util.HashMap;
import java.util.Map;

public class MapModel {
    private MainModel mainModel;

    //world stuff
    private int worldXSize;
    private int worldYSize;
    private int[] underGroundColor;
    private byte[] tileID;
    private HashMap<Integer, SpecialTile> specialTiles;
    private boolean mapChanged = true;
    private int startX, startY;
    private int finishX, finishY;

    private String code;
    private boolean codeChanged = true;


    public MapModel(MainModel mainModel, int worldXSize, int worldYSize, int[] underGroundColor, byte[] tileID, HashMap<Integer, SpecialTile> specialTiles){
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
        this.specialTiles = specialTiles;

        findStartAndFinish();

        code = "\n" +
                "def tick():\n" +
                "     global MotorL\n" +
                "     global MotorR\n";
    }

    /*****GETTERS*****/
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
    public HashMap<Integer, SpecialTile> getSpecialTiles() { return specialTiles; }
    public boolean isMapChanged(){if(mapChanged){mapChanged = false; return true;} return false;}

    public int getStartX() { return startX; }
    public int getStartY() { return startY; }
    public int getFinishX() { return finishX; }
    public int getFinishY() { return finishY; }

    public String getCode() { return code; }
    public boolean getCodeChanged() { return codeChanged; }

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
    public void setTileID(byte tileID, int x, int y) { this.tileID[x + y * worldXSize] = tileID; mapChanged = true;}

    public void setCode(String code) { this.code = code;codeChanged = true; }
    public void setCodeChanged(boolean codeChanged){ this.codeChanged = codeChanged; }

    /*****OTHER SMALL LOGIC*****/
    private void changeActualMapSize(int oldXSize, int oldYSize, int newXSize, int newYSize){
        int[] underGroundColor = new int[newXSize * newYSize];
        byte[] tileID = new byte[newXSize * newYSize];
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
