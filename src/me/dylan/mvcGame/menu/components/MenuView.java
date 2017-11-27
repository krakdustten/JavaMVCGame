package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.drawers.TextDrawer;
import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.main.Camera2D;

public class MenuView {
    private MenuModel model;
    private int texture_id;
    private int vbo_id;
    private int drawAmount = 0;

    public MenuView(MenuModel model, String menuImg){
        this.model = model;
        texture_id = Texture.createImageId(menuImg);
        vbo_id = VBODrawer2D.createBufferId();
        update();
    }

    public void update(){
        if(!model.needUpdating())return;

        Camera2D camera2D = model.getMainModel().getCamera2D();
        float width = model.getTotalWidth();
        float height = model.getTotalHeight();

        float xStartBackground = (-camera2D.getWidth() / 2 - camera2D.getxPos()) / camera2D.getZoom();
        float yStartBackground = (-camera2D.getHeight() / 2 - camera2D.getyPos()) / camera2D.getZoom();
        float widthBackground = camera2D.getWidth() / camera2D.getZoom();
        float heightBackground = camera2D.getHeight() / camera2D.getZoom();

        float backgroundRepeatSize = 64 / camera2D.getZoom();

        int drawAmountX = model.getBackA() <= 0 ? 0: (int)Math.ceil(widthBackground / backgroundRepeatSize);
        int drawAmountY = model.getBackA() <= 0 ? 0: (int) Math.ceil(heightBackground / backgroundRepeatSize);

        float xStart, yStart;
        if(model.getxAlign() < 0) xStart = xStartBackground + model.getxMargin();
        else if(model.getxAlign() > 0)xStart = (camera2D.getWidth() / 2 - camera2D.getxPos()) / camera2D.getZoom() - width - model.getxMargin();
        else xStart = -camera2D.getxPos() / camera2D.getZoom() - width / 2;
        if(model.getyAlign() < 0) yStart = yStartBackground + model.getyMargin();
        else if(model.getyAlign() > 0) yStart = (camera2D.getHeight() / 2 - camera2D.getyPos()) / camera2D.getZoom() - height - model.getyMargin();
        else yStart = -camera2D.getyPos() / camera2D.getZoom() - height / 2;

        model.setDrawXstart(xStart);
        model.setDrawYstart(yStart);

        //calc the total float buffer and draw amount
        drawAmount = drawAmountX * drawAmountY * 6;
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiButton)
                drawAmount += 6 * 3;
        }
        float[] buffer = new float[drawAmount * 9];
        int offset = 0;
        for(int i = 0; i < drawAmountX; i++){
            for(int j = 0; j < drawAmountY; j++){
                offset = VBODrawer2D.draw2DSquare(buffer, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                        xStartBackground + i * backgroundRepeatSize, yStartBackground + j * backgroundRepeatSize, backgroundRepeatSize, backgroundRepeatSize,
                        model.getBackR(), model.getBackG(), model.getBackB(), model.getBackA(), 0.75f, 0, 0.25f, 0.25f);
            }
        }

        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiButton){
                MenuModel.GuiButton but = (MenuModel.GuiButton) element;
                float bSize = element.height;
                //pre
                offset = VBODrawer2D.draw2DSquare(buffer, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + xStart, (float)element.y + yStart, bSize, bSize, but.butR, but.butG, but.butB, but.butA, 0, but.hover ? 0.25f : 0, 0.25f, 0.25f);
                //mid,
                //TODO don't stretch but redraw
                offset = VBODrawer2D.draw2DSquare(buffer, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + bSize + xStart, (float)element.y + yStart, element.width - 2 * bSize, bSize, but.butR, but.butG, but.butB, but.butA, 0.25f, but.hover ? 0.25f : 0, 0.25f, 0.25f);
                //end
                offset = VBODrawer2D.draw2DSquare(buffer, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, (float)element.x + element.width - bSize + xStart, (float)element.y + yStart, bSize, bSize, but.butR, but.butG, but.butB, but.butA, 0.5f, but.hover ? 0.25f : 0, 0.25f, 0.25f);
            }
        }
        VBODrawer2D.writeBufToMem(vbo_id, buffer);

        //text
        TextDrawer textDrawer = model.getMainModel().getTextDrawer();
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiLabel){
                MenuModel.GuiLabel sub = (MenuModel.GuiLabel) element;
                if(sub instanceof MenuModel.GuiButton)
                    textDrawer.drawText(sub.text, element.x + (element.width - textDrawer.getSizeForText(sub.text, element.height * 2/3)) / 2 + xStart , element.y + element.height / 6 + yStart, sub.textR, sub.textG, sub.textB, sub.textA, element.height * 2/3);
                else
                    textDrawer.drawText(sub.text, element.x + (element.width - textDrawer.getSizeForText(sub.text, element.height)) / 2 + xStart, element.y + yStart, sub.textR, sub.textG, sub.textB, sub.textA, element.height);
            }
        }
        textDrawer.writeBufToMem();
    }

    public void draw(){
        VBODrawer2D.drawVBO(model.getMainModel(), vbo_id, texture_id, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, drawAmount);

        model.getMainModel().getTextDrawer().draw(model.getMainModel());
    }

    public void delete() {
        Texture.deleteImage(texture_id);
        VBODrawer2D.deleteVBO(vbo_id);
    }
}
