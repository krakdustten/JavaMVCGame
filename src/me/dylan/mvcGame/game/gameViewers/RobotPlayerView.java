package me.dylan.mvcGame.game.gameViewers;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.game.GameModel;

public class RobotPlayerView {
    private int vbo;
    private int texture;

    private GameModel model;

    public RobotPlayerView(GameModel model){
        this.model = model;

        vbo = VBODrawer2D.createBufferId();
        texture = Texture.createImageId("img/Robot.png");

        update();
    }

    public void update(){
        if(!model.getPlayer().isChanged()){
            return;
        }

        float xStart = model.getPlayer().getX();
        float yStart = model.getPlayer().getY();
        int motL = (int) (model.getPlayer().getMoterLPos() * 8);
        int motR = (int) (model.getPlayer().getMoterRPos() * 8);

        float[] vertexes = new float[VBODrawer2D.calcArraySizeForSquares(VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, 3)];

        int offset = VBODrawer2D.draw2DSquareRot(vertexes, 0, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64, 64, 64, model.getPlayer().getRotation(),
                1, 1, 1, 1, 0, 0, 0.125f, 0.125f);
        offset = VBODrawer2D.draw2DSquareRotCenter(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64, 64, 8, model.getPlayer().getRotation(), xStart * 64 + 32, yStart * 64 + 32,
                1, 1, 1, 1, 0, 0.125f + motL * 0.015625f, 0.125f, 0.015625f);
        offset = VBODrawer2D.draw2DSquareRotCenter(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64 + 56, 64, 8, model.getPlayer().getRotation(), xStart * 64 + 32, yStart * 64 + 32,
                1, 1, 1, 1, 0, 0.125f + motR * 0.015625f, 0.125f, 0.015625f);

        VBODrawer2D.writeBufToMem(vbo, vertexes);
    }

    public void render() {
        VBODrawer2D.drawVBO(model.getMainModel(), vbo, texture, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, VBODrawer2D.calcDrawAmountForSquares(3));
    }
}
