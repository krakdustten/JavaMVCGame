package me.dylan.mvcGame.game.gameObjects.specialTiles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class SpecialTile {
    private final int ID;
    private static HashMap<Integer, Class<? extends SpecialTile>> specialTiles;

    protected SpecialTile(int ID){
        this.ID = ID;
    }

    public void saveToFile(DataOutputStream os) throws IOException {
        os.writeInt(ID);
    }

    public void loadFromFile(DataInputStream is) throws IOException{ }

    public static SpecialTile loadStaticFromFile(DataInputStream is) throws IOException {
        int id = is.readInt();
        if(!specialTiles.containsKey(id)) return null;
        try {
            SpecialTile tile = specialTiles.get(id).newInstance();
            tile.loadFromFile(is);
            return tile;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void render(){}
    public void update(){}
}
