package me.dylan.mvcGame.worldEditor;

import me.dylan.mvcGame.game.gameObjects.MapModel;
import me.dylan.mvcGame.game.gameObjects.Tiles;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;

/**
 * The model of the world editor.
 *
 * @author Dylan Gybels
 */
public class WorldEditorModel extends MapModel{
    private boolean showInEditorMenu;
    private MenuController inEditorMenu;
    private boolean windowClosing;

    private boolean editingSensor = false;
    private int editingSensorIndex = -1;
    private boolean editingRobot = false;

    private byte selectedTile;
    private int selectedColor = 256 * 256 * 256 - 1;

    /**
     * Create a new world editor model.
     *
     * @param mainModel The main model.
     * @param worldXSize The world x size.
     * @param worldYSize The world y size.
     */
    public WorldEditorModel(MainModel mainModel, int worldXSize, int worldYSize){
        super(mainModel,worldXSize, worldYSize, null, null);
    }

    /**
     * Create a new world editor model.
     *
     * @param mapModel The map used.
     */
    public WorldEditorModel(MapModel mapModel) {
        super(mapModel.getMainModel(), mapModel.getWorldXSize(), mapModel.getWorldYSize(), null, null);

        for(int i = 0; i < getWorldXSize(); i++){
            for(int j = 0; j < getWorldYSize(); j++){
                setUnderGroundColor(mapModel.getUnderGroundColor(i, j), i, j);
                setTileID((byte) mapModel.getTileID(i, j), i, j);
            }
        }
        findStartAndFinish();
        setCode(mapModel.getCode());
        setRobot(mapModel.getRobot());

        inEditorMenu = new MenuController(getMainModel(), "img/menu.png");
        inEditorMenu.addGuiElement(new MenuModel.GuiButton(0, 300, 350, 64, 1, "MAIN MENU", 1, 1, 1, 1, 0, 1, 0, 1));
        inEditorMenu.addGuiElement(new MenuModel.GuiButton(0, 200, 350, 64, 2, "SAVE", 1, 1, 1, 1, 0, 1, 0, 1));
        inEditorMenu.addGuiElement(new MenuModel.GuiButton(0, 100, 350, 64, 3, "BACK", 1, 1, 1, 1, 0, 1, 0, 1));
        inEditorMenu.addGuiElement(new MenuModel.GuiButton(0, 0, 350, 64, 4, "QUIT", 1, 1, 1, 1, 0, 1, 0, 1));
        inEditorMenu.setAlignMargin(0, 0, 0, 0);
        inEditorMenu.setBackgroundColor(0.8f, 0.5f,0.4f, 0.3f);
    }

    /*GETTERS*/

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
     * Get if the in editor menu is shown.
     * @return If the in editor menu is shown.
     */
    public boolean getShowInEditorMenu() { return showInEditorMenu; }

    /**
     * Get the in editor menu.
     * @return The in editor menu.
     */
    public MenuController getInEditorMenu() { return inEditorMenu; }

    /**
     * Get if the extra window is closing.
     * @return If the extra window is closing.
     */
    public boolean getWindowClosing() { return windowClosing; }

    /**
     * Get the selected sort of tile.
     * @return The selected sort of tile.
     */
    public byte getSelectedTile() { return selectedTile; }

    /**
     * Get the selected color.
     * @return The selected color.
     */
    public int getSelectedColor() { return selectedColor; }

    /**
     * Get if we are editing a sensor.
     * @return If we are editing a sensor.
     */
    public boolean getEditingSensor() { return editingSensor; }

    /**
     * Get the index of the sensor we editing.
     * @return The index of the sensor we are editing.
     */
    public int getEditingSensorIndex() { return editingSensorIndex; }

    /**
     * Get if we are editing the robot.
     * @return If we are editing the robot.
     */
    public boolean getEditingRobot() { return editingRobot; }

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
     * Set if the in game menu should be shown.
     * @param showInEditorMenu If the in game menu should be shown.
     */
    public void setShowInEditorMenu(boolean showInEditorMenu) { this.showInEditorMenu = showInEditorMenu;inEditorMenu.updateView(); }

    /**
     * Set if the extra window is closing.
     * @param windowClosing If the extra window is closing.
     */
    public void setWindowClosing(boolean windowClosing) { this.windowClosing = windowClosing; }

    /**
     * Set the selected sort of tile.
     * @param selectedTile The new selected sort of tile.
     */
    public void setSelectedTile(byte selectedTile) { this.selectedTile = selectedTile; }

    /**
     * Set the selected color.
     * @param selectedColor The new selected color.
     */
    public void setSelectedColor(int selectedColor) { this.selectedColor = selectedColor; }

    /**
     * Set that we are editing a sensor or not.
     * @param editingSensor Are we editing the sensor or not.
     */
    public void setEditingSensor(boolean editingSensor) { this.editingSensor = editingSensor; }

    /**
     * Set the index of the sensor we are editing.
     * @param editingSensorIndex The new index of the sensor we are editing.
     */
    public void setEditingSensorIndex(int editingSensorIndex) { this.editingSensorIndex = editingSensorIndex; }

    /**
     * Set that we are editing the robot.
     * @param editingRobot Are we editing the robot or not.
     */
    public void setEditingRobot(boolean editingRobot) {
        this.editingRobot = editingRobot;
        getRobot().setX(getWorldXSize() / 2.0f);
        getRobot().setY(getWorldYSize() / 2.0f);
    }

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
     * Change a tile to a specified tile id and color.
     * @param toTile The tile id to change it to.
     * @param toColor The color to change it to.
     * @param tileX The x coordinate of the tile to change.
     * @param tileY The y coordinate of the tile to change.
     */
    public void changeTileTo(byte toTile, int toColor, int tileX, int tileY) {
        byte oldTile = (byte) getTileID(tileX, tileY);

        switch (oldTile){
            case Tiles.START_ID:
                removeAllTilesOf(Tiles.START_ID, Tiles.FLOOR_ID);
                break;
            case Tiles.FINISH_ID:
                removeAllTilesOf(Tiles.FINISH_ID, Tiles.FLOOR_ID);
                break;
        }

        if(oldTile == toTile) {
            setTileID(Tiles.NO_TILE, tileX, tileY);
            return;
        }

        switch (toTile){
            case Tiles.START_ID:
            case Tiles.FINISH_ID:
                if(getWorldXSize() < 3 ||getWorldYSize() < 3) return;
                if(tileX < 1) tileX = 1;
                if(tileY < 1) tileY = 1;
                if(tileX >= getWorldXSize() - 1) tileX = getWorldXSize() - 2;
                if(tileY >= getWorldYSize() - 1) tileY = getWorldYSize() - 2;
                removeAllTilesOf(toTile, Tiles.FLOOR_ID);
                for(int i = -1; i < 2; i++){
                    for(int j = -1; j < 2; j++){
                        setTileID(toTile, tileX + i, tileY + j);
                        setUnderGroundColor(toColor, tileX + i, tileY + j);
                    }
                }
                break;
            default:
                setTileID(toTile, tileX, tileY);
                setUnderGroundColor(toColor, tileX, tileY);
                break;
        }

    }

    private void removeAllTilesOf(byte tile, byte toTile){
        for(int i = 0; i < getWorldXSize(); i++){
            for(int j = 0; j < getWorldYSize(); j++){
                if(getTileID(i, j) == tile)
                    setTileID(toTile, i, j);
            }
        }
    }

    /**
     * Distroy the model.
     */
    public void distroy() {
        inEditorMenu.delete();
        inEditorMenu = null;
    }
}
