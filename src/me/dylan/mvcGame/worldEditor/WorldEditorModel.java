package me.dylan.mvcGame.worldEditor;

import me.dylan.mvcGame.game.gameObjects.MapModel;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;

import java.util.HashMap;

public class WorldEditorModel extends MapModel{

    private boolean showInEditorMenu;
    private MenuController inEditorMenu;

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

    /*****OTHER SMALL LOGIC*****/

    public void moveView(float dx, float dy){
        setViewX(getViewX() - dx);
        setViewY(getViewY() - dy);
    }

    public void distroy() {
        inEditorMenu.delete();
        inEditorMenu = null;
    }
}
