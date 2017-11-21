package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.main.MainModel;

public class MenuController {
    private MenuModel model;
    private MenuView view;

    public MenuController(MainModel mainModel){
        model = new MenuModel(mainModel);
        view = new MenuView(model, "./img/menu.png");
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

    public int onClick(long window, int button, int action, int mods){
        


        return -1;
    }
    //TODO click detection for buttons
}
