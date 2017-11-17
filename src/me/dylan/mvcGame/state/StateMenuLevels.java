package me.dylan.mvcGame.state;

import me.dylan.mvcGame.main.MainViewer;

public class StateMenuLevels extends State{

    public StateMenuLevels(MainViewer mainViewer, StateHandler stateHandler) {
        super(mainViewer, stateHandler);
    }

    @Override
    public void init(int previousState) {
        System.out.println("StateMenuLevels: " + previousState);
    }

    @Override
    public void update() {
        stateHandler.changeState(StateHandler.STATE_GAME);
    }

    @Override
    public void render(MainViewer mainViewer) {

    }

    @Override
    public void deInit() {

    }

    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {

    }

    @Override
    public void mousePosEvent(long window, double xPos, double yPos) {

    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {

    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) {

    }
}
