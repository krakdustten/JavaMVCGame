package me.dylan.mvcGame.worldEditor;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.game.gameObjects.robot.RobotModel;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;

public class RobotEditorView {
    private WorldEditorModel model;

    private int vbo;
    private int texture;

    public RobotEditorView(WorldEditorModel model) {
        this.model = model;

        vbo = VBODrawer2D.createBufferId();
        texture = Texture.createImageId("img/Robot.png");

        update();
    }

    public void update() {
        if(!model.getRobot().getChanged()) return;

        RobotModel robot = model.getRobot();
        float xStart = robot.getX();
        float yStart = robot.getY();
        int motL = (int) (robot.getMoterLPos() * 8);
        int motR = (int) (robot.getMoterRPos() * 8);

        float[] vertexes = new float[VBODrawer2D.calcArraySizeForSquares(VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, 3)];

        int offset = VBODrawer2D.draw2DSquareRot(vertexes, 0, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64, 64, 64, robot.getRotation(),
                1, 1, 1, 1, 0, 0, 0.125f, 0.125f);
        offset = VBODrawer2D.draw2DSquareRotCenter(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64, 64, 8, robot.getRotation(), xStart * 64 + 32, yStart * 64 + 32,
                1, 1, 1, 1, 0, 0.125f + motR * 0.015625f, 0.125f, 0.015625f);
        offset = VBODrawer2D.draw2DSquareRotCenter(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                xStart * 64, yStart * 64 + 56, 64, 8, robot.getRotation(), xStart * 64 + 32, yStart * 64 + 32,
                1, 1, 1, 1, 0, 0.125f + motL * 0.015625f, 0.125f, 0.015625f);

        VBODrawer2D.writeBufToMem(vbo, vertexes);

        model.getRobot().setChange(false);
    }

    public void render() {
        VBODrawer2D.drawVBO(model.getMainModel(), vbo, texture, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, VBODrawer2D.calcDrawAmountForSquares(3));
    }

    public void distroy() {
        VBODrawer2D.deleteVBO(vbo);
        Texture.deleteImage(texture);
    }
}
