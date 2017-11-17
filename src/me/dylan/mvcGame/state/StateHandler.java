package me.dylan.mvcGame.state;

import me.dylan.mvcGame.main.MainGameThread;
import me.dylan.mvcGame.main.MainViewer;
import me.dylan.mvcGame.menu.mainMenu.MainMenuController;

public class StateHandler{

    private int currentState = -1;
    private int lastState = -1;

    MainViewer mainViewer;
    MainGameThread mainGameThread;

    public static final int STATE_MENU_MAIN = 0;
    public static final int STATE_MENU_OPTIONS= 1;
    public static final int STATE_MENU_LEVELS = 2;
    public static final int STATE_GAME = 3;
    public static final int STATE_MAX = STATE_GAME;

    public static final State[] states = new State[STATE_MAX + 1];

    public StateHandler(MainViewer mainViewer, MainGameThread mainGameThread) {
        this.mainViewer = mainViewer;
        this.mainGameThread = mainGameThread;

        states[STATE_MENU_MAIN] = new MainMenuController(mainViewer, this);
        states[STATE_MENU_OPTIONS] = new StateMenuOptions(mainViewer, this);
        states[STATE_MENU_LEVELS] = new StateMenuLevels(mainViewer, this);
        states[STATE_GAME] = new StateGame(mainViewer, this);

        changeState(STATE_MENU_MAIN);
    }

    public int getCurrentStateId(){return currentState;}

    public int changeState(int newState) {
        if(newState != currentState && newState >= 0 && newState <= STATE_MAX){
            lastState = currentState;
            currentState = newState;
            if(lastState >= 0)states[lastState].deInit();
            states[currentState].init(lastState);
            return lastState;
        }
        if(newState == -1 && currentState != -1){
            states[currentState].deInit();
            mainGameThread.stop();
            return -1;
        }
        return -2;
    }

    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {
        if(currentState >= 0)states[currentState].keyboardEvent(window, key, scancode, action, mods);
    }

    public void mousePosEvent(long window, double xPos, double yPos) {
        if(currentState >= 0)states[currentState].mousePosEvent(window, xPos, yPos);
    }

    public void mouseButtonEvent(long window, int button, int action, int mods) {
        if(currentState >= 0)states[currentState].mouseButtonEvent(window, button, action, mods);
    }

    public void scrollEvent(long window, double xOffset, double yOffset) {
        if(currentState >= 0)states[currentState].scrollEvent(window, xOffset, yOffset);
    }

    public void update() {
        if(currentState >= 0)states[currentState].update();
    }

    public void render(MainViewer mainViewer) {
        if(currentState >= 0)states[currentState].render(mainViewer);
    }

    public void deInit() {
        if(currentState >= 0)states[currentState].deInit();
    }
}
