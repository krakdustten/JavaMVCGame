package me.dylan.mvcGame.game.gameViewers;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.game.GameModel;

public class RobotPlayerView {
    private int vbo;
    private int texture;
    private int textureTime = 0;

    private GameModel model;

    float test = 0;

    public RobotPlayerView(GameModel model){
        this.model = model;

        vbo = VBODrawer2D.createBufferId();
        texture = Texture.createImageId("./img/Robot.png");

        update();
    }

    public void update(){
        textureTime++;
        if(test >= 2 * Math.PI) test -= 2 * Math.PI;
        if(textureTime >= 800) textureTime = 0;
        if(!model.getPlayer().isChanged()){
            return;
        }

        float xStart = model.getPlayer().getX();
        float yStart = model.getPlayer().getY();

        float[] vertexes = new float[VBODrawer2D.calcArraySizeForSquares(VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, 3)];
        int offset = VBODrawer2D.draw2DSquare(vertexes, 0, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64, 64, 64, model.getPlayer().getRotation(),
                1, 1, 1, 1, 0, 0, 0.125f, 0.125f);


        VBODrawer2D.writeBufToMem(vbo, vertexes);
    }

    public void render() {
        VBODrawer2D.drawVBO(model.getMainModel(), vbo, texture, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, VBODrawer2D.calcDrawAmountForSquares(3));
    }
}
