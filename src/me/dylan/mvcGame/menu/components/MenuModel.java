package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.main.MainModel;

import java.util.HashMap;

/**
 * The model of a menu.
 *
 * @author Dylan Gybels
 */
public class MenuModel {
    private MainModel mainModel;

    private HashMap<Integer, GuiElement> guiElements = new HashMap<>();
    private int xAlign = -1; //- left, 0 mid, + right
    private int yAlign = -1; //- top, 0 mid, + bottom
    private int totalWidth = 0;
    private int totalHeight = 0;
    private int xMargin = 0;
    private int yMargin = 0;
    private boolean changed = false;
    private float drawXstart, drawYstart;
    private float backR, backG, backB, backA;

    /**
     * Create a new menu model.
     * @param mainModel The main model.
     */
    public MenuModel(MainModel mainModel){
        this(mainModel, 0, 0, 0, 0, 1, 1, 1, 1);
    }

    /**
     * Create a new menu model.<br>
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
     * @param mainModel The main model
     * @param xAlign The left/right alignment.
     * @param yAlign The top/bottom alignment.
     * @param xMargin The left/right margin.
     * @param yMargin The top/bottom margin.
     * @param backR The red part of the background color.
     * @param backG The green part of the background color.
     * @param backB The blue part of the background color.
     * @param backA The opacity factor of the background.
     */
    public MenuModel(MainModel mainModel, int xAlign, int yAlign, int xMargin, int yMargin, float backR, float backG, float backB, float backA){
        this.mainModel = mainModel;
        this.xAlign = xAlign;
        this.yAlign = yAlign;
        this.totalWidth = 0;
        this.totalHeight = 0;
        this.xMargin = xMargin;
        this.yMargin = yMargin;
        this.backR = backR;
        this.backG = backG;
        this.backB = backB;
        this.backA = backA;
    }

    /**
     * Add an new element to the menu.
     * @param element The element to add.
     * @return True, if the addition was a success.
     */
    public boolean addGuiElement(GuiElement element){
        if(guiElements.containsKey(element.id))
            return false;
        guiElements.put(element.id, element);

        int width = element.x + element.width;
        int height = element.y + element.height;
        if(width > totalWidth)totalWidth = width;
        if(height > totalHeight)totalHeight = height;
        changed = true;
        return true;
    }

    /**
     * Get all of the gui elements.
     * @return The gui elements.
     */
    public GuiElement[] getAllGuiElements(){
        return guiElements.values().toArray(new GuiElement[0]);
    }

    /**
     * Get a specific gui element with the id.
     * @param id The id of the wanted element.
     * @return The gui element.
     */
    public GuiElement getGuiElementAtId(int id){
        return guiElements.get(id);
    }

    /**
     * Set that we need to update the view.
     */
    public void updateView(){changed = true;}

    /**
     * Check if the view needed updating.
     * @return True, if we need to update the view.
     */
    public boolean needUpdating(){
        boolean temp = changed;
        changed = false;
        return temp;
    }

    /**
     * Get the main model.
     * @return The main model.
     */
    public MainModel getMainModel() { return mainModel; }

    /**
     * Get the left/right alignment.<br>
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
     * @return The left/right alignment.
     */
    public int getxAlign() { return xAlign; }

    /**
     * Get the top/bottom alignment.<br>
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
     *
     * @return The top/bottom alignment.
     */
    public int getyAlign() { return yAlign; }

    /**
     * Get the total width of the menu.
     * @return The total width of the menu.
     */
    public int getTotalWidth() { return totalWidth; }

    /**
     * Get the total height of the menu.
     * @return The total height of the menu.
     */
    public int getTotalHeight() { return totalHeight; }

    /**
     * Get the left/right margin.
     * @return The left/right margin.
     */
    public int getxMargin() { return xMargin; }

    /**
     * Get the top/bottom margin.
     * @return The top/bottom margin.
     */
    public int getyMargin() { return yMargin; }

    /**
     * Get the x position that we last draw to.
     * @return The x position that we last draw to.
     */
    public float getDrawXstart() { return drawXstart; }

    /**
     * Get the y position that we last draw to.
     * @return The y position that we last draw to.
     */
    public float getDrawYstart() { return drawYstart; }

    /**
     * Get the red part of the background color.
     * @return The red part of the background color.
     */
    public float getBackR() { return backR; }

    /**
     * Get the green part of the background color.
     * @return The green part of the background color.
     */
    public float getBackG() { return backG; }

    /**
     * Get the blue part of the background color.
     * @return The blue part of the background color.
     */
    public float getBackB() { return backB; }

