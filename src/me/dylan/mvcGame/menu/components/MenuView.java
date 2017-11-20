package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.drawers.TextDrawer;
import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer;

public class MenuView {
    private MenuModel model;
    private int texture_id;
    private int vbo_id;
    private int drawAmount = 0;

    public MenuView(MenuModel model, String menuImg){
        this.model = model;
        texture_id = Texture.createImageId(menuImg);
        vbo_id = VBODrawer.createBufferId();
        update();
    }

    public void update(){
        if(!model.needUpdating())return;

        //calc the total float buffer and draw amount
        drawAmount = 0;
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiButton)
                drawAmount += 6 * 3;
        }
        float[] buffer = new float[drawAmount * 9];
        int offset = 0;
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiButton){
                //pre
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, (float)element.x, (float)element.y, 16f, 16f, 1, 1, 1, 1, 0, 0, 0.25f, 0.25f);
                //mid
                //TODO don't stretch but redraw
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + 16f, (float)element.y, element.width - 32f, 16f, 1, 1, 1, 1, 0.25f, 0, 0.25f, 0.25f);
                //end
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + element.width - 16f, (float)element.y, 16f, 16f, 1, 1, 1, 1, 0.5f, 0, 0.25f, 0.25f);
            }
        }
        VBODrawer.writeBufToMem(vbo_id, buffer);

        //text
        TextDrawer textDrawer = model.getMainModel().getTextDrawer();
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiLabel){
                textDrawer.drawText(((MenuModel.GuiLabel) element).text, element.x, element.y, 1,1,1,1,16);
            }
        }
        textDrawer.writeBufToMem();
    }

    public void draw(){
        VBODrawer.drawVBO(model.getMainModel(), vbo_id, texture_id, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, drawAmount);

        model.getMainModel().getTextDrawer().draw(model.getMainModel());
    }
}
