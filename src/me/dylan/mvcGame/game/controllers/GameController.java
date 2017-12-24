package me.dylan.mvcGame.game.controllers;

import me.dylan.mvcGame.game.GameMapLoader;
import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.MapModel;
import me.dylan.mvcGame.game.gameViewers.GameView;
import me.dylan.mvcGame.game.gameViewers.CodeIDEContainer;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;
import org.lwjgl.glfw.GLFW;

import javax.swing.*;

/**
 * The main controller for the game.
 *
 * @author Dylan Gybels
 */
public class GameController extends State {
    private GameModel model;
    private GameView view;
    private CodeIDEContainer codeIDEContainer;
    private RobotPlayerController playerController;

    private boolean keyPressed[] = new boolean[6];//UP, DOWN, LEFT, RIGHT, ZOOM IN, ZOOM OUT
    private boolean mouseKeyPressed = false;
    private double oldMouseX, oldMouseY;

    /**
     * Create a new main game controller.
     *
     * @param mainModel The main model.
     * @param stateHandler The main state handler.
     */
    public GameController(MainModel mainModel, StateHandler stateHandler) { super(mainModel, stateHandler); }

    /**
     * Initialize the main game controller.
     *
     * @param previousState The ID of the previous state.
     */
    @Override
    public void init(int previousState) {
        //load the map or save
        MapModel map = null;
        if(mainModel.getGameFileToLoad().endsWith(".mapd")){
            map = GameMapLoader.loadMap(mainModel, mainModel.getGameFileToLoad());
        }else if(mainModel.getGameFileToLoad().endsWith(".savd")){
            map = GameMapLoader.loadSave(mainModel, mainModel.getGameFileToLoad());
        }else
            stateHandler.changeState(StateHandler.STATE_MENU_MAIN);

        //create the necessary objects
        if(map == null) stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
        this.model = new GameModel(map);

        view = new GameView(this.model);

        playerController = new RobotPlayerController(this.model);
        codeIDEContainer = new CodeIDEContainer(this.model);
    }

    /**
     * Update the main game controller.
     */
    @Override
    public void update() {
        if(!model.getGameMenuShown()) {
            if (model.isGameRunning()) updateGame();

            //handle the game overlay (You lost or you won)
            else if(model.getGameOverlay() == null && (model.getLost() || model.getWon())){
                MenuController overlay = new MenuController(mainModel, "img/menu.png");
                overlay.addGuiElement(new MenuModel.GuiLabel(0, 100, 350, 64, 3, model.getWon() ? "YOU WON" : "YOU LOST", 1, 1, 1, 1));
                overlay.addGuiElement(new MenuModel.GuiLabel(0, 0, 350, 64, 4, String.format("Time: %.2fs", model.getGameTime()), 1, 1, 1, 1));
                overlay.setAlignMargin(0, 0, 0, 0);
                overlay.setBackgroundColor(0.8f, 0.5f,0.4f, 0.3f);
                model.setGameOverlay(overlay);
            }

            //handle a game reset
            if (model.getShouldGameReset()) {
                playerController = new RobotPlayerController(this.model);
                model.setLost(false);
                model.setWon(false);
                model.setGameTime(0);
                model.setShouldGameReset(false);
            }

            //handle key presses
            if (keyPressed[0]) model.moveView(0, -25f / model.getViewZoom());
            if (keyPressed[1]) model.moveView(0, 25f / model.getViewZoom());
            if (keyPressed[2]) model.moveView(25f / model.getViewZoom(), 0);
            if (keyPressed[3]) model.moveView(-25f / model.getViewZoom(), 0);
            if (keyPressed[4]) model.setViewZoom(model.getViewZoom() * 1.03f);
            if (keyPressed[5]) model.setViewZoom(model.getViewZoom() * 0.97f);
        }else
            model.getInGameMenu().update();//update in game menu
        //update the rest of the objects
        view.update();
        playerController.update();
        codeIDEContainer.update();

        if(model.getWindowClosing())stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
        if(model.getGameOverlay() != null) model.getGameOverlay().update();
    }

    private void updateGame(){
        model.updateGameTime();
        playerController.updateGame();
    }

    /**
     * Render the main game.
     */
    @Override
    public void render() {
        view.render();
        playerController.render();
        if(model.getGameMenuShown())model.getInGameMenu().render();
        if(model.getGameOverlay() != null) model.getGameOverlay().render();
    }

