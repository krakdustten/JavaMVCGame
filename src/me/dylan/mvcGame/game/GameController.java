package me.dylan.mvcGame.game;

import me.dylan.mvcGame.game.gameObjects.specialTiles.SpecialTile;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

public class GameController extends State {
    private GameModel model;
    private GameView view;

    //TODO world editor

    public GameController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        SpecialTile.registerAllSpecialTiles();

        GameModel model = new GameModel(mainModel, 10, 8);
        for(int i = 0; i < (10 * 8); i++){
            model.setTileID((int)(Math.random() * 24), i % 10, i / 10);
            model.setUnderGroundColor((int)(Math.random() * 255 * 255 * 255), i % 10, i / 10);
        }

        GameMapLoader.saveMap(model, "game1.sg");

        model = GameMapLoader.loadMap(mainModel, "game1.sg");
        view = new GameView(model);
    }

    @Override
    public void update() {
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
