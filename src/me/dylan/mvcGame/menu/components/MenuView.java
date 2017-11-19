package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.main.MainViewer;

public class MenuView {
    private MenuModel model;
    private int texture_id;
    private int vbo_id;
    private int drawAmount = 0;

    public MenuView(MenuModel model, String menuImg){
        this.model = model;

        update();
    }

    public void update(){
        if(!model.needUpdating())return;

        //update VBO

    }

    public void draw(MainViewer mainViewer){
        //draw VBO
    }
}
