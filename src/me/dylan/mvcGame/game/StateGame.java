package me.dylan.mvcGame.game;

import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

import java.io.File;

public class StateGame extends State {

    //TODO world editor
    //TODO maybe go to 3D

    public StateGame(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        GameModel model = new GameModel();
        model.setWorldXSize(10);
        model.setWorldYSize(8);
        int[] tile = new int[10 * 8];
        int[] color = new int[10 * 8];
        for(int i = 0; i < (10 * 8); i++){
            tile[i] = (int)(Math.random() * 24);
            color[i] = (int)(Math.random() * 255);
        }
        model.setTileID(tile);
        model.setUnderGroundColor(color);

        GameMapLoader.saveMap(model, "game1.sg");
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void deInit() {

    }

    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {

    }

    @Override
    public void mousePosEvent(long window) {

    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {

    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) {

    }

    @Override
    public void screenResizeEvent() {

    }
}
