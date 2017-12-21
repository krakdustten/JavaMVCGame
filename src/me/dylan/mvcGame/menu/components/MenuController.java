package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.main.MainModel;
import org.lwjgl.glfw.GLFW;

//TODO javadoc
public class MenuController {
    private MenuModel model;
    private MenuView view;

    public MenuController(MainModel mainModel, String menuImg){
        model = new MenuModel(mainModel);
        view = new MenuView(model, menuImg);
    }

    public boolean addGuiElement(MenuModel.GuiElement element){
        return model.addGuiElement(element);
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
        float z = model.getMainModel().getCamera2D().getZoom();
        for(MenuModel.GuiElement element : model.getAllGuiElements()) {
            if (element instanceof MenuModel.GuiButton) {
                MenuModel.GuiButton but = (MenuModel.GuiButton) element;
                if(mouseX > (but.x / z + model.getDrawXstart()) &&
                        mouseX < ((but.x + but.width) / z + model.getDrawXstart()) &&
                        mouseY > (but.y  / z  + model.getDrawYstart()) &&
                        mouseY < ((but.y + but.height)  / z + model.getDrawYstart()))
                    return but.id;
            }
        }

        return -1;
    }

    public void mousePosEvent(long window) {
        int mouseX = (int)model.getMainModel().getMouseXWorld();
        int mouseY = (int)model.getMainModel().getMouseYWorld();
        float z = model.getMainModel().getCamera2D().getZoom();
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof  MenuModel.GuiButton){
                MenuModel.GuiButton but = (MenuModel.GuiButton) element;
                boolean onBut = mouseX > (but.x / z + model.getDrawXstart()) && mouseX < ((but.x + but.width) / z + model.getDrawXstart()) && mouseY > (but.y / z + model.getDrawYstart()) && mouseY < ((but.y + but.height) / z + model.getDrawYstart());
                if(onBut != but.hover){
                    but.hover = onBut;
                    model.updateView();
                }
            }
        }
    }

    public void screenResizeEvent(){
        model.updateView();
    }

    public void setAlignMargin(int xAlign, int yAlign, int xMargin, int yMargin){
        setxAlign(xAlign);
        setyAlign(yAlign);
        setxMargin(xMargin);
        setyMargin(yMargin);
    }
    public void setxAlign(int xAlign) { model.setxAlign(xAlign); }
    public void setyAlign(int yAlign) { model.setyAlign(yAlign); }
    public void setxMargin(int xMargin) { model.setxMargin(xMargin); }
    public void setyMargin(int yMargin) { model.setyMargin(yMargin); }

    public void setBackgroundColor(float backR, float backG, float backB, float backA){
        setBackR(backR);
        setBackG(backG);
        setBackB(backB);
        setBackA(backA);
    }
    public void setBackR(float backR) { model.setBackR(backR); }
    public void setBackG(float backG) { model.setBackG(backG); }
    public void setBackB(float backB) { model.setBackB(backB); }
    public void setBackA(float backA) { model.setBackA(backA); }

    public void updateView(){model.updateView();}

    public void delete(){
        view.delete();
        model = null;
        view = null;
    }

}