    /**
     * De initialize the main game controller.
     */
    @Override
    public void deInit() {
        if(model != null)GameMapLoader.saveSave(model, "saves/maps/autoSave.savd");
        if(codeIDEContainer != null)codeIDEContainer.distroy();
        codeIDEContainer = null;
        if(playerController != null)playerController.distroy();
        playerController = null;
        if(model.getGameOverlay() != null) model.getGameOverlay().delete();
        if(model != null)model.distroy();
        model = null;

    }

    /**
     * Handle a keyboard event.
     * @param window The window the event happened on.
     * @param key The key the event was on.
     * @param scancode The scan code of the key.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {
        switch (key){
            case GLFW.GLFW_KEY_W:
                handleKey(0, action);
                break;
            case GLFW.GLFW_KEY_S:
                handleKey(1, action);
                break;
            case GLFW.GLFW_KEY_A:
                handleKey(2, action);
                break;
            case GLFW.GLFW_KEY_D:
                handleKey(3, action);
                break;
            case GLFW.GLFW_KEY_Q:
                handleKey(4, action);
                break;
            case GLFW.GLFW_KEY_E:
                handleKey(5, action);
                break;
            case GLFW.GLFW_KEY_ESCAPE:
                if(action == GLFW.GLFW_PRESS) model.setGameMenuShown(!model.getGameMenuShown());
        }
    }

    private void handleKey(int arrayKey, int action){
        if(action == GLFW.GLFW_PRESS) keyPressed[arrayKey] = true;
        if(action == GLFW.GLFW_RELEASE) keyPressed[arrayKey]= false;
    }

    /**
     * Handle a mouse position event.
     * @param window The window the event happened on.
     */
    @Override
    public void mousePosEvent(long window) {
        if(!model.getGameMenuShown()) {
            double mouseX = mainModel.getMouseX();
            double mouseY = mainModel.getMouseY();

            if (mouseKeyPressed) {
                double dx = oldMouseX - mouseX;
                double dy = oldMouseY - mouseY;

                dx = dx / model.getViewZoom();
                dy = dy / model.getViewZoom();

                model.moveView(-(int) dx, (int) dy);
            }

            oldMouseX = mouseX;
            oldMouseY = mouseY;
        }
        else model.getInGameMenu().mousePosEvent(window);
    }

    /**
     * Handle a mouse button event.
     * @param window The window the event happened on.
     * @param button The mouse button the event was on.
     * @param action The action that triggered the event.
     * @param mods The modifications of the event.
     */
    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_1) {
            if (action == GLFW.GLFW_PRESS)
                mouseKeyPressed = true;
            else if (action == GLFW.GLFW_RELEASE)
                mouseKeyPressed = false;
        }
        if(model.getGameMenuShown()){
            int id = model.getInGameMenu().onClick(window, button, action, mods);
            switch (id){
                case 1://main menu
                    stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
                    break;
                case 2://save
                    //have to use old dialog because we are not in the FX thread

                    String name = JOptionPane.showInputDialog(null, "What's the name of the save file?", "");
                    String prepath = model.getMainModel().getGameFileToLoad();
                    if(name == null) break;

                    if(!prepath.startsWith("saves/")) prepath = "saves/" + prepath;
                    prepath = prepath.substring(0, prepath.lastIndexOf("/")) + "/";
                    if(!GameMapLoader.saveSave(model, prepath + name + ".savd"))
                        JOptionPane.showMessageDialog(null, "Something whent wrong, the file is not saved.", "Warning", JOptionPane.INFORMATION_MESSAGE);

                    break;
                case 3://back
                    model.setGameMenuShown(false);
                    break;
                case 4://quit
                    stateHandler.changeState(StateHandler.STATE_QUIT);
                    break;
            }
        }
    }

    /**
     * Handle a scroll event.
     * @param window The window the event happened on.
     * @param xOffset The x change of the scroll wheel.
     * @param yOffset The y change of the scroll wheel.
     */
    public void scrollEvent(long window, double xOffset, double yOffset) {
        if(!model.getGameMenuShown()) model.setViewZoom(model.getViewZoom() * (float)(yOffset * 0.075f + 1.00f));
    }

    /**
     * Handle a screen resize event.
     */
    @Override
    public void screenResizeEvent() {
        if(model.getGameMenuShown()) model.getInGameMenu().screenResizeEvent();
    }
}