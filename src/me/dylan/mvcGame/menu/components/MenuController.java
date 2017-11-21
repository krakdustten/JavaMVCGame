package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.main.MainModel;
import org.lwjgl.glfw.GLFW;

public class MenuController {
    private MenuModel model;
    private MenuView view;

    public MenuController(MainModel mainModel){
        model = new MenuModel(mainModel);
        view = new MenuView(model, "./img/menu.png");

        model.setxAlign(0);

        model.getMainModel().getCamera().setZoom(2);
        model.getMainModel().getCamera().setScreenPosition(-50, -50);
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
        if(button != GLFW.GLFW_MOUSE_BUTTON_1 || action != GLFW.GLFW_PRESS)return -1;
        int mouseX = (int)model.getMainModel().getMouseXWorld();
        int mouseY = (int)model.getMainModel().getMouseYWorld();
        for(MenuModel.GuiElement element : model.getAllGuiElements()) {
            if (element instanceof MenuModel.GuiButton) {
                MenuModel.GuiButton but = (MenuModel.GuiButton) element;
                if(mouseX > (but.x + model.getDrawXstart()) && mouseX < (but.x + but.width + model.getDrawXstart()) && mouseY > (but.y + model.getDrawYstart()) && mouseY < (but.y + but.height + model.getDrawYstart()))
                    return but.id;
            }
        }

        return -1;
    }

    public void mousePosEvent(long window) {
        int mouseX = (int)model.getMainModel().getMouseXWorld();
        int mouseY = (int)model.getMainModel().getMouseYWorld();
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof  MenuModel.GuiButton){
                MenuModel.GuiButton but = (MenuModel.GuiButton) element;
                boolean onBut = mouseX > (but.x + model.getDrawXstart()) && mouseX < (but.x + but.width + model.getDrawXstart()) && mouseY > (but.y + model.getDrawYstart()) && mouseY < (but.y + but.height + model.getDrawYstart());
                if(onBut != but.hover){
                    but.hover = onBut;
                    model.updateView();
                }
            }
        }
    }

    //TODO add model functions
    //TODO window resize event
}
