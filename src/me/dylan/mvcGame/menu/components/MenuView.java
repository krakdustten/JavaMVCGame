package me.dylan.mvcGame.menu.components;

import me.dylan.mvcGame.drawers.TextDrawer;
import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.main.Camera2D;

/**
 * The view of a menu.
 *
 * @author Dylan Gybels
 */
public class MenuView {
    private MenuModel model;
    private int texture_id;
    private int vbo_id;
    private int drawAmount = 0;

    /**
     * Create a new menu wiew.
     * @param model The model of the menu.
     * @param menuImg The path to the image the menu needs to use.
     */
    public MenuView(MenuModel model, String menuImg){
        this.model = model;
        texture_id = Texture.createImageId(menuImg);
        vbo_id = VBODrawer2D.createBufferId();
        update();
    }

    /**
     * Update the buffers on the graphics card if needed.
     */
    public void update(){
        if(!model.needUpdating())return;

        Camera2D camera2D = model.getMainModel().getCamera2D();
        float z = camera2D.getZoom();
        float width = model.getTotalWidth();
        float height = model.getTotalHeight();

        float xStartBackground = (-camera2D.getWidth() / 2) / z - camera2D.getxPos();
        float yStartBackground = (-camera2D.getHeight() / 2) / z - camera2D.getyPos();
        float widthBackground = camera2D.getWidth() / z;
        float heightBackground = camera2D.getHeight() / z;

        float backgroundRepeatSize = 64 / z;

        int drawAmountX = model.getBackA() <= 0 ? 0: (int)Math.ceil(widthBackground / backgroundRepeatSize);
        int drawAmountY = model.getBackA() <= 0 ? 0: (int) Math.ceil(heightBackground / backgroundRepeatSize);

        float xStart, yStart;
        if(model.getxAlign() < 0) xStart = xStartBackground + model.getxMargin() / z;
        else if(model.getxAlign() > 0)xStart = (camera2D.getWidth() / 2 - width - model.getxMargin()) / z - camera2D.getxPos();
        else xStart = -camera2D.getxPos() - (width / 2) / z;
        if(model.getyAlign() < 0) yStart = yStartBackground + model.getyMargin() / z;
        else if(model.getyAlign() > 0) yStart = (camera2D.getHeight() / 2 - height - model.getyMargin()) / z - camera2D.getyPos();
        else yStart = -camera2D.getyPos() - (height / 2) / z;

        model.setDrawXstart(xStart);
        model.setDrawYstart(yStart);

        //calc the total float buffer and draw amount
        drawAmount = drawAmountX * drawAmountY * 6;
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiButton){
                int temp = (int) (6 * Math.ceil((float)(element.width) / element.height));
                drawAmount += temp;
            }
                //drawAmount += 6 * 3;

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
                offset = VBODrawer2D.draw2DSquare(buffer, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                        (float)element.x / z + xStart, (float)element.y / z + yStart, bSize / z, bSize / z,
                        but.butR, but.butG, but.butB, but.butA,
                        0, but.hover ? 0.25f : 0, 0.25f, 0.25f);
                //mid,
                for(float i = bSize; i < (element.width - bSize); i += bSize) {
                    float percWidth = (i - (element.width - bSize));
                    if(percWidth <= 0) percWidth = bSize;

                    offset = VBODrawer2D.draw2DSquare(buffer, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                            (element.x + i) / z + xStart, (float) element.y / z + yStart, percWidth / z, bSize / z,
                            but.butR, but.butG, but.butB, but.butA,
                            0.25f, but.hover ? 0.25f : 0, 0.25f * percWidth / bSize, 0.25f);
                }
                //end
                offset = VBODrawer2D.draw2DSquare(buffer, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                        (element.x + element.width - bSize) / z + xStart, (float)element.y / z + yStart, bSize / z, bSize / z,
                        but.butR, but.butG, but.butB, but.butA,
                        0.5f, but.hover ? 0.25f : 0, 0.25f, 0.25f);
            }
        }
        VBODrawer2D.writeBufToMem(vbo_id, buffer);

        //text
        TextDrawer textDrawer = model.getMainModel().getTextDrawer();
        for(MenuModel.GuiElement element : model.getAllGuiElements()){
            if(element instanceof MenuModel.GuiLabel){
                MenuModel.GuiLabel sub = (MenuModel.GuiLabel) element;
                if(sub instanceof MenuModel.GuiButton)
                    textDrawer.drawText(sub.text,
                            (element.x + (element.width - textDrawer.getSizeForText(sub.text, element.height * 2/3)) / 2) / z + xStart ,
                            (element.y + element.height / 6 ) / z + yStart, sub.textR, sub.textG, sub.textB, sub.textA,
                            (element.height * 2/3)  / z);
                else
                    textDrawer.drawText(sub.text,
                            (element.x + (element.width - textDrawer.getSizeForText(sub.text, element.height)) / 2) / z + xStart,
                            element.y / z + yStart, sub.textR, sub.textG, sub.textB, sub.textA,
                            element.height / z);
            }
        }
        textDrawer.writeBufToMem();
    }

    /**
     * Draw the buffer to the screen.
     */
    public void draw(){
        VBODrawer2D.drawVBO(model.getMainModel(), vbo_id, texture_id, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, drawAmount);

        model.getMainModel().getTextDrawer().draw(model.getMainModel());
    }

    /**
     * Clear all vars.
     */
    public void delete() {
        Texture.deleteImage(texture_id);
        VBODrawer2D.deleteVBO(vbo_id);
    }
}
