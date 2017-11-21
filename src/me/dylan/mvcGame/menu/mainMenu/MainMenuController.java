package me.dylan.mvcGame.menu.mainMenu;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.menu.components.MenuController;
import me.dylan.mvcGame.menu.components.MenuModel;
import me.dylan.mvcGame.state.State;
import me.dylan.mvcGame.state.StateHandler;

public class MainMenuController extends State {

    private int vbo_id;
    private int image_id;

    private MenuController menuController;

    public MainMenuController(MainModel mainModel, StateHandler stateHandler) {
        super(mainModel, stateHandler);
    }

    @Override
    public void init(int previousState) {
        float[] vertexes = new float[VBODrawer.calcArraySizeForSquares(VBODrawer.COORDS_COLOR_TEXTURE_TYPE, 1)];
        VBODrawer.draw2DSquare(vertexes, 0, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, -50f, -50f, 100, 100, 1, 1, 1, 0.7f, 0, 0, 1, 1);
        mainModel.getTextDrawer().drawText("Hello world!!!", 0, 0, 1, 1, 1 ,1, 20f);
        vbo_id = VBODrawer.createBufferId();
        VBODrawer.writeBufToMem(vbo_id, vertexes);
        mainModel.getTextDrawer().writeBufToMem();

        image_id = Texture.createImageId("./img/test.jpg");

        menuController = new MenuController(mainModel);

        menuController.addGuiElement(new MenuModel.GuiButton(-50, -50, 100, 28, 1, "Wauw", 1, 1, 1, 1, 0, 1, 0, 1));
    }

    @Override
    public void update() {
        menuController.update();
    }

    @Override
    public void render() {
        //VBODrawer.drawVBO(mainModel, vbo_id, image_id, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, VBODrawer.calcDrawAmountForSquares(1));
        mainModel.getTextDrawer().draw(mainModel);
        menuController.render();
    }

    @Override
    public void deInit() { }

    @Override
    public void keyboardEvent(long window, int key, int scancode, int action, int mods) { }

    @Override
    public void mousePosEvent(long window) { }

    @Override
    public void mouseButtonEvent(long window, int button, int action, int mods) { }

    @Override
    public void scrollEvent(long window, double xOffset, double yOffset) { }
}
