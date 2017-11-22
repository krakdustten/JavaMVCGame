package me.dylan.mvcGame.menu;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

public class OptionsMenuController extends State {

    public OptionsMenuController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        System.out.println("OptionsMenuController: " + previousState);
    }

    @Override
    public void update() {
        stateHandler.changeState(StateHandler.STATE_MENU_LEVELS);
    }

    @Override
    public void render() {

    }

    @Override
    public void deInit() {

    }

    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {

    }

    @Override
    public void mousePosEvent(long window) {

    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {

    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) {

    }

    @Override
    public void screenResizeEvent() {

    }
}
