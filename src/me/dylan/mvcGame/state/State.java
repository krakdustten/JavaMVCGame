package me.dylan.mvcGame.state;

import me.dylan.mvcGame.main.MainModel;

/**
 * The master class of all the states.
 *
 * @author Dylan Gybels
 */
public abstract class State {
    /**The main model.*/
    protected MainModel mainModel;
    /**The main state handler instance.*/
    protected StateHandler stateHandler;

    /**
     * Create a new state.
     *
     * @param mainModel The main model.
     * @param stateHandler The main state handler.
     */
    public State(MainModel mainModel, StateHandler stateHandler){
        this.mainModel = mainModel;
        this.stateHandler = stateHandler;
    }

    /**
     * Initialize the state.
     *
     * @param previousState The ID of the previous state.
     * */
    public abstract void init(int previousState);
    /**Update the state*/
    public abstract void update();
    /**Render the state*/
    public abstract void render();
    /**De-initialize the state*/
    public abstract void deInit();

    /**
     * Send a key board event to the state.
     *
     * @param window The window the event happened on.
     * @param key The key the event was on.
     * @param scancode The scan code of the key.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    public abstract void keyboardEvent(long window, int key, int scancode, int action, int mods);

    /**
     * Send a mouse position event to the state.
     * The mouse position can be found in the main model.
     *
     * @param window The window the event happened on.
     */
    public abstract void mousePosEvent(long window);

    /**
     * Send a mouse button event to the state.
     *
     * @param window The window the event happened on.
     * @param button The mouse button the event was on.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    public abstract void mouseButtonEvent(long window, int button, int action, int mods);

    /**
     * Send a scroll event to the state.
     *
     * @param window The window the event happened on.
     * @param xOffset The x change of the scroll wheel.
     * @param yOffset The y change of the scroll wheel.
     */
    public abstract void scrollEvent(long window, double xOffset, double yOffset);

    /**
     * Send a screen resize event to the state.
     */
    public abstract void screenResizeEvent();
}