    /**
     * Get the opacity factor of the background.
     * @return The opacity factor of the background.
     */
    public float getBackA() { return backA; }

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
    public void setxAlign(int xAlign) { this.xAlign = xAlign; }

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
    public void setyAlign(int yAlign) { this.yAlign = yAlign; }

    /**
     * Set the left/right margin.
     * @param xMargin The left/right margin.
     */
    public void setxMargin(int xMargin) { this.xMargin = xMargin; }

    /**
     * Set the top/bottom margin.
     * @param yMargin The top/bottom margin.
     */
    public void setyMargin(int yMargin) { this.yMargin = yMargin; }

    /**
     * Set the x position that we last drawn to.
     * @param drawXstart The new x position that we last drawn to.
     */
    public void setDrawXstart(float drawXstart) { this.drawXstart = drawXstart; }

    /**
     * Set the y position that we last drawn to.
     * @param drawYstart The new y position that we last drawn to.
     */
    public void setDrawYstart(float drawYstart) { this.drawYstart = drawYstart; }

    /**
     * Set the red part of the background color.
     * @param backR The red part of the background color.
     */
    public void setBackR(float backR) { this.backR = backR; }

    /**
     * Set the green part of the background color.
     * @param backG The green part of the background color.
     */
    public void setBackG(float backG) { this.backG = backG; }

    /**
     * Set the blue part of the background color.
     * @param backB The blue part of the background color.
     */
    public void setBackB(float backB) { this.backB = backB; }

    /**
     * Set the opacity factor of the background.
     * @param backA The opacity factor of the background.
     */
    public void setBackA(float backA) { this.backA = backA; }

    /**
     * A gui element. The base class.
     */
    public static abstract class GuiElement{
        public int x, y, width, height, id;

        /**
         * Create a new gui element.
         * @param x The x position.
         * @param y The y position.
         * @param width The width.
         * @param height The height.
         * @param id The id.
         */
        public GuiElement(int x, int y, int width, int height, int id){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.id = id;
        }
    }

    /**
     * A label.
     */
    public static class GuiLabel extends GuiElement{
        public String text;
        public float textR, textG, textB, textA;

        /**
         * Create a new label.
         * @param x The x position.
         * @param y The y position.
         * @param width The width.
         * @param height The height.
         * @param id The id.
         * @param text The text to show.
         */
        public GuiLabel(int x, int y, int width, int height, int id, String text){
            this(x, y, width, height, id, text, 1, 1, 1, 1);
        }

        /**
         * Create a new label with a given color for the text.
         * @param x The x position.
         * @param y The y position.
         * @param width The width.
         * @param height The height.
         * @param id The id.
         * @param text The text to show.
         * @param textR The red part of the text color.
         * @param textG The green part of the text color.
         * @param textB The blue part of the text color.
         * @param textA The opacity factor of the text.
         */
        public GuiLabel(int x, int y, int width, int height, int id, String text, float textR, float textG, float textB, float textA){
            super(x, y, width, height, id);
            this.text = text;
            this.textR = textR;
            this.textG = textG;
            this.textB = textB;
            this.textA = textA;
        }
    }

    /**
     * A button
     */
    public static class GuiButton extends GuiLabel{
        public float butR, butG, butB, butA;
        public boolean hover = false;

        /**
         * Create a new button.
         * @param x The x position.
         * @param y The y position.
         * @param width The width.
         * @param height The height.
         * @param id The id.
         * @param text The text to show.
         */
        public GuiButton(int x, int y, int width, int height, int id, String text){
            this(x, y, width, height, id, text, 1, 1, 1, 1, 1, 1, 1,1);
        }

        /**
         * Create a new button with a given color for the text and for the button.
         * @param x The x position.
         * @param y The y position.
         * @param width The width.
         * @param height The height.
         * @param id The id.
         * @param text The text to show.
         * @param textR The red part of the text color.
         * @param textG The green part of the text color.
         * @param textB The blue part of the text color.
         * @param textA The opacity factor of the text.
         * @param butR The red part of the button color.
         * @param butG The blue part of the button color.
         * @param butB The green part of the button color.
         * @param butA The opacity factor of the button.
         */
        public GuiButton(int x, int y, int width, int height, int id, String text, float textR, float textG, float textB, float textA, float butR, float butG, float butB, float butA){
            super(x, y, width, height, id, text, textR, textG, textB, textA);
            this.butR = butR;
            this.butG = butG;
            this.butB = butB;
            this.butA = butA;
        }
    }

}
