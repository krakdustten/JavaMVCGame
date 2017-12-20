package me.dylan.mvcGame.game.gameObjects;

import me.dylan.mvcGame.drawers.AdvancedTextureTileMap;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;

public class GameModel extends MapModel{
    //score and gametime stuff
    private float gameTime = 0;
    private boolean gameWon = false;
    private boolean gameLost = false;
    private boolean gameStarted = false;
    private boolean shouldGameReset = true;

    //menu
    private MenuController inGameMenu;
    private boolean isGameMenuShown = false;
    private MenuController gameOverlay;

    //extra
    private AdvancedTextureTileMap tileTextures;
    private String error;
    private boolean errorChanged;
    private boolean windowClosing = false;

    public GameModel(MainModel mainModel, int worldXSize, int worldYSize){
        this(mainModel,worldXSize, worldYSize, null, null);
    }

    public GameModel(MainModel mainModel, int worldXSize, int worldYSize, int[] underGroundColor, byte[] tileID){
        super(mainModel, worldXSize, worldYSize, underGroundColor, tileID);

        error = "";

        setViewZoom(0.5f);
        setViewX((getStartX() + 1.5f) * 64);
        setViewY((getStartY() + 1.5f) * 64);

        inGameMenu = new MenuController(mainModel, "img/menu.png");
        inGameMenu.addGuiElement(new MenuModel.GuiButton(0, 300, 350, 64, 1, "MAIN MENU", 1, 1, 1, 1, 0, 1, 0, 1));
        inGameMenu.addGuiElement(new MenuModel.GuiButton(0, 200, 350, 64, 2, "SAVE", 1, 1, 1, 1, 0, 1, 0, 1));
        inGameMenu.addGuiElement(new MenuModel.GuiButton(0, 100, 350, 64, 3, "BACK", 1, 1, 1, 1, 0, 1, 0, 1));
        inGameMenu.addGuiElement(new MenuModel.GuiButton(0, 0, 350, 64, 4, "QUIT", 1, 1, 1, 1, 0, 1, 0, 1));
        inGameMenu.setAlignMargin(0, 0, 0, 0);
        inGameMenu.setBackgroundColor(0.8f, 0.5f,0.4f, 0.3f);
    }

    public GameModel(MapModel mapModel) {
        this(mapModel.getMainModel(), mapModel.getWorldXSize(), mapModel.getWorldYSize(), null, null);

        for(int i = 0; i < getWorldXSize(); i++){
            for(int j = 0; j < getWorldYSize(); j++){
                setUnderGroundColor(mapModel.getUnderGroundColor(i, j), i, j);
                setTileID((byte) mapModel.getTileID(i, j), i, j);
            }
        }
        findStartAndFinish();
        setCode(mapModel.getCode());
        setRobot(mapModel.getRobot());
    }

    /*****GETTERS*****/


    public AdvancedTextureTileMap getTileTextures() { return tileTextures; }

    public float getViewX() { return -getMainModel().getCamera2D().getxPos(); }
    public float getViewY() { return -getMainModel().getCamera2D().getyPos(); }
    public float getViewZoom() { return getMainModel().getCamera2D().getZoom(); }

    public boolean getWon() { return gameWon; }
    public boolean getLost() { return gameLost; }
    public boolean getGameStarted() { return gameStarted; }
    public float getGameTime() { return gameTime;}
    public boolean getShouldGameReset() { return shouldGameReset; }

    public boolean getErrorChanged() { return errorChanged; }
    public String getError() { return error; }

    public MenuController getInGameMenu() { return inGameMenu; }
    public boolean getGameMenuShown() { return isGameMenuShown; }

    public boolean getWindowClosing() { return windowClosing; }

    public MenuController getGameOverlay() { return gameOverlay; }

    /****SETTERS*****/

    public void setViewX(float viewX) {
        if(viewX > (getWorldXSize() * 64)) viewX = getWorldXSize() * 64;
        if(viewX < 0) viewX = 0;
        getMainModel().getCamera2D().setxPos(-viewX);
    }
    public void setViewY(float viewY) {
        if(viewY > (getWorldYSize() * 64)) viewY = getWorldYSize() * 64;
        if(viewY < 0) viewY = 0;
        getMainModel().getCamera2D().setyPos(-viewY);
    }
    public void setViewZoom(float viewZoom) {
        if(viewZoom < 0.25f) viewZoom = 0.25f;
        if(viewZoom > 8) viewZoom = 8f;
        getMainModel().getCamera2D().setZoom(viewZoom);
    }

    public void setTileTextures(AdvancedTextureTileMap tileTextures) { this.tileTextures = tileTextures; }

    public void setGameStarted(boolean gameStarted){this.gameStarted = gameStarted;}
    public void setWon(boolean gameWon){this.gameWon = gameWon; if(!gameWon) gameOverlay = null;}
    public void setLost(boolean gameLost) { this.gameLost = gameLost; if(!gameLost) gameOverlay = null;}
    public void setShouldGameReset(boolean shouldGameReset) { this.shouldGameReset = shouldGameReset; }

    public void setError(String error){ this.error = error; errorChanged = true;}
    public void setErrorChanged(boolean errorChanged) { this.errorChanged = errorChanged; }

    public void setGameMenuShown(boolean gameMenuShown) {
        isGameMenuShown = gameMenuShown;
        inGameMenu.updateView();
    }

    public void setGameTime(float gameTime) { this.gameTime = gameTime; }

    public void setWindowClosing(boolean windowClosing) { this.windowClosing = windowClosing; }

    public void setGameOverlay(MenuController gameOverlay) { this.gameOverlay = gameOverlay; }

    /*****OTHER SMALL LOGIC*****/

    public void moveView(float dx, float dy){
        setViewX(getViewX() - dx);
        setViewY(getViewY() - dy);
    }

    public void updateGameTime() {
        gameTime += 1/50f;
    }
    public boolean isGameRunning(){
        return (gameStarted && !gameWon && !gameLost);
    }

    public void distroy() {
        inGameMenu.delete();
        tileTextures.distroy();
    }
}
