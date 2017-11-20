package me.dylan.mvcGame.menu.mainMenu;

import me.dylan.mvcGame.drawers.TextDrawer;
import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer;
import me.dylan.mvcGame.main.MainViewer;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

public class MainMenuController extends State {

    private int vbo_id;
    private int image_id;
    private TextDrawer tDraw;

    public MainMenuController(MainViewer mainViewer, StateHandler stateHandler) {
        super(mainViewer, stateHandler);
    }

    @Override
    public void init(int previousState) {
        System.out.println("MainMenuController: " + previousState);

        String text = "Hello world!";
        tDraw = new TextDrawer("./img/ASCII-normal.png");

        float[] vertexes = new float[VBODrawer.calcArraySizeForSquares(VBODrawer.COORDS_COLOR_TEXTURE_TYPE, 1)];
        VBODrawer.draw2DSquare(vertexes, 0, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, 0f, 0f, 100, 100, 1, 1, 1, 0.2f, 0, 0, 1, 1);
        tDraw.drawText(text, 0, 0, 1, 1, 1 ,1, 100f);
        vbo_id = VBODrawer.createBufferId();
        VBODrawer.writeBufToMem(vbo_id, vertexes);
        tDraw.writeBufToMem();

        image_id = Texture.createImageId("./img/test.jpg");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(MainViewer mainViewer) {
        VBODrawer.drawVBO(mainViewer, vbo_id, image_id, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, VBODrawer.calcDrawAmountForSquares(1));
        tDraw.draw(mainViewer);
    }

    @Override
    public void deInit() {

    }

    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) {

    }

    @Override
    public void mousePosEvent(long window, double xPos, double yPos) {

    }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) {

    }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) {

    }
}
