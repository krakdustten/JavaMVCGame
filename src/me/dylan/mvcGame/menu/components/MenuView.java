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
        model.getMainModel().getCamera().setZoom(3);
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
                MenuModel.GuiButton but = (MenuModel.GuiButton) element;
                float bSize = element.height;
                //pre
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, (float)element.x, (float)element.y, bSize, bSize, but.butR, but.butG, but.butB, but.butA, 0, 0, 0.25f, 0.25f);
                //mid,
                //TODO don't stretch but redraw
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + bSize, (float)element.y, element.width - 2 * bSize, bSize, but.butR, but.butG, but.butB, but.butA, 0.25f, 0, 0.25f, 0.25f);
                //end
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + element.width - 32f, (float)element.y, bSize, bSize, but.butR, but.butG, but.butB, but.butA, 0.5f, 0, 0.25f, 0.25f);
            }
        }
        VBODrawer.writeBufToMem(vbo_id, buffer);

        //text
        TextDrawer textDrawer = model.getMainModel().getTextDrawer();
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiLabel){
                MenuModel.GuiLabel sub = (MenuModel.GuiLabel) element;
                if(sub instanceof MenuModel.GuiButton)
                    textDrawer.drawText(sub.text, element.x + (element.width - textDrawer.getSizeForText(sub.text, element.height * 2/3)) / 2 , element.y + element.height * 1/6, sub.textR, sub.textG, sub.textB, sub.textA, element.height * 2/3);
                else
                    textDrawer.drawText(sub.text, element.x , element.y, sub.textR, sub.textG, sub.textB, sub.textA, element.height);
            }
        }
        textDrawer.writeBufToMem();
    }

    public void draw(){
        VBODrawer.drawVBO(model.getMainModel(), vbo_id, texture_id, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, drawAmount);

        model.getMainModel().getTextDrawer().draw(model.getMainModel());
    }
}
