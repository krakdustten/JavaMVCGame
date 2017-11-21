package me.dylan.mvcGame.menu.mainMenu;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

public class MainMenuController extends State {

    private MenuController menuController;

    public MainMenuController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {

        menuController = new MenuController(mainModel);
        menuController.addGuiElement(new MenuModel.GuiButton(0, 0, 100, 32, 1, "Wauw", 1, 1, 1, 1, 0, 1, 0, 1));
        menuController.addGuiElement(new MenuModel.GuiButton(0, 50, 150, 32, 2, "Niets", 1, 1, 1, 1, 0, 1, 0, 1));
        menuController.setAlignMargin(-1, -1, 10, 10);
    }

    @Override
    public void update() {
        menuController.update();
    }

    @Override
    public void render() {
        menuController.render();
    }

    @Override
    public void deInit() { }

    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) { }

    @Override
    public void mousePosEvent(long window) {
        menuController.mousePosEvent(window);
    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {
        int id = menuController.onClick(window, button, action, mods);
        if(id >= 0){
            System.out.println(id);
            stateHandler.changeState(StateHandler.STATE_MENU_LEVELS);
        }
    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) { }

    @Override
    public void screenResizeEvent() {
        menuController.screenResizeEvent();
    }
}
