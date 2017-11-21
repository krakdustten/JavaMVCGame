package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.drawers.TextDrawer;
import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer;
import me.dylan.mvcGame.main.Camera;

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

        Camera camera = model.getMainModel().getCamera();
        float width = model.getTotalWidth();
        float height = model.getTotalHeight();

        float xStart = 0;
        float yStart = 0;
        if(model.getxAlign() < 0) xStart = (-camera.getWidth() / 2 - camera.getxPos()) / camera.getZoom();
        else if(model.getxAlign() > 0)xStart = ((camera.getWidth() / 2 - camera.getxPos()) - width * 2) / camera.getZoom();
        else xStart = ((-camera.getWidth() / 2 - camera.getxPos()) / camera.getZoom()) - (((-camera.getWidth() / 2 - camera.getxPos()) / camera.getZoom()) - ((camera.getWidth() / 2 - camera.getxPos()) - width * 2) / camera.getZoom()) / 2;

        //TODO add margin

        model.setDrawXstart(xStart);
        model.setDrawYstart(yStart);

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
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + xStart, (float)element.y + yStart, bSize, bSize, but.butR, but.butG, but.butB, but.butA, 0, but.hover ? 0.25f : 0, 0.25f, 0.25f);
                //mid,
                //TODO don't stretch but redraw
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + bSize + xStart, (float)element.y + yStart, element.width - 2 * bSize, bSize, but.butR, but.butG, but.butB, but.butA, 0.25f, but.hover ? 0.25f : 0, 0.25f, 0.25f);
                //end
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + element.width - bSize + xStart, (float)element.y + yStart, bSize, bSize, but.butR, but.butG, but.butB, but.butA, 0.5f, but.hover ? 0.25f : 0, 0.25f, 0.25f);
            }
        }
        VBODrawer.writeBufToMem(vbo_id, buffer);

        //text
        TextDrawer textDrawer = model.getMainModel().getTextDrawer();
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiLabel){
                MenuModel.GuiLabel sub = (MenuModel.GuiLabel) element;
                if(sub instanceof MenuModel.GuiButton)
                    textDrawer.drawText(sub.text, element.x + (element.width - textDrawer.getSizeForText(sub.text, element.height * 2/3)) / 2 + xStart , element.y + element.height / 6 + yStart, sub.textR, sub.textG, sub.textB, sub.textA, element.height * 2/3);
                else
                    textDrawer.drawText(sub.text, element.x + xStart, element.y + yStart, sub.textR, sub.textG, sub.textB, sub.textA, element.height);
            }
        }
        textDrawer.writeBufToMem();
    }

    public void draw(){
        VBODrawer.drawVBO(model.getMainModel(), vbo_id, texture_id, VBODrawer.COORDS_COLOR_TEXTURE_TYPE, drawAmount);

        model.getMainModel().getTextDrawer().draw(model.getMainModel());
    }
}
