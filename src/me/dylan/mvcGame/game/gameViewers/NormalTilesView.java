package me.dylan.mvcGame.game.gameViewers;

import me.dylan.mvcGame.drawers.Texture;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.game.GameMapLoader;
import me.dylan.mvcGame.game.GameModel;
import me.dylan.mvcGame.game.gameObjects.Tiles;

public class NormalTilesView {
    private int textureMapId;
    private int vbo_id;

    private GameModel model;

    public NormalTilesView(GameModel model){
        this.model = model;
        textureMapId = Texture.createImageId("./img/TileMap.png");
        vbo_id = VBODrawer2D.createBufferId();
        update();
    }

    public void update(){
        if(!model.isMapChanged())return;
        int worldX = model.getWorldXSize();
        int worldY = model.getWorldYSize();

        float[] vertexes = new float[VBODrawer2D.calcArraySizeForSquares(VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, worldX * worldY)];
        int offset = 0;

        for(int i = 0; i < worldX; i++){
            for(int j = 0; j < worldY; j++){
                int color = model.getUnderGroundColor(i, j);
                int tileID = model.getTileID(i, j);

                int around = 0; // 0: up, 1: right, 2: down, 3: left
                around += model.getTileID(i, j + 1) == tileID ? 1 : 0;
                around += model.getTileID(i + 1, j) == tileID ? 2 : 0;
                around += model.getTileID(i, j - 1) == tileID ? 4 : 0;
                around += model.getTileID(i - 1, j) == tileID ? 8 : 0;
                switch (tileID){
                    case Tiles.FLOOR_ID:
                        offset = VBODrawer2D.draw2DSquare(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                                i * 64, j * 64, 64, 64,
                                (float)(((color >> 16) % 256) / 256.0f), (float)(((color >> 8) % 256) / 256.0f), (float)((color % 256) / 256.0f), 1,
                                0, 0, 0.125f, 0.125f);
                        break;
                    case Tiles.WALL_ID:
                        int tex = around % 4 + 4;
                        int tey = (around / 4) % 4;
                        offset = VBODrawer2D.draw2DSquare(vertexes, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE,
                                i * 64, j * 64, 64, 64,
                                (float)(((color >> 16) % 256.0f) / 256.0f), (float)(((color >> 8) % 256.0f) / 256.0f), (float)((color % 256.0f) / 256.0f), 1,
                                tex * 0.125f, tey * 0.125f, 0.125f, 0.125f);
                        break;
                        //TODO other tiles
                }
            }
        }
        VBODrawer2D.writeBufToMem(vbo_id, vertexes);
    }

    public void render(){
        VBODrawer2D.drawVBO(model.getMainModel(), vbo_id, textureMapId, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, VBODrawer2D.calcDrawAmountForSquares(model.getWorldXSize() * model.getWorldYSize()));
    }
}
