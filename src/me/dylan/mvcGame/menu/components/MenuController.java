package me.dylan.mvcGame.menu.components;

public class MenuController {
    private MenuModel model;
    private MenuView view;

    public MenuController(){
        model = new MenuModel();
        view = new MenuView(model, "");
    }
}
