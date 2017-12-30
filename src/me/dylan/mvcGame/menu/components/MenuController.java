package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.main.MainModel;
import org.lwjgl.glfw.GLFW;

/**
 * The controller of a menu.
 *
 * @author Dylan Gybels
 */
public class MenuController {
    private MenuModel model;
    private MenuView view;

    /**
     * Create a new menu.
     *
     * @param mainModel The main model
     * @param menuImg The path to the file that holds the images for the menu.
     */
    public MenuController(MainModel mainModel, String menuImg){
        model = new MenuModel(mainModel);
        view = new MenuView(model, menuImg);
    }

    /**
     * Add a new gui element to the menu.
     * @param element The element to add.
     * @return True, if the addition was a success.
     */
    public boolean addGuiElement(MenuModel.GuiElement element){
        return model.addGuiElement(element);
    }

    /**
     * Update this menu.
     */
    public void update() {
        view.update();
    }

    /**
     * Render this menu.
     */
    public void render() {
        view.draw();
    }

    /**
     * Send an on click event to this menu.
     *
     * @param window The window the event happened on.
     * @param button The button the event happened on.
     * @param action The action that happened on the button.
     * @param mods The modifications on the event.
     * @return -1 if no button was clicked and the ID of the that was clicked if one was clicked.
     */
    public int onClick(long window, int button, int action, int mods){
        if(button != GLFW.GLFW_MOUSE_BUTTON_1 || action != GLFW.GLFW_PRESS)return -1;
        int mouseX = (int)model.getMainModel().getMouseXWorld();
        int mouseY = (int)model.getMainModel().getMouseYWorld();
        float z = model.getMainModel().getCamera2D().getZoom();
        for(MenuModel.GuiElement element : model.getAllGuiElements()) {
            if (element instanceof MenuModel.GuiButton) {
                MenuModel.GuiButton but = (MenuModel.GuiButton) element;
                if(mouseX > (but.x / z + model.getDrawXstart()) &&
                        mouseX < ((but.x + but.width) / z + model.getDrawXstart()) &&
                        mouseY > (but.y  / z  + model.getDrawYstart()) &&
                        mouseY < ((but.y + but.height)  / z + model.getDrawYstart()))
                    return but.id;
            }
        }

        return -1;
    }

    /**
     * Send a mouse position event to this menu.
     *
     * @param window The window this event happened on.
     */
    public void mousePosEvent(long window) {
        int mouseX = (int)model.getMainModel().getMouseXWorld();
        int mouseY = (int)model.getMainModel().getMouseYWorld();
        float z = model.getMainModel().getCamera2D().getZoom();
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof  MenuModel.GuiButton){
                MenuModel.GuiButton but = (MenuModel.GuiButton) element;
                boolean onBut = mouseX > (but.x / z + model.getDrawXstart()) && mouseX < ((but.x + but.width) / z + model.getDrawXstart()) && mouseY > (but.y / z + model.getDrawYstart()) && mouseY < ((but.y + but.height) / z + model.getDrawYstart());
                if(onBut != but.hover){
                    but.hover = onBut;
                    model.updateView();
                }
            }
        }
    }

    /**
     * Send a screen resize event to this menu.
     */
    public void screenResizeEvent(){
        model.updateView();
    }

    /**
     * Set the alignments and margins of this menu.<br>
     * <br>
     * If the margin is (-) negative:<br>
     * The menu will be left/top aligned.<br>
     * The Margin will be applied on the left/top side of the menu.<br>
     * <br>
     * If the margin is (0) zero:<br>
     * The menu will be aligned to the middle.<br>
     * The Margin wont be used.<br>
     * <br>
     * If the margin is (+) positive:<br>
     * The menu will be right/bottom aligned.<br>
     * The Margin will be applied on the right/bottom side of the menu.<br>
     *
     * @param xAlign The left/right alignment.
     * @param yAlign The top/bottom alignment.
     * @param xMargin The left/right margin.
     * @param yMargin The top/bottom margin.
     */
    public void setAlignMargin(int xAlign, int yAlign, int xMargin, int yMargin){
        setxAlign(xAlign);
        setyAlign(yAlign);
        setxMargin(xMargin);
        setyMargin(yMargin);
    }

    /**
     * Set the left/right alignment.<br>
     * <br>
     * If the margin is (-) negative:<br>
     * The menu will be left aligned.<br>
     * The Margin will be applied on the left side of the menu.<br>
     * <br>
     * If the margin is (0) zero:<br>
     * The menu will be aligned to the middle.<br>
     * The Margin wont be used.<br>
     * <br>
     * If the margin is (+) positive:<br>
     * The menu will be right aligned.<br>
     * The Margin will be applied on the right side of the menu.<br>
     *
     * @param xAlign The left/right alignment.
     */
    public void setxAlign(int xAlign) { model.setxAlign(xAlign); }

    /**
     * Set the top/bottom alignment.<br>
     * <br>
     * If the margin is (-) negative:<br>
     * The menu will be top aligned.<br>
     * The Margin will be applied on the top side of the menu.<br>
     * <br>
     * If the margin is (0) zero:<br>
     * The menu will be aligned to the middle.<br>
     * The Margin wont be used.<br>
     * <br>
     * If the margin is (+) positive:<br>
     * The menu will be bottom aligned.<br>
     * The Margin will be applied on the bottom side of the menu.<br>
     * @param yAlign The top/bottom alignment.
     */
    public void setyAlign(int yAlign) { model.setyAlign(yAlign); }

    /**
     * Set the left/right margin.
     * @param xMargin The left/right margin.
     */
    public void setxMargin(int xMargin) { model.setxMargin(xMargin); }

    /**
     * Set the top/bottom margin.
     * @param yMargin The top/bottom margin.
     */
    public void setyMargin(int yMargin) { model.setyMargin(yMargin); }

    /**
     * Set the color of the background.
     * @param backR The red part of the color.
     * @param backG The green part of the color.
     * @param backB The blue part of the color.
     * @param backA The opacity factor.
     */
    public void setBackgroundColor(float backR, float backG, float backB, float backA){
        setBackR(backR);
        setBackG(backG);
        setBackB(backB);
        setBackA(backA);
    }

    /**
     * Set the red part of the background color.
     * @param backR The red part of the background color.
     */
    public void setBackR(float backR) { model.setBackR(backR); }

    /**
     * Set the green part of the background color.
     * @param backG The green part of the background color.
     */
    public void setBackG(float backG) { model.setBackG(backG); }

    /**
     * Set the blue part of the background color.
     * @param backB The blue part of the background color.
     */
    public void setBackB(float backB) { model.setBackB(backB); }

    /**
     * Set the opacity factor of the background.
     * @param backA The opacity factor of the background.
     */
    public void setBackA(float backA) { model.setBackA(backA); }

    /**
     * Set that the view has to updated.
     */
    public void updateView(){model.updateView();}

    /**
     * Clear all vars.
     */
    public void delete(){
        view.delete();
        model = null;
        view = null;
    }

}
