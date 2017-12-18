package me.dylan.mvcGame.worldEditor;

import me.dylan.mvcGame.main.MainModel;

public class WorldEditorModel {
    private MainModel mainModel;

    public WorldEditorModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public MainModel getMainModel() { return mainModel; }

    public void setMainModel(MainModel mainModel) { this.mainModel = mainModel; }
}
