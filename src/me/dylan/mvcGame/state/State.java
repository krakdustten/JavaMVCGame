package me.dylan.mvcGame.state;

import me.dylan.mvcGame.main.MainModel;

public abstract class State {
    protected MainModel mainModel;
    protected StateHandler stateHandler;

    public State(MainModel mainModel, StateHandler stateHandler){
        this.mainModel = mainModel;
        this.stateHandler = stateHandler;
    }

    public abstract void init(int previousState);
    public abstract void update();
    public abstract void render();
    public abstract void deInit();

    public abstract void keyboardEvent(long window, int key, int scancode, int action, int mods);
    public abstract void mousePosEvent(long window, double xPos, double yPos);
    public abstract void mouseButtonEvent(long window, int button, int action, int mods);
    public abstract void scrollEvent(long window, double xOffset, double yOffset);
}
