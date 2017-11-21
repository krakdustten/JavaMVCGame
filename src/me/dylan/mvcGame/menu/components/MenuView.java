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

        float xStartBackground = (-camera.getWidth() / 2 - camera.getxPos()) / camera.getZoom();
        float yStartBackground = (-camera.getHeight() / 2 - camera.getyPos()) / camera.getZoom();
        float widthBackground = camera.getWidth() / camera.getZoom();
        float heightBackground = camera.getHeight() / camera.getZoom();

        float backgroundRepeatSize = 64 / camera.getZoom();

        int drawAmountX = (int)Math.ceil(widthBackground / backgroundRepeatSize);
        int drawAmountY = (int) Math.ceil(heightBackground / backgroundRepeatSize);

        float xStart, yStart;
        if(model.getxAlign() < 0) xStart = xStartBackground + model.getxMargin();
        else if(model.getxAlign() > 0)xStart = (camera.getWidth() / 2 - camera.getxPos()) / camera.getZoom() - width - model.getxMargin();
        else xStart = -camera.getxPos() / camera.getZoom() - width / 2;
        if(model.getyAlign() < 0) yStart = yStartBackground + model.getyMargin();
        else if(model.getyAlign() > 0) yStart = (camera.getHeight() / 2 - camera.getyPos()) / camera.getZoom() - height - model.getyMargin();
        else yStart = -camera.getyPos() / camera.getZoom() - height / 2;

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
                offset = VBODrawer.draw2DSquare(buffer, offset, VBODrawer.COORDS_COLOR_TEXTURE_TYPE,
                        xStartBackground + i * backgroundRepeatSize, yStartBackground + j * backgroundRepeatSize, backgroundRepeatSize, backgroundRepeatSize,
                        model.getBackR(), model.getBackG(), model.getBackB(), model.getBackA(), 0.75f, 0, 0.25f, 0.25f);
            }
        }

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

    public void delete() {
        Texture.deleteImage(texture_id);
        VBODrawer.deleteVBO(vbo_id);
    }
}
