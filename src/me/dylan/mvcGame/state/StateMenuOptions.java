package me.dylan.mvcGame.state;

import me.dylan.mvcGame.main.MainModel;

public class StateMenuOptions extends State {

    public StateMenuOptions(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        System.out.println("StateMenuOptions: " + previousState);
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
}
