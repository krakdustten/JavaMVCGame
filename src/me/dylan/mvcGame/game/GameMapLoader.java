package me.dylan.mvcGame.game;

import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.specialTiles.SpecialTile;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.other.ResourceHandling;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameMapLoader {
    public static final int LOADER_VERSION = 001;

    public static GameModel loadMap(MainModel mainModel, String filePath){
        try {
            DataInputStream is = new DataInputStream(ResourceHandling.getFileOrResource(filePath));

            if(is == null) return null;

            int version = is.readInt();
            if(version != LOADER_VERSION)return null;

            int worldXSize = is.readInt();
            int worldYSize = is.readInt();
            int[] butCol = new int[worldXSize * worldYSize];
            byte[] tiles = new byte[worldXSize * worldYSize];

            for(int i = 0; i < worldXSize; i++) {
                for (int j = 0; j < worldYSize; j++) {
                    butCol[i + j * worldXSize] = is.readInt();
                    tiles[i + j * worldXSize] = is.readByte();
                }
            }

            int totalSpecial = is.readInt();
            HashMap<Integer, SpecialTile> specialTiles = new HashMap<>();
            for(int i = 0; i < totalSpecial; i++){
                int pos = is.readInt();
                SpecialTile specialTile = SpecialTile.loadStaticFromFile(is);
                specialTiles.put(pos, specialTile);
            }

            int starterCodeChars = is.readInt();
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < starterCodeChars; i++) stringBuilder.append(is.readChar());
            String code = stringBuilder.toString();

            is.close();

            GameModel gameModel= new GameModel(mainModel, worldXSize, worldYSize, butCol, tiles, specialTiles);
            gameModel.setCode(code);

            return gameModel;
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
                    os.writeByte(model.getTileID(i, j));
                }
            }

            os.writeInt(specialTiles.size());
            for(Map.Entry<Integer, SpecialTile> entry : specialTiles.entrySet()){
                os.writeInt(entry.getKey());
                entry.getValue().saveToFile(os);
            }

            char[] starterCodeChars = model.getCode().toCharArray();
            os.writeInt(starterCodeChars.length);
            for(int i = 0; i < starterCodeChars.length; i++) os.writeChar(starterCodeChars[i]);

            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static GameModel loadSave(MainModel mainModel, String gameFileToLoad) {
        return null;
    }

    public static boolean saveSave(GameModel model, String filePath) {
        return false;
    }
}
