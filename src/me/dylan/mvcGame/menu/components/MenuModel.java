package me.dylan.mvcGame.menu.components;

import java.util.ArrayList;

public class MenuModel {
    private ArrayList<GuiElement> guiElements = new ArrayList<>();
    private int xAlign = -1; //- left, 0 mid, + right
    private int yAlign = -1; //- top, 0 mid, + bottom
    private int totalWidth = 0;
    private int totalHeight = 0;
    private int xMargin = 0;
    private int yMargin = 0;




    public class GuiElement{
        public int x, y, width, height, id;
    }

    public class GuiButton extends GuiElement{
        public int texture, textureKlick;
        public String text;
    }

    public class GuiLabel extends GuiElement{
        public String text;
    }
}
