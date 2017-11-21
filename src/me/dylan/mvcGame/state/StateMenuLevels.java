package me.dylan.mvcGame.state;

import me.dylan.mvcGame.main.MainModel;

public class StateMenuLevels extends State{

    public StateMenuLevels(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        System.out.println("StateMenuLevels: " + previousState);
        temp = 0;
    }


    private int temp = 0;
    @Override
    public void update() {
        temp++;
        if(temp == 100)stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
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
