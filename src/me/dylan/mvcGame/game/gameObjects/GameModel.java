package me.dylan.mvcGame.game.gameObjects;

import me.dylan.mvcGame.drawers.AdvancedTextureTileMap;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;

/**
 * The main game model.
 *
 * @author Dylan Gybels
 */
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

    /**
     * Create a new game model.
     *
     * @param mainModel The main model.
     * @param worldXSize The world x size.
     * @param worldYSize The world y size.
     */
    public GameModel(MainModel mainModel, int worldXSize, int worldYSize){
        this(mainModel,worldXSize, worldYSize, null, null);
    }

    /**
     * Create a new game model.
     *
     * @param mainModel The main model.
     * @param worldXSize The world x size.
     * @param worldYSize The world y size.
     * @param underGroundColor The array that holds all the underground colors.
     * @param tileID The array that holds the tile ID's.
     */
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

    /**
     * Create a new game model.
     *
     * @param mapModel The map to use.
     */
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

        for(Sensor s : getRobot().getSensors()) s.setMapModel(this);
    }

    /*GETTERS*/

    /**
     * Get the tile texture for the tiles.
     * @return The tile texture.
     */
    public AdvancedTextureTileMap getTileTextures() { return tileTextures; }

    /**
     * Get the x position of the camera.
     * @return The x position of the camera.
     */
    public float getViewX() { return -getMainModel().getCamera2D().getxPos(); }

    /**
     * Get the y position of the camera.
     * @return The y position of the view.
     */
    public float getViewY() { return -getMainModel().getCamera2D().getyPos(); }

    /**
     * Get the zoom of the camera.
     * @return The zoom of the camera.
     */
    public float getViewZoom() { return getMainModel().getCamera2D().getZoom(); }

    /**
     * Get if the game was won.
     * @return True, if the game was won.
     */
    public boolean getWon() { return gameWon; }

    /**
     * Get if the game was lost.
     * @return If the game was lost.
     */
    public boolean getLost() { return gameLost; }

    /**
     * Get if the game was started.
     * @return If the game was started.
     */
    public boolean getGameStarted() { return gameStarted; }

    /**
     * Get the game time.
     * @return The game time.
     */
    public float getGameTime() { return gameTime;}

    /**
     * Get if the game should reset.
     * @return True, if the game should reset.
     */
    public boolean getShouldGameReset() { return shouldGameReset; }

    /**
     * Get if the error string changed.
     * @return True, if the error string changed.
     */
    public boolean getErrorChanged() { return errorChanged; }

    /**
     * Get the error string.
     * @return The error string.
     */
    public String getError() { return error; }

    /**
     * Get the in-game menu.
     * @return The in-game menu.
     */
    public MenuController getInGameMenu() { return inGameMenu; }

    /**
     * Get if the in-game menu is visible.
     * @return True, if the in-game menu is visible.
     */
    public boolean getGameMenuShown() { return isGameMenuShown; }

    /**
     * Get if the window is closing.
     * @return True, if the window is closing.
     */
    public boolean getWindowClosing() { return windowClosing; }

    /**
     * Get the game overlay.
     * @return The game overlay.
     */
    public MenuController getGameOverlay() { return gameOverlay; }

    /*SETTERS*/

    /**
     * Set the x position of the camera.
     * @param viewX The new x position of the camera.
     */
    public void setViewX(float viewX) {
        if(viewX > (getWorldXSize() * 64)) viewX = getWorldXSize() * 64;
        if(viewX < 0) viewX = 0;
        getMainModel().getCamera2D().setxPos(-viewX);
    }

    /**
     * Set the y position of the camera.
     * @param viewY The new y position of the camera.
     */
    public void setViewY(float viewY) {
        if(viewY > (getWorldYSize() * 64)) viewY = getWorldYSize() * 64;
        if(viewY < 0) viewY = 0;
        getMainModel().getCamera2D().setyPos(-viewY);
    }

    /**
     * Set the zoom of the camera.
     * @param viewZoom The new zoom of the camera.
     */
    public void setViewZoom(float viewZoom) {
        if(viewZoom < 0.25f) viewZoom = 0.25f;
        if(viewZoom > 8) viewZoom = 8f;
        getMainModel().getCamera2D().setZoom(viewZoom);
    }

    /**
     * Set the tile texture. This should only be set by the viewer of the tiles.
     * @param tileTextures The new tile texture.
     */
    public void setTileTextures(AdvancedTextureTileMap tileTextures) { this.tileTextures = tileTextures; }

    /**
     * Set that the game is started or not.
     * @param gameStarted If the game is started or not.
     */
    public void setGameStarted(boolean gameStarted){this.gameStarted = gameStarted;}

    /**
     * Set that the game is won or not.
     * @param gameWon If the game is won or not.
     */
    public void setWon(boolean gameWon){this.gameWon = gameWon; if(!gameWon) gameOverlay = null;}

    /**
     * Set that the game is lost or not.
     * @param gameLost If the game is lost or not.
     */
    public void setLost(boolean gameLost) { this.gameLost = gameLost; if(!gameLost) gameOverlay = null;}

    /**
     * Set that the game should reset.
     * @param shouldGameReset If the game should reset.
     */
    public void setShouldGameReset(boolean shouldGameReset) { this.shouldGameReset = shouldGameReset; }

    /**
     * Set the error string.
     * @param error The new error string.
     */
    public void setError(String error){ this.error = error; errorChanged = true;}

    /**
     * Set if the error was changed.
     * @param errorChanged If the error was changed or not.
     */
    public void setErrorChanged(boolean errorChanged) { this.errorChanged = errorChanged; }

    /**
     * Set if we should show the in-game menu.
     * @param gameMenuShown Should show the in-game menu.
     */
    public void setGameMenuShown(boolean gameMenuShown) {
        isGameMenuShown = gameMenuShown;
        inGameMenu.updateView();
        if(gameOverlay != null)gameOverlay.delete();
        gameOverlay = null;
    }

    /**
     * Set the game time.
     * @param gameTime The new game time.
     */
    public void setGameTime(float gameTime) { this.gameTime = gameTime; }

    /**
     * Set if the second window is closing.
     * @param windowClosing Is the second window closing.
     */
    public void setWindowClosing(boolean windowClosing) { this.windowClosing = windowClosing; }

    /**
     * Set the game overlay.
     * @param gameOverlay The new game overlay.
     */
    public void setGameOverlay(MenuController gameOverlay) { this.gameOverlay = gameOverlay; }

    /*OTHER SMALL LOGIC*/

    /**
     * Move the camera.
     * @param dx The x way to move it.
     * @param dy The y way to move it.
     */
    public void moveView(float dx, float dy){
        setViewX(getViewX() - dx);
        setViewY(getViewY() - dy);
    }

    /**
     * Update the game time.
     */
    public void updateGameTime() {
        gameTime += 1/50f;
    }

    /**
     * Is the game running.
     * @return If the game is running.
     */
    public boolean isGameRunning(){
        return (gameStarted && !gameWon && !gameLost);
    }

    /**
     * Distroy the model.
     */
    public void distroy() {
        inGameMenu.delete();
        tileTextures.distroy();
    }
}
