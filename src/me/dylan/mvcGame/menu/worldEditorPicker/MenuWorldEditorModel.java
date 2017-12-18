package me.dylan.mvcGame.menu.worldEditorPicker;

import me.dylan.mvcGame.main.MainModel;

public class MenuWorldEditorModel {
    private MainModel mainModel;

    private boolean windowClosing;
    private boolean mapSelected;

    public MenuWorldEditorModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public MainModel getMainModel() { return mainModel; }
    public boolean getWindowClosing() { return windowClosing; }
    public boolean getMapSelected() { return mapSelected; }

    public void setWindowClosing(boolean windowClosing) { this.windowClosing = windowClosing; }
    public void setMapSelected(boolean mapSelected) { this.mapSelected = mapSelected; }
}
