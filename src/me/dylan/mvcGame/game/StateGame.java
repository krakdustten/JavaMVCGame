package me.dylan.mvcGame.game;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.drawers.VBODrawer3D;
import me.dylan.mvcGame.game.tiles.specialTiles.SpecialTile;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

public class StateGame extends State {

    private int vbo;
    private int pic;

    //TODO world editor
    //TODO maybe go to 3D

    public StateGame(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        /*SpecialTile.registerAllSpecialTiles();

        GameModel model = new GameModel(10, 8);
        for(int i = 0; i < (10 * 8); i++){
            model.setTileID((int)(Math.random() * 24), i % 10, i / 10);
            model.setUnderGroundColor((int)(Math.random() * 255), i % 10, i / 10);
        }

        GameMapLoader.saveMap(model, "game1.sg");

        GameModel retmodel = GameMapLoader.loadMap("game1.sg");

        System.out.println(retmodel.getWorldXSize());*/

        vbo = VBODrawer3D.createBufferId();

        //float t = 10;

        float[] data = new float[VBODrawer3D.calcArraySizeForSquares(VBODrawer3D.COORDS_COLOR_TEXTURE_TYPE, 1)];


        VBODrawer3D.draw3DQuad(data,0,VBODrawer3D.COORDS_COLOR_TEXTURE_TYPE,-100,-100,-100,-100, 100, -100, 100, -100, -100, 100, 100, -100, 1,1 ,1, 1, 0,0, 1, 1);

        //front
        //int offset = VBODrawer3D.draw3DQuad(data, 0, VBODrawer3D.COORDS_COLOR_TEXTURE_TYPE, 0, 0, t, 0, t, t, t, 0, t, t, t, t, 1, 1, 1, 1, 0, 0, 1, 1);
        //right
        //offset = VBODrawer3D.draw3DQuad(data, offset, VBODrawer3D.COORDS_COLOR_TEXTURE_TYPE, t, 0, t, t, t, t, t, 0, 0, t, t, 0, 1, 1, 1, 1, 0, 0, 1, 1);

        //VBODrawer3D.writeBufToMem(vbo, data);
        VBODrawer3D.writeBufToMem(vbo, data);

        pic = Texture.createImageId("./img/test.jpg");

        mainModel.getCamera3D().setCameraPosition(0,0, 0);
        mainModel.getCamera3D().setZoom(1);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        //VBODrawer3D.drawVBO(mainModel, vbo, pic, VBODrawer3D.COORDS_COLOR_TEXTURE_TYPE, 1);
        VBODrawer3D.drawVBO(mainModel, vbo, pic, VBODrawer3D.COORDS_COLOR_TEXTURE_TYPE, VBODrawer3D.calcDrawAmountForSquares(1));
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
