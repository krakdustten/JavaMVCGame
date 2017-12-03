package me.dylan.mvcGame.menu;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

public class OptionsMenuController extends State {
    private MenuController menu;
    private int perviousState = StateHandler.STATE_QUIT;

    public OptionsMenuController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        this.perviousState = previousState;
        menu = new MenuController(mainModel, "img/menu.png");
        menu.addGuiElement(new MenuModel.GuiLabel(0, 300, 250, 64, 0, "OPTIONS", 1, 1, 1, 1));
        menu.addGuiElement(new MenuModel.GuiButton(0, 0, 250, 64, 1, "BACK", 1, 1, 1, 1, 0, 1, 0, 1));
        menu.setAlignMargin(0, 0, 0, 0);
        menu.setBackgroundColor(0.8f, 0.5f,0.4f, 1);
    }

    @Override
    public void update() {
        menu.update();
    }

    @Override
    public void render() {
        menu.render();
    }

    @Override
    public void deInit() {
        menu.delete();
        menu = null;
        perviousState = StateHandler.STATE_QUIT;
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
        int buttomId = menu.onClick(window, button, action, mods);
        if(buttomId >= 0){
            stateHandler.changeState(perviousState);
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
