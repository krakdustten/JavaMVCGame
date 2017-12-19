package me.dylan.mvcGame.worldEditor;

import me.dylan.mvcGame.game.GameMapLoader;
import me.dylan.mvcGame.game.gameObjects.MapModel;
import me.dylan.mvcGame.game.gameObjects.Tiles;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;
import org.lwjgl.glfw.GLFW;

import java.io.File;

public class WorldEditorController extends State{
    private WorldEditorModel model;
    private WorldEditorView view;

    private boolean keyPressed[] = new boolean[6];//UP, DOWN, LEFT, RIGHT, ZOOM IN, ZOOM OUT
    private long mouseKeyPressedFromTime = 0;
    private double oldMouseX, oldMouseY;

    public WorldEditorController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    //TODO Add options to change tile
    //TODO Add secondairy button press
    //TODO Add world editor menu
    //TODO Add save option /\

    @Override
    public void init(int previousState) {
        MapModel map = null;
        if(mainModel.getGameFileToLoad().endsWith(".mapd")){
            File file = new File(mainModel.getGameFileToLoad());

            if(file.exists()){
                map = GameMapLoader.loadMap(mainModel, mainModel.getGameFileToLoad());
            }else{
                map = new MapModel(mainModel, 1, 1, new int[]{256*256*256 - 1}, new byte[]{Tiles.WALL_ID});
            }
        }
        if(map == null) stateHandler.changeState(StateHandler.STATE_MENU_MAIN);
        model = new WorldEditorModel(map);

        GameMapLoader.saveMap(model, mainModel.getGameFileToLoad());

        view = new WorldEditorView(model);
    }

    @Override
    public void update() {
        if (keyPressed[0]) model.moveView(0, -25f / model.getViewZoom());
        if (keyPressed[1]) model.moveView(0, 25f / model.getViewZoom());
        if (keyPressed[2]) model.moveView(25f / model.getViewZoom(), 0);
        if (keyPressed[3]) model.moveView(-25f / model.getViewZoom(), 0);
        if (keyPressed[4]) model.setViewZoom(model.getViewZoom() * 1.03f);
        if (keyPressed[5]) model.setViewZoom(model.getViewZoom() * 0.97f);

        view.update();
    }

    @Override
    public void render() {
        view.render();
    }

    @Override
    public void deInit() {

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
                if(action == GLFW.GLFW_PRESS) model.setShowInEditorMenu(!model.getShowInEditorMenu());
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

        if (mouseKeyPressedFromTime > 0 && mouseKeyPressedFromTime + 200 < System.currentTimeMillis()) {
            double dx = oldMouseX - mouseX;
            double dy = oldMouseY - mouseY;

            dx = dx / model.getViewZoom();
            dy = dy / model.getViewZoom();

            model.moveView(-(int) dx, (int) dy);
        }

        oldMouseX = mouseX;
        oldMouseY = mouseY;
    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_1) {
            if (action == GLFW.GLFW_PRESS && mouseKeyPressedFromTime == 0)
                mouseKeyPressedFromTime = System.currentTimeMillis();
            else if (action == GLFW.GLFW_RELEASE){
                if(mouseKeyPressedFromTime + 200 > System.currentTimeMillis()){
                    smallMousePressB1();
                }
                mouseKeyPressedFromTime = 0;
            }

        }
    }

    private void smallMousePressB1(){
        int tileX = (int) (Math.floor(mainModel.getMouseXWorld() / 64));
        int tileY = (int) (Math.floor(mainModel.getMouseYWorld() / 64));

        boolean changeMap = false;
        int xOffset = 0;
        int yOffset = 0;
        int xSize = model.getWorldXSize();
        int ySize = model.getWorldYSize();

        if(tileX < 0){
            changeMap = true;
            xOffset = -tileX;
            xSize += xOffset;
            tileX +=xOffset;
        }else if(tileX >= model.getWorldXSize()){
            xSize = tileX + 1;
            changeMap = true;
        }

        if(tileY < 0){
            changeMap = true;
            yOffset = -tileY;
            ySize += yOffset;
            tileY +=yOffset;
        }else if(tileY >= model.getWorldYSize()){
            ySize = tileY + 1;
            changeMap = true;
        }

        if(changeMap) {
            model.changeActualMapSize(xSize, ySize, xOffset, yOffset);
            model.moveView(-xOffset * 64.0f, -yOffset * 64.0f);
        }

        model.setUnderGroundColor(256*256*256 - 1, tileX, tileY);
        if(model.getTileID(tileX, tileY) == Tiles.WALL_ID)
            model.setTileID(Tiles.NO_TILE, tileX, tileY);
        else
            model.setTileID(Tiles.WALL_ID, tileX, tileY);

        model.checkIfMapCanBeSmaller(model);
    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) {
        model.setViewZoom(model.getViewZoom() * (float)(yOffset * 0.075f + 1.00f));
    }

    @Override
    public void screenResizeEvent() {

    }
}
