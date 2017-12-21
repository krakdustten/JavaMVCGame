package me.dylan.mvcGame.menu.levelMenu;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;
import org.lwjgl.system.CallbackI;

//TODO javadoc
public class StateMenuLevels extends State {
    private MenuLevelsContainer container;
    private MenuLevelsModel model;
    private MenuController menu;

    public StateMenuLevels(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        model = new MenuLevelsModel(mainModel);
        container = new MenuLevelsContainer(model);

        menu = new MenuController(mainModel, "img/menu.png");
        menu.addGuiElement(new MenuModel.GuiLabel(0, 200, 250, 64, 1, "LEVELS", 1, 1, 1, 1));
        menu.addGuiElement(new MenuModel.GuiButton(0, 0, 250, 64, 2, "BACK", 1, 1, 1, 1, 0, 1, 0, 1));
        menu.setAlignMargin(0, 0, 0, 0);
        menu.setBackgroundColor(0.8f, 0.5f,0.4f, 1);
    }
    @Override
    public void update() {
        container.update();
        menu.update();

        if (model.getWindowClosing()) {
            stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
        }
        if (model.getMapSelected()) {
            stateHandler.changeState(StateHandler.STATE_GAME);
        }
    }
    @Override
    public void render() {
        menu.render();
    }

    @Override
    public void deInit() {
        container.distroy();
        container = null;
        menu.delete();
        menu = null;
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
