package me.dylan.mvcGame.menu;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

//TODO javadoc
public class MainMenuController extends State {

    private MenuController menu;

    public MainMenuController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        mainModel.getCamera2D().setZoom(1);

        menu = new MenuController(mainModel, "img/menu.png");
        menu.addGuiElement(new MenuModel.GuiButton(0, 200, 250, 64, 1, "PLAY", 1, 1, 1, 1, 0, 1, 0, 1));
        menu.addGuiElement(new MenuModel.GuiButton(0, 100, 250, 64, 2, "EDITOR", 1, 1, 1, 1, 0, 1, 0, 1));
        menu.addGuiElement(new MenuModel.GuiButton(0, 0, 250, 64, 3, "QUIT", 1, 1, 1, 1, 0, 1, 0, 1));
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
    }

    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) { }

    @Override
    public void mousePosEvent(long window) {
        menu.mousePosEvent(window);
    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {
        int id = menu.onClick(window, button, action, mods);
        if(id >= 0){
            switch (id){
                case 1:stateHandler.changeState(StateHandler.STATE_MENU_LEVELS);break;
                case 2:stateHandler.changeState(StateHandler.STATE_MENU_WORLD_EDITOR);break;
                case 3:stateHandler.changeState(StateHandler.STATE_QUIT);break;
            }

        }
    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) { }

    @Override
    public void screenResizeEvent() {
        menu.screenResizeEvent();
    }
}
