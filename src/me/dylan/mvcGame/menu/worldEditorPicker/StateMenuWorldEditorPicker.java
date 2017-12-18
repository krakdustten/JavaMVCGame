package me.dylan.mvcGame.menu.worldEditorPicker;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

public class StateMenuWorldEditorPicker extends State{
    private MenuWorldEditorModel model;
    private MenuWorldEditorContainer container;

    private MenuController menu;

    public StateMenuWorldEditorPicker(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        menu = new MenuController(mainModel, "img/menu.png");
        menu.addGuiElement(new MenuModel.GuiLabel(0, 200, 250, 64, 1, "WORLD EDITOR", 1, 1, 1, 1));
        menu.addGuiElement(new MenuModel.GuiButton(0, 0, 250, 64, 2, "BACK", 1, 1, 1, 1, 0, 1, 0, 1));
        menu.setAlignMargin(0, 0, 0, 0);
        menu.setBackgroundColor(0.8f, 0.5f,0.4f, 1);

        model = new MenuWorldEditorModel(mainModel);
        container = new MenuWorldEditorContainer(model);
    }

    @Override
    public void update() {
        menu.update();
        container.update();

        if(model.getWindowClosing())stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
        if(model.getMapSelected())stateHandler.changeState(StateHandler.STATE_WORLD_EDITOR);
    }

    @Override
    public void render() {
        menu.render();
    }

    @Override
    public void deInit() {
        menu.delete();
        menu = null;
        container.distroy();
        container = null;
    }

    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {

    }

    @Override
    public void mousePosEvent(long window) {
        menu.mousePosEvent(window);
    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {
        int id = menu.onClick(window, button, action, mods);
        switch (id){
            case 2:
                stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
                break;
        }
    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) {

    }

    @Override
    public void screenResizeEvent() {
        menu.screenResizeEvent();
    }
}
