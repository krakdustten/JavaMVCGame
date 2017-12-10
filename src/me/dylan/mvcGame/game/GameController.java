package me.dylan.mvcGame.game;

import me.dylan.mvcGame.game.controllers.RobotPlayerController;
import me.dylan.mvcGame.game.gameObjects.specialTiles.SpecialTile;
import me.dylan.mvcGame.game.gameViewers.CodeIDEViewer;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;
import org.lwjgl.glfw.GLFW;

public class GameController extends State {
    private GameModel model;
    private GameView view;
    private CodeIDEViewer codeIDEViewer;
    private RobotPlayerController playerController;


    private boolean keyPressed[] = new boolean[6];//UP, DOWN, LEFT, RIGHT, ZOOM IN, ZOOM OUT
    private boolean mouseKeyPressed = false;
    private double oldMouseX, oldMouseY;

    public GameController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        SpecialTile.registerAllSpecialTiles();

        GameModel model = new GameModel(mainModel, 40, 29);
        for(int i = 0; i < (40 * 29); i++){
            model.setTileID((byte)(Math.random() * 1.5 + 1), i % 40, i / 40);
            model.setUnderGroundColor(/*(int)(Math.random() * 256 * 256 * 256)*/ 256 * 256 * 256 - 1, i % 40, i / 40);
        }

        GameMapLoader.saveMap(model, "random.sg");

        this.model = GameMapLoader.loadMap(mainModel, "game1.sg");
        view = new GameView(this.model);

        this.model.setViewZoom(0.5f);
        this.model.setViewX(this.model.getWorldXSize() * 64 / 2);
        this.model.setViewY(this.model.getWorldYSize() * 64 / 2);

        //TODO make code runner --> for handling world code
        //TODO world editor --> adaptive worlds with world code

        codeIDEViewer = new CodeIDEViewer(this.model);
        playerController = new RobotPlayerController(this.model);
    }

    @Override
    public void update() {
        if(model.isGameRunning())
            updateGame();

        if(model.getShouldGameReset()){
            playerController = new RobotPlayerController(this.model);
            model.setShouldGameReset(false);
        }

        if(keyPressed[0])model.moveView(0, -25f/model.getViewZoom());
        if(keyPressed[1])model.moveView(0, 25f/model.getViewZoom());
        if(keyPressed[2])model.moveView(25f/model.getViewZoom(), 0);
        if(keyPressed[3])model.moveView(-25f/model.getViewZoom(), 0);
        if(keyPressed[4])model.setViewZoom(model.getViewZoom() * 1.03f);
        if(keyPressed[5])model.setViewZoom(model.getViewZoom() * 0.97f);
        view.update();
        playerController.update();

    }

    private void updateGame(){
        model.updateGameTime();
        playerController.updateGame();
    }

    @Override
    public void render() {
        view.render();
        playerController.render();
    }

    @Override
    public void deInit() {
        codeIDEViewer.distroy();
        codeIDEViewer = null;
    }

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
                if(action == GLFW.GLFW_PRESS) stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
        }
    }

    private void handleKey(int arrayKey, int action){
        if(action == GLFW.GLFW_PRESS) keyPressed[arrayKey] = true;
        if(action == GLFW.GLFW_RELEASE) keyPressed[arrayKey]= false;
    }

    @Override
    public void mousePosEvent(long window) {
        double mouseX = mainModel.getMouseX();
        double mouseY = mainModel.getMouseY();

        if(mouseKeyPressed) {
            double dx = oldMouseX - mouseX;
            double dy = oldMouseY - mouseY;

            dx = dx/model.getViewZoom();
            dy = dy/model.getViewZoom();

            model.moveView(-(int)dx, (int)dy);
        }

        oldMouseX = mouseX;
        oldMouseY = mouseY;
    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_1) {
            if (action == GLFW.GLFW_PRESS)
                mouseKeyPressed = true;
            else if (action == GLFW.GLFW_RELEASE)
                mouseKeyPressed = false;
        }
    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) {
        model.setViewZoom(model.getViewZoom() * (float)(yOffset * 0.075f + 1.00f));
    }

    @Override
    public void screenResizeEvent() {

    }
}