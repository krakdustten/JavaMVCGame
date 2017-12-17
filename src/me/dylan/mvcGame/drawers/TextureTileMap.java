package me.dylan.mvcGame.drawers;

public class TextureTileMap {
    private int texture_id;
    protected float xBlocksize;
    protected float yBlocksize;


    public TextureTileMap(int texture_id, int xBlocks, int yBlocks){
        this.texture_id = texture_id;
        this.xBlocksize = 1.0f / xBlocks;
        this.yBlocksize = 1.0f / yBlocks;
    }

    public TextureTileMap(String texturePath, int xBlocks, int yBlocks){
        this(Texture.createImageId(texturePath), xBlocks, yBlocks);
    }

    public int drawTile2D(float[] array, int offset, float x, float y, float dx, float dy, float r, float g, float b, float a, int xBlock, int yBlock){
        float tx = xBlock * xBlocksize;
        float ty = yBlock * yBlocksize;
        return VBODrawer2D.draw2DSquare(array, offset, VBODrawer2D.COORDS_COLOR_TEXTURE_TYPE, x, y, dx, dy, r, g, b, a, tx, ty, xBlocksize, yBlocksize);
    }

    public int getTexture_id() { return texture_id; }

    public void distroy() {
        Texture.deleteImage(texture_id);
    }
}
