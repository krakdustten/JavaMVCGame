package me.dylan.mvcGame.state;

import me.dylan.mvcGame.game.controllers.GameController;
import me.dylan.mvcGame.main.MainGameThread;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.MainMenuController;
import me.dylan.mvcGame.menu.levelMenu.StateMenuLevels;
import me.dylan.mvcGame.menu.worldEditorPicker.StateMenuWorldEditorPicker;
import me.dylan.mvcGame.worldEditor.WorldEditorController;

/**
 * The main state handler.
 *
 * @author Dylan Gybels
 */
public class StateHandler{
    private int currentState = -1;
    private int lastState = -1;

    private MainModel mainModel;
    private MainGameThread mainGameThread;

    /**Quit the game.*/
    public static final int STATE_QUIT = -1;
    /**Main menu state.*/
    public static final int STATE_MENU_MAIN = 0;
    /**World editor menu state.*/
    public static final int STATE_MENU_WORLD_EDITOR= 1;
    /**Level picker state.*/
    public static final int STATE_MENU_LEVELS = 2;
    /**Game state.*/
    public static final int STATE_GAME = 3;
    /**World editor state.*/
    public static final int STATE_WORLD_EDITOR = 4;
    /**The maximum state value.*/
    public static final int STATE_MAX = STATE_WORLD_EDITOR;

    private static final State[] states = new State[STATE_MAX + 1];

    /**
     * Create a new state handler.
     *
     * @param mainModel The main model.
     * @param mainGameThread The main game thread.
     */
    public StateHandler(MainModel mainModel, MainGameThread mainGameThread) {
        this.mainModel = mainModel;
        this.mainGameThread = mainGameThread;

        //populate the states array
        states[STATE_MENU_MAIN] = new MainMenuController(mainModel, this);
        states[STATE_MENU_WORLD_EDITOR] = new StateMenuWorldEditorPicker(mainModel, this);
        states[STATE_MENU_LEVELS] = new StateMenuLevels(mainModel, this);
        states[STATE_GAME] = new GameController(mainModel, this);
        states[STATE_WORLD_EDITOR] = new WorldEditorController(mainModel, this);

        changeState(STATE_MENU_MAIN);//start in the main menu
    }

    /**
     * get the id of the current state
     *
     * @return The id of the current state.
     */
    public int getCurrentStateId(){return currentState;}

    /**
     * Change state to a new state.
     *
     * @param newState The new state.
     * @return the last state.
     */
    public int changeState(int newState) {
        if(newState != currentState && newState >= 0 && newState <= STATE_MAX){//good next state
            //de init last state and init new state
            lastState = currentState;
            currentState = newState;
            if(lastState >= 0)states[lastState].deInit();
            states[currentState].init(lastState);
            return lastState;
        }
        if(newState == STATE_QUIT && currentState != STATE_QUIT){//quit state
            //de init last state and quit
            states[currentState].deInit();
            currentState = STATE_QUIT;
            mainGameThread.stop();
            return STATE_QUIT;
        }
        return -2;//something when wrong
    }

    /**
     * Update.
     */
    public void update() {
        if(currentState >= 0)states[currentState].update();
    }

    /**
     * Render.
     */
    public void render() {
        if(currentState >= 0)states[currentState].render();
    }

    /**
     * Keyboard event.
     *
     * @param window The window the event happened on.
     * @param key The key the event happened on.
     * @param scancode The scancode of the key.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {
        if(currentState >= 0)states[currentState].keyboardEvent(window, key, scancode, action, mods);
    }

    /**
     * Mouse position event.
     *
     * @param window The window the event happened on.
     */
    public void mousePosEvent(long window) {
        if(currentState >= 0)states[currentState].mousePosEvent(window);
    }

    /**
     * Mouse button event.
     *
     * @param window The window the event happened on.
     * @param button The mouse button the event happened on.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    public void mouseButtonEvent(long window, int button, int action, int mods) {
        if(currentState >= 0)states[currentState].mouseButtonEvent(window, button, action, mods);
    }

    /**
     * Scroll event.
     *
     * @param window The window the event happened on.
     * @param xOffset The x change of the scroll wheel.
     * @param yOffset The y change of the scroll wheel.
     */
    public void scrollEvent(long window, double xOffset, double yOffset) {
        if(currentState >= 0)states[currentState].scrollEvent(window, xOffset, yOffset);
    }

    /**
     * Screen resize event.
     */
    public void screenResizeEvent() {
        if(currentState >= 0)states[currentState].screenResizeEvent();
    }

    /**
     * De init the state handler.
     */
    public void deInit() {
        if(currentState >= 0)states[currentState].deInit();
    }
}
