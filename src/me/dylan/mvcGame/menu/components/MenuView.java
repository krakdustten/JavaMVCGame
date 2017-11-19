package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.main.MainViewer;

public class MenuView {
    private MenuModel model;

    public MenuView(MenuModel model){
        this.model = model;
    }

    public void update(){
        if(!model.needUpdating())return;

        //update VBO
    }

    public void draw(MainViewer mainViewer){

    }
}
