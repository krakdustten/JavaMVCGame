package me.dylan.mvcGame.game.tiles.specialTiles;

import org.reflections.Reflections;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class SpecialTile {
    private final int ID;
    private static HashMap<Integer, Class<? extends SpecialTile>> specialTiles;

    protected SpecialTile(int ID){
        this.ID = ID;
    }

    public static void registerAllSpecialTiles(){
        specialTiles = new HashMap<>();
        Reflections reflections = new Reflections("me.dylan.mvcGame.game.tiles.specialTiles");

        Set<Class<? extends SpecialTile>> sub = reflections.getSubTypesOf(SpecialTile.class);
        for(Class<? extends SpecialTile> cla : sub){
            try {
                SpecialTile instance = cla.newInstance();
                specialTiles.put(instance.ID, cla);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveToFile(DataOutputStream os) throws IOException {
        os.writeInt(ID);
    }

    public void loadFromFile(DataInputStream is) throws IOException{

    }

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
}
