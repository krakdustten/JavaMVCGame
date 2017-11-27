package me.dylan.mvcGame.game;

import me.dylan.mvcGame.game.gameViewers.NormalTilesView;
import me.dylan.mvcGame.main.MainModel;

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
