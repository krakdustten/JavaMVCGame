package me.dylan.mvcGame.menu.worldEditorPicker;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

/**
 * The state handler of the world editor picker.
 * You can call this a controller.
 *
 * @author Dylan Gybels
 */
public class StateMenuWorldEditorPicker extends State{
    private MenuWorldEditorPickerModel model;
    private MenuWorldEditorPickerContainer container;

    private MenuController menu;

    /**
     * Create a new state world editor picker.
     *
     * @param mainModel The main model.
     * @param stateHandler The main state handler.
     */
    public StateMenuWorldEditorPicker(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    /**
     * Initialize this state.
     *
     * @param previousState The ID of the previous state.
     */
    @Override
    public void init(int previousState) {
        menu = new MenuController(mainModel, "img/menu.png");
        menu.addGuiElement(new MenuModel.GuiLabel(0, 200, 250, 64, 1, "WORLD EDITOR", 1, 1, 1, 1));
        menu.addGuiElement(new MenuModel.GuiButton(0, 0, 250, 64, 2, "BACK", 1, 1, 1, 1, 0, 1, 0, 1));
        menu.setAlignMargin(0, 0, 0, 0);
        menu.setBackgroundColor(0.8f, 0.5f,0.4f, 1);

        model = new MenuWorldEditorPickerModel(mainModel);
        container = new MenuWorldEditorPickerContainer(model);
    }

    /**
     * Update this state.
     */
    @Override
    public void update() {
        menu.update();
        container.update();

        if(model.getWindowClosing())stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
        if(model.getMapSelected())stateHandler.changeState(StateHandler.STATE_WORLD_EDITOR);
    }

    /**
     * Render this state.
     */
    @Override
    public void render() {
        menu.render();
    }

    /**
     * De-initialize this state.
     */
    @Override
    public void deInit() {
        menu.delete();
        menu = null;
        container.distroy();
        container = null;
    }

    /**
     * Send a keyboard event to this state.
     *
     * @param window The window the event happened on.
     * @param key The key the event was on.
     * @param scancode The scan code of the key.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {

    }

    /**
     * Send a mouse position event to this state.
     *
     * @param window The window the event happened on.
     */
    @Override
    public void mousePosEvent(long window) {
        menu.mousePosEvent(window);
    }

    /**
     * Send a mouse button event to this state.
     *
     * @param window The window the event happened on.
     * @param button The mouse button the event was on.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {
        int id = menu.onClick(window, button, action, mods);
        switch (id){
            case 2:
                stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
                break;
        }
    }

    /**
     * Send a scroll event to this state.
     *
     * @param window The window the event happened on.
     * @param xOffset The x change of the scroll wheel.
     * @param yOffset The y change of the scroll wheel.
     */
    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) {

    }

    /**
     * Send a window resize event to this state.
     */
    @Override
    public void screenResizeEvent() {
        menu.screenResizeEvent();
    }
}
