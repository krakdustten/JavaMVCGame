package me.dylan.mvcGame.menu;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

/**
 * The controller of the main menu.
 *
 * @author Dylan Gybels
 */
public class MainMenuController extends State {
    private MenuController menu;

    /**
     * Create a new main menu controller.
     *
     * @param mainModel The main model.
     * @param stateHandler The main state handler.
     */
    public MainMenuController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    /**
     * Initialize the main menu controller.
     *
     * @param previousState The ID of the previous state.
     */
    @Override
    public void init(int previousState) {
        mainModel.getCamera2D().setZoom(1);

        menu = new MenuController(mainModel, "img/menu.png");
        menu.addGuiElement(new MenuModel.GuiButton(0, 200, 250, 64, 1, "PLAY", 1, 1, 1, 1, 0, 1, 0, 1));
        menu.addGuiElement(new MenuModel.GuiButton(0, 100, 250, 64, 2, "EDITOR", 1, 1, 1, 1, 0, 1, 0, 1));
        menu.addGuiElement(new MenuModel.GuiButton(0, 0, 250, 64, 3, "QUIT", 1, 1, 1, 1, 0, 1, 0, 1));
        menu.setAlignMargin(0, 0, 0, 0);
        menu.setBackgroundColor(0.8f, 0.5f,0.4f, 1);
    }

    /**
     * Update the main menu controller.
     */
    @Override
    public void update() {
        menu.update();
    }

    /**
     * Render the main menu controller.
     */
    @Override
    public void render() {
        menu.render();
    }

    /**
     * De-initialize the main menu controller.
     */
    @Override
    public void deInit() {
        menu.delete();
        menu = null;
    }

    /**
     * Send a keyboard event to the main menu controller.
     *
     * @param window The window the event happened on.
     * @param key The key the event was on.
     * @param scancode The scan code of the key.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) { }

    /**
     * Send a mouse position event to the main menu controller.
     *
     * @param window The window the event happened on.
     */
    @Override
    public void mousePosEvent(long window) {
        menu.mousePosEvent(window);
    }

    /**
     * Send a mouse button event to the main menu controller.
     *
     * @param window The window the event happened on.
     * @param button The mouse button the event was on.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {
        int id = menu.onClick(window, button, action, mods);
        if(id >= 0){
            switch (id){
                case 1:stateHandler.changeState(StateHandler.STATE_MENU_LEVELS);break;
                case 2:stateHandler.changeState(StateHandler.STATE_MENU_WORLD_EDITOR);break;
                case 3:stateHandler.changeState(StateHandler.STATE_QUIT);break;
            }

        }
    }

    /**
     * Send a scroll event to the main menu controller.
     *
     * @param window The window the event happened on.
     * @param xOffset The x change of the scroll wheel.
     * @param yOffset The y change of the scroll wheel.
     */
    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) { }

    /**
     * Send a screen resize event to the main menu controller.
     */
    @Override
    public void screenResizeEvent() {
        menu.screenResizeEvent();
    }
}
