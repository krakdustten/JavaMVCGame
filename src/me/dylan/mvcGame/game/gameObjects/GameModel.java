package me.dylan.mvcGame.game.gameObjects;

import me.dylan.mvcGame.drawers.AdvancedTextureTileMap;
import me.dylan.mvcGame.game.gameObjects.Tiles;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;
import me.dylan.mvcGame.game.gameObjects.specialTiles.SpecialTile;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;

import java.util.HashMap;
import java.util.Map;

public class GameModel {
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

    //player stuff
    private RobotPlayerModel player;
    private String code;
    private boolean codeChanged = true;

    //score and gametime stuff
    private float gameTime = 0;
    private boolean gameWon = false;
    private boolean gameStarted = false;
    private boolean shouldGameReset = true;

    //menu
    private MenuController inGameMenu;
    private boolean isGameMenuShown = false;

    //extra
    private AdvancedTextureTileMap tileTextures;
    private String error;
    private boolean errorChanged;

    public GameModel(MainModel mainModel, int worldXSize, int worldYSize){
        this(mainModel,worldXSize, worldYSize, null, null, new HashMap<>());
    }

    public GameModel(MainModel mainModel, int worldXSize, int worldYSize, int[] underGroundColor, byte[] tileID, HashMap<Integer, SpecialTile> specialTiles){
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

        code = "\n" +
                "def tick():\n" +
                "     global MotorL\n" +
                "     global MotorR\n";
        error = "";

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

        setViewX(getWorldXSize() * 64 / 2);
        setViewY(getWorldYSize() * 64 / 2);

        inGameMenu = new MenuController(mainModel, "img/menu.png");
        inGameMenu.addGuiElement(new MenuModel.GuiButton(0, 300, 350, 64, 1, "MAIN MENU", 1, 1, 1, 1, 0, 1, 0, 1));
        inGameMenu.addGuiElement(new MenuModel.GuiButton(0, 200, 350, 64, 2, "SAVE", 1, 1, 1, 1, 0, 1, 0, 1));
        inGameMenu.addGuiElement(new MenuModel.GuiButton(0, 100, 350, 64, 3, "BACK", 1, 1, 1, 1, 0, 1, 0, 1));
        inGameMenu.addGuiElement(new MenuModel.GuiButton(0, 0, 350, 64, 4, "QUIT", 1, 1, 1, 1, 0, 1, 0, 1));
        inGameMenu.setAlignMargin(0, 0, 0, 0);
        inGameMenu.setBackgroundColor(0.8f, 0.5f,0.4f, 0.3f);
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

    public float getViewX() { return -mainModel.getCamera2D().getxPos(); }
    public float getViewY() { return -mainModel.getCamera2D().getyPos(); }
    public float getViewZoom() { return mainModel.getCamera2D().getZoom(); }

    public String getCode() { return code; }
    public boolean getCodeChanged() { return codeChanged; }
    public RobotPlayerModel getPlayer() { return player; }

    public AdvancedTextureTileMap getTileTextures() { return tileTextures; }

    public boolean getWon() { return gameWon; }
    public boolean getGameStarted() { return gameStarted; }
    public float getGameTime() { return gameTime;}
    public boolean getShouldGameReset() { return shouldGameReset; }

    public boolean getErrorChanged() { return errorChanged; }
    public String getError() { return error; }

    public MenuController getInGameMenu() { return inGameMenu; }
    public boolean getGameMenuShown() { return isGameMenuShown; }

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
        if(viewZoom < 0.25f) viewZoom = 0.25f;
        if(viewZoom > 8) viewZoom = 8f;
        mainModel.getCamera2D().setZoom(viewZoom);
    }

    public void setCode(String code){ this.code = code; codeChanged = true; }
    public void setCodeChanged(boolean codeChanged){ this.codeChanged = codeChanged; }

    public void setTileTextures(AdvancedTextureTileMap tileTextures) { this.tileTextures = tileTextures; }


    public void setPlayer(RobotPlayerModel player) {
        this.player = player;
    }

    public void setGameStarted(boolean gameStarted){this.gameStarted = gameStarted;}
    public void setWon(boolean gameWon){this.gameWon = gameWon;}
    public void setShouldGameReset(boolean shouldGameReset) { this.shouldGameReset = shouldGameReset; }

    public void setError(String error){ this.error = error; errorChanged = true;}
    public void setErrorChanged(boolean errorChanged) { this.errorChanged = errorChanged; }

    public void setGameMenuShown(boolean gameMenuShown) {
        isGameMenuShown = gameMenuShown;
        inGameMenu.updateView();
    }

    /*****OTHER SMALL LOGIC*****/

    public void moveView(float dx, float dy){
        setViewX(getViewX() - dx);
        setViewY(getViewY() - dy);
    }

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

    public void updateGameTime() {
        gameTime += 1/50f;
    }
    public boolean isGameRunning(){
        return (gameStarted && !gameWon);
    }

    public void distroy() {
        inGameMenu.delete();
        tileTextures.distroy();
    }
}