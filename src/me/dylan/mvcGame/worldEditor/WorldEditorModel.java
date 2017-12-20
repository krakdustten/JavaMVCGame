package me.dylan.mvcGame.worldEditor;

import me.dylan.mvcGame.game.gameObjects.MapModel;
import me.dylan.mvcGame.game.gameObjects.Tiles;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;

public class WorldEditorModel extends MapModel{

    private boolean showInEditorMenu;
    private MenuController inEditorMenu;
    private boolean windowClosing;

    private boolean editingSensor = false;
    private int editingSenserIndex = -1;
    private boolean editingRobot = false;

    private byte selectedTile;
    private int selectedColor = 256 * 256 * 256 - 1;

    public WorldEditorModel(MainModel mainModel, int worldXSize, int worldYSize){
        super(mainModel,worldXSize, worldYSize, null, null);
    }

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

    /****GETTERS****/

    public float getViewX() { return -getMainModel().getCamera2D().getxPos(); }
    public float getViewY() { return -getMainModel().getCamera2D().getyPos(); }
    public float getViewZoom() { return getMainModel().getCamera2D().getZoom(); }

    public boolean getShowInEditorMenu() {
        return showInEditorMenu;
    }
    public MenuController getInEditorMenu() { return inEditorMenu; }
    public boolean getWindowClosing() { return windowClosing; }

    public byte getSelectedTile() { return selectedTile; }
    public int getSelectedColor() { return selectedColor; }

    public boolean getEditingSensor() { return editingSensor; }
    public int getEditingSenserIndex() { return editingSenserIndex; }
    public boolean getEditingRobot() { return editingRobot; }

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

    public void setShowInEditorMenu(boolean showInEditorMenu) { this.showInEditorMenu = showInEditorMenu;inEditorMenu.updateView(); }

    public void setWindowClosing(boolean windowClosing) { this.windowClosing = windowClosing; }

    public void setSelectedTile(byte selectedTile) { this.selectedTile = selectedTile; }
    public void setSelectedColor(int selectedColor) { this.selectedColor = selectedColor; }

    public void setEditingSensor(boolean editingSensor) { this.editingSensor = editingSensor; }
    public void setEditingSenserIndex(int editingSenserIndex) { this.editingSenserIndex = editingSenserIndex; }
    public void setEditingRobot(boolean editingRobot) {
        this.editingRobot = editingRobot;
        getRobot().setX(getWorldXSize() / 2.0f);
        getRobot().setY(getWorldYSize() / 2.0f);
    }

    /*****OTHER SMALL LOGIC*****/

    public void moveView(float dx, float dy){
        setViewX(getViewX() - dx);
        setViewY(getViewY() - dy);
    }

    public void changeTileTo(byte toTile, int toColor, int tileX, int tileY) {
        byte oldTile = (byte) getTileID(tileX, tileY);

        switch (oldTile){
            case Tiles.START_ID:
                removeAllTilesOf(Tiles.START_ID, Tiles.FLOOR_ID);
                break;
            case Tiles.END_ID:
                removeAllTilesOf(Tiles.END_ID, Tiles.FLOOR_ID);
                break;
        }

        if(oldTile == toTile) {
            setTileID(Tiles.NO_TILE, tileX, tileY);
            return;
        }

        switch (toTile){
            case Tiles.START_ID:
            case Tiles.END_ID:
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

    public void distroy() {
        inEditorMenu.delete();
        inEditorMenu = null;
    }
}
