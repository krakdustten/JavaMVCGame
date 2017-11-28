package me.dylan.mvcGame.game;

import me.dylan.mvcGame.game.gameObjects.specialTiles.SpecialTile;
import me.dylan.mvcGame.main.MainModel;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameMapLoader {
    public static final int LOADER_VERSION = 001;

    public static GameModel loadMap(MainModel mainModel, String filePath){
        try {
            DataInputStream is = new DataInputStream(new FileInputStream(new File(filePath)));

            int version = is.readInt();
            if(version != LOADER_VERSION)return null;

            int worldXSize = is.readInt();
            int worldYSize = is.readInt();
            int[] butCol = new int[worldXSize * worldYSize];
            int[] tiles = new int[worldXSize * worldYSize];

            for(int i = 0; i < worldXSize; i++) {
                for (int j = 0; j < worldYSize; j++) {
                    butCol[i + j * worldXSize] = is.readInt();
                    tiles[i + j * worldXSize] = is.readInt();
                }
            }

            int totalSpecial = is.readInt();
            HashMap<Integer, SpecialTile> specialTiles = new HashMap<>();
            for(int i = 0; i < totalSpecial; i++){
                int pos = is.readInt();
                SpecialTile specialTile = SpecialTile.loadStaticFromFile(is);
                specialTiles.put(pos, specialTile);
            }


            is.close();
            return new GameModel(mainModel, worldXSize, worldYSize, butCol, tiles, specialTiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveMap(GameModel model, String filePath){
        try {
            DataOutputStream os = new DataOutputStream(new FileOutputStream(new File(filePath)));

            os.writeInt(LOADER_VERSION);
            os.writeInt(model.getWorldXSize());
            os.writeInt(model.getWorldYSize());

            HashMap<Integer, SpecialTile> specialTiles = model.getSpecialTiles();

            for(int i = 0; i < model.getWorldXSize(); i++){
                for(int j = 0; j < model.getWorldYSize(); j++){
                    os.writeInt(model.getUnderGroundColor(i, j));
                    os.writeInt(model.getTileID(i, j));
                }
            }

            os.writeInt(specialTiles.size());
            for(Map.Entry<Integer, SpecialTile> entry : specialTiles.entrySet()){
                os.writeInt(entry.getKey());
                entry.getValue().saveToFile(os);
            }

            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
