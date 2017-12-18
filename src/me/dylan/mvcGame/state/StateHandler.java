package me.dylan.mvcGame.state;

import me.dylan.mvcGame.game.controllers.GameController;
import me.dylan.mvcGame.main.MainGameThread;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.MainMenuController;
import me.dylan.mvcGame.menu.levelMenu.StateMenuLevels;
import me.dylan.mvcGame.menu.worldEditorPicker.StateMenuWorldEditorPicker;
import me.dylan.mvcGame.worldEditor.WorldEditorController;

public class StateHandler{


    private int currentState = -1;
    private int lastState = -1;

    private MainModel mainModel;
    private MainGameThread mainGameThread;

    public static final int STATE_QUIT = -1;
    public static final int STATE_MENU_MAIN = 0;
    public static final int STATE_MENU_WORLD_EDITOR= 1;
    public static final int STATE_MENU_LEVELS = 2;
    public static final int STATE_GAME = 3;
    public static final int STATE_WORLD_EDITOR = 4;
    public static final int STATE_MAX = STATE_WORLD_EDITOR;

    public static final State[] states = new State[STATE_MAX + 1];

    public StateHandler(MainModel mainModel, MainGameThread mainGameThread) {
        this.mainModel = mainModel;
        this.mainGameThread = mainGameThread;

        states[STATE_MENU_MAIN] = new MainMenuController(mainModel, this);
        states[STATE_MENU_WORLD_EDITOR] = new StateMenuWorldEditorPicker(mainModel, this);
        states[STATE_MENU_LEVELS] = new StateMenuLevels(mainModel, this);
        states[STATE_GAME] = new GameController(mainModel, this);
        states[STATE_WORLD_EDITOR] = new WorldEditorController(mainModel, this);

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
        if(newState == STATE_QUIT && currentState != STATE_QUIT){
            states[currentState].deInit();
            currentState = STATE_QUIT;
            mainGameThread.stop();
            return STATE_QUIT;
        }
        return -2;
    }

    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {
        if(currentState >= 0)states[currentState].keyboardEvent(window, key, scancode, action, mods);
    }

    public void mousePosEvent(long window) {
        if(currentState >= 0)states[currentState].mousePosEvent(window);
    }

    public void mouseButtonEvent(long window, int button, int action, int mods) {
        if(currentState >= 0)states[currentState].mouseButtonEvent(window, button, action, mods);
    }

    public void scrollEvent(long window, double xOffset, double yOffset) {
        if(currentState >= 0)states[currentState].scrollEvent(window, xOffset, yOffset);
    }

    public void screenResizeEvent() {
        if(currentState >= 0)states[currentState].screenResizeEvent();
    }

    public void update() {
        if(currentState >= 0)states[currentState].update();
    }

    public void render() {
        if(currentState >= 0)states[currentState].render();
    }

    public void deInit() {
        if(currentState >= 0)states[currentState].deInit();
    }


}
