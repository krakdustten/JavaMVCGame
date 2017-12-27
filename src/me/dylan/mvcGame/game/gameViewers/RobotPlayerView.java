package me.dylan.mvcGame.game.gameViewers;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;

/**
 * The viewer that draws the robot player.
 *
 * @author Dylan Gybels
 */
public class RobotPlayerView {
    private int vbo;
    private int texture;

    private GameModel model;

    /**
     * Create a new robot viewer.
     *
     * @param model The game model.
     */
    public RobotPlayerView(GameModel model){
        this.model = model;

        vbo = VBODrawer2D.createBufferId();
        texture = Texture.createImageId("img/Robot.png");

        update();
    }

    /**
     * Update the buffers on the graphics card if needed.
     */
    public void update(){
        RobotPlayerModel playerModel = (RobotPlayerModel) model.getRobot();
        if(!playerModel.getChanged()){
            return;
        }

        float xStart = playerModel.getX();
        float yStart = playerModel.getY();
        int motL = (int) (playerModel.getMoterLPos() * 8);
        int motR = (int) (playerModel.getMoterRPos() * 8);

        float[] vertexes = new float[VBODrawer2D.calcArraySizeForSquares(VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, 3)];

        int offset = VBODrawer2D.draw2DSquareRot(vertexes, 0, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64, 64, 64, playerModel.getRotation(),
                1, 1, 1, 1, 0, 0, 0.125f, 0.125f);
        offset = VBODrawer2D.draw2DSquareRotCenter(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64, 64, 8, playerModel.getRotation(), xStart * 64 + 32, yStart * 64 + 32,
                1, 1, 1, 1, 0, 0.125f + motR * 0.015625f, 0.125f, 0.015625f);
        VBODrawer2D.draw2DSquareRotCenter(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64 + 56, 64, 8, playerModel.getRotation(), xStart * 64 + 32, yStart * 64 + 32,
                1, 1, 1, 1, 0, 0.125f + motL * 0.015625f, 0.125f, 0.015625f);

        VBODrawer2D.writeBufToMem(vbo, vertexes);
    }

    /**
     * Render the buffers on the graphics card.
     */
    public void render() {
        VBODrawer2D.drawVBO(model.getMainModel(), vbo, texture, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, VBODrawer2D.calcDrawAmountForSquares(3));
    }

    /**
     * Clear all vars and distroy.
     */
    public void distroy() {
        VBODrawer2D.deleteVBO(vbo);
        Texture.deleteImage(texture);
    }
}
