package me.dylan.mvcGame.worldEditor;

import me.dylan.mvcGame.drawers.TextureTileMap;
import me.dylan.mvcGame.drawers.VBODrawer2D;
import me.dylan.mvcGame.game.gameObjects.Tiles;

/**
 * The world viewer of the world editor.
 *
 * @author Dylan Gybels
 */
public class WorldEditorView {
    private WorldEditorModel model;

    private int vbo_id;
    private TextureTileMap tileMap;

    /**
     * Create a new world editor view.
     *
     * @param model The world editor model.
     */
    public WorldEditorView(WorldEditorModel model) {
        this.model = model;

        tileMap = new TextureTileMap("img/TileMap.png", 8, 8);
        vbo_id = VBODrawer2D.createBufferId();
        update();
    }

    /**
     * Update the buffers on the graphics card if needed.
     */
    public void update() {
        if(!model.getMapChanged())return;
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
                        offset = tileMap.drawTile2D(vertexes, offset, i * 64, j * 64, 64, 64,
                                ((color >> 16) % 256) / 256.0f, ((color >> 8) % 256) / 256.0f, (color % 256) / 256.0f, 1,
                                0, 0);
                        break;
                    case Tiles.WALL_ID:
                        int tex = around % 4 + 4;
                        int tey = (around / 4) % 4;
                        offset = tileMap.drawTile2D(vertexes, offset, i * 64, j * 64, 64, 64,
                                ((color >> 16) % 256) / 256.0f, ((color >> 8) % 256) / 256.0f, (color % 256) / 256.0f, 1,
                                tex, tey);
                        break;
                    case Tiles.START_ID:
                        int tex2 = (around / 8 ) % 2 == 0 ? 0 : (around / 2 ) % 2 == 1 ? 1 : 2;
                        int tey2 = around % 2 == 0 ? 2 : (around / 4 ) % 2 == 1 ? 3 : 4;
                        offset = tileMap.drawTile2D(vertexes, offset, i * 64, j * 64, 64, 64,
                                ((color >> 16) % 256) / 256.0f, ((color >> 8) % 256) / 256.0f, (color % 256) / 256.0f, 1,
                                tex2, tey2);
                        break;
                    case Tiles.FINISH_ID:
                        int tex3 = (around / 8 ) % 2 == 0 ? 0 : (around / 2 ) % 2 == 1 ? 1 : 2;
                        int tey3 = around % 2 == 0 ? 5 : (around / 4 ) % 2 == 1 ? 6 : 7;
                        offset = tileMap.drawTile2D(vertexes, offset, i * 64, j * 64, 64, 64,
                                ((color >> 16) % 256) / 256.0f, ((color >> 8) % 256) / 256.0f, (color % 256) / 256.0f, 1,
                                tex3, tey3);
                        break;
                }
            }
        }
        VBODrawer2D.writeBufToMem(vbo_id, vertexes);
    }

    /**
     * Render the buffers on the graphics card.
     */
    public void render() {
        VBODrawer2D.drawVBO(model.getMainModel(), vbo_id, tileMap.getTexture_id(), VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, VBODrawer2D.calcDrawAmountForSquares(model.getWorldXSize() * model.getWorldYSize()));
    }

    /**
     * Clear all vars and distroy.
     */
    public void distroy() {
        VBODrawer2D.deleteVBO(vbo_id);
        tileMap.distroy();
    }
}
