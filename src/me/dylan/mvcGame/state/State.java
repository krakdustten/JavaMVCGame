package me.dylan.mvcGame.state;

import me.dylan.mvcGame.main.MainViewer;

public abstract class State {
    protected MainViewer mainViewer;
    protected StateHandler stateHandler;

    public State(MainViewer mainViewer, StateHandler stateHandler){
        this.mainViewer = mainViewer;
        this.stateHandler = stateHandler;
    }

    public abstract void init(int previousState);
    public abstract void update();
    public abstract void render(MainViewer mainViewer);
    public abstract void deInit();

    public abstract void keyboardEvent(long window, int key, int scancode, int action, int mods);
    public abstract void mousePosEvent(long window, double xPos, double yPos);
    public abstract void mouseButtonEvent(long window, int button, int action, int mods);
    public abstract void scrollEvent(long window, double xOffset, double yOffset);
}
