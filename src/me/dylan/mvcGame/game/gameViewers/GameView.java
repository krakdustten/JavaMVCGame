package me.dylan.mvcGame.game.gameViewers;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameViewers.NormalTilesView;

//TODO javadoc
public class GameView {
    private NormalTilesView normalTilesView;
    private GameModel gameModel;

    public GameView(GameModel gameModel){
        this.gameModel = gameModel;
        normalTilesView = new NormalTilesView(gameModel);
    }


    public void update() {
        normalTilesView.update();
    }

    public void render() {
        normalTilesView.render();
    }
}
