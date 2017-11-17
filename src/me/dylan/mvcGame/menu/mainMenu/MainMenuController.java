package me.dylan.mvcGame.menu.mainMenu;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer;
import me.dylan.mvcGame.main.MainViewer;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

public class MainMenuController extends State {

    private int vbo_id;
    private int image_id;

    public MainMenuController(MainViewer mainViewer, StateHandler stateHandler) {
        super(mainViewer, stateHandler);
    }

    @Override
    public void init(int previousState) {
        System.out.println("MainMenuController: " + previousState);

        float[] vertexes = new float[VBODrawer.calcArraySizeForSquares(VBODrawer.COORDS_COLOR_TYPE, 1)];
        VBODrawer.draw2DSquare(vertexes, 0, VBODrawer.COORDS_COLOR_TYPE, -0.5f, -0.5f, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1);
        vbo_id = VBODrawer.createBufferId();
        VBODrawer.writeBufToMem(vbo_id, vertexes);

        image_id = Texture.createImageId2("./img/ASCII-normal.png");
    }

    @Override
    public void update() {

    }

    @Override
    public void render(MainViewer mainViewer) {
        //Texture.bindTextureId(image_id);
        /*GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor4f(1,1,1, 0);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(-0.5f, 0.5f);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(0.5f, 0.5f);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(0.5f, -0.5f);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(-0.5f, -0.5f);
        GL11.glEnd();*/
        VBODrawer.drawVBO(vbo_id, 0, VBODrawer.COORDS_COLOR_TYPE, VBODrawer.calcDrawAmountForSquares(1));
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
