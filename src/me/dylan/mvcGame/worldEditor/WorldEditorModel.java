package me.dylan.mvcGame.worldEditor;

import me.dylan.mvcGame.game.gameObjects.MapModel;
import me.dylan.mvcGame.main.MainModel;

import java.util.HashMap;

public class WorldEditorModel extends MapModel{

    private boolean showInEditorMenu;

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
    }

    /****GETTERS****/

    public float getViewX() { return -getMainModel().getCamera2D().getxPos(); }
    public float getViewY() { return -getMainModel().getCamera2D().getyPos(); }
    public float getViewZoom() { return getMainModel().getCamera2D().getZoom(); }

    public boolean getShowInEditorMenu() {
        return showInEditorMenu;
    }

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

    public void setShowInEditorMenu(boolean showInEditorMenu) {
        this.showInEditorMenu = showInEditorMenu;
    }

    /*****OTHER SMALL LOGIC*****/

    public void moveView(float dx, float dy){
        setViewX(getViewX() - dx);
        setViewY(getViewY() - dy);
    }
}
