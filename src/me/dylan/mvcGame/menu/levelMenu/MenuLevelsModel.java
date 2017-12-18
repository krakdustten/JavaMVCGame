package me.dylan.mvcGame.menu.levelMenu;

import me.dylan.mvcGame.main.MainModel;

public class MenuLevelsModel {
    private MainModel mainModel;

    private boolean windowClosing = false;
    private boolean mapSelected = false;

    public MenuLevelsModel(MainModel mainModel){
        this.mainModel = mainModel;
    }

    public MainModel getMainModel() { return mainModel; }
    public Boolean getWindowClosing() { return windowClosing; }
    public boolean getMapSelected() { return mapSelected; }

    public void setMainModel(MainModel mainModel) { this.mainModel = mainModel; }
    public void setWindowClosing(boolean windowClosing) { this.windowClosing = windowClosing; }
    public void setMapSelected(boolean mapSelected) { this.mapSelected = mapSelected; }
}
