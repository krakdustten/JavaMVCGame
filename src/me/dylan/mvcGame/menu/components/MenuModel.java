package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.main.MainModel;

import java.util.HashMap;

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

    public MenuModel(MainModel mainModel){
        this(mainModel, 0, 0, 0, 0);
    }

    public MenuModel(MainModel mainModel, int xAlign, int yAlign, int xMargin, int yMargin){
        this.mainModel = mainModel;
        this.xAlign = xAlign;
        this.yAlign = yAlign;
        this.totalWidth = 0;
        this.totalHeight = 0;
        this.xMargin = xMargin;
        this.yMargin = yMargin;
    }

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

    public GuiElement[] getAllGuiElements(){
        return guiElements.values().toArray(new GuiElement[0]);
    }

    public GuiElement getGuiElementAtId(int id){
        return guiElements.get(id);
    }

    public void updateView(){changed = true;}

    public boolean needUpdating(){
        boolean temp = changed;
        changed = false;
        return temp;
    }

    public MainModel getMainModel() { return mainModel; }

    public int getxAlign() { return xAlign; }
    public int getyAlign() { return yAlign; }
    public int getTotalWidth() { return totalWidth; }
    public int getTotalHeight() { return totalHeight; }
    public int getxMargin() { return xMargin; }
    public int getyMargin() { return yMargin; }
    public float getDrawXstart() { return drawXstart; }
    public float getDrawYstart() { return drawYstart; }

    public void setxAlign(int xAlign) { this.xAlign = xAlign; }
    public void setyAlign(int yAlign) { this.yAlign = yAlign; }
    public void setxMargin(int xMargin) { this.xMargin = xMargin; }
    public void setyMargin(int yMargin) { this.yMargin = yMargin; }
    public void setDrawXstart(float drawXstart) { this.drawXstart = drawXstart; }
    public void setDrawYstart(float drawYstart) { this.drawYstart = drawYstart; }


    public static class GuiElement{
        public int x, y, width, height, id;
        public GuiElement(int x, int y, int width, int height, int id){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.id = id;
        }
    }

    public static class GuiLabel extends GuiElement{
        public String text;
        public float textR, textG, textB, textA;
        public GuiLabel(int x, int y, int width, int height, int id, String text){
            this(x, y, width, height, id, text, 1, 1, 1, 1);
        }

        public GuiLabel(int x, int y, int width, int height, int id, String text, float textR, float textG, float textB, float textA){
            super(x, y, width, height, id);
            this.text = text;
            this.textR = textR;
            this.textG = textG;
            this.textB = textB;
            this.textA = textA;
        }
    }

    public static class GuiButton extends GuiLabel{
        public float butR, butG, butB, butA;
        public boolean hover = false;
        public GuiButton(int x, int y, int width, int height, int id, String text){
            this(x, y, width, height, id, text, 1, 1, 1, 1, 1, 1, 1,1);
        }

        public GuiButton(int x, int y, int width, int height, int id, String text, float textR, float textG, float textB, float textA, float butR, float butG, float butB, float butA){
            super(x, y, width, height, id, text, textR, textG, textB, textA);
            this.butR = butR;
            this.butG = butG;
            this.butB = butB;
            this.butA = butA;
        }
    }

}
