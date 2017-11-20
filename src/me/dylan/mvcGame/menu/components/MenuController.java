package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.main.MainModel;

public class MenuController {
    private MenuModel model;
    private MenuView view;

    public MenuController(MainModel mainModel){
        model = new MenuModel(mainModel);
        view = new MenuView(model, "./img/test.jpg");
    }

    public void addGuiElement(MenuModel.GuiElement element){
        model.addGuiElement(element);
    }

    public void update() {
        view.update();
    }

    public void render() {
        view.draw();
    }

    //TODO methods to add extra menu elements
    //TODO click detection for buttons
}
