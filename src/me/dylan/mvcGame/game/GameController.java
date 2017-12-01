package me.dylan.mvcGame.game;

import me.dylan.mvcGame.game.gameObjects.Tiles;
import me.dylan.mvcGame.game.gameObjects.specialTiles.SpecialTile;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;
import org.lwjgl.glfw.GLFW;

public class GameController extends State {
    private GameModel model;
    private GameView view;
    private CodeIDEController codeIDEController;

    private boolean keyPressed[] = new boolean[6];//UP, DOWN, LEFT, RIGHT, ZOOM IN, ZOOM OUT



    public GameController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        SpecialTile.registerAllSpecialTiles();

        /*GameModel model = new GameModel(mainModel, 40, 29);
        for(int i = 0; i < (40 * 29); i++){
            if((i / 40) % 4 == 0 || i % 40 == 0 || i % 40 == model.getWorldXSize() - 1) model.setTileID(Tiles.WALL_ID, i % 40, i / 40);
            else model.setTileID(Tiles.FLOOR_ID, i % 40, i / 40);

            model.setUnderGroundColor(/*(int)(Math.random() * 256 * 256 * 256)*//* 256 * 256 * 256 - 1, i % 40, i / 40);
        }


        GameMapLoader.saveMap(model, "game1.sg");*/

        this.model = GameMapLoader.loadMap(mainModel, "game1.sg");
        view = new GameView(model);

        model.setViewZoom(0.5f);
        model.setViewX(model.getWorldXSize() * 64 / 2);
        model.setViewY(model.getWorldYSize() * 64 / 2);

        //TODO make code runner --> method handling from code runner
        //TODO world editor

        codeIDEController = new CodeIDEController(model);
    }

    @Override
    public void update() {
        if(keyPressed[0])model.moveView(0, -10f);
        if(keyPressed[1])model.moveView(0, 10f);
        if(keyPressed[2])model.moveView(10f, 0);
        if(keyPressed[3])model.moveView(-10f, 0);
        if(keyPressed[4])model.setViewZoom(model.getViewZoom() * 1.02f);
        if(keyPressed[5])model.setViewZoom(model.getViewZoom() * 0.98f);

        view.update();
    }

    @Override
    public void render() {
        view.render();
    }

    @Override
    public void deInit() {
        codeIDEController.distroy();
        codeIDEController = null;
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

    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {

    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) {
        model.setViewZoom(model.getViewZoom() * (float)(yOffset * 0.075f + 1.00f));
    }

    @Override
    public void screenResizeEvent() {

    }
}
