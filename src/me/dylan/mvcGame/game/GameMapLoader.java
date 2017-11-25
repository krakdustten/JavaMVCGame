package me.dylan.mvcGame.game;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameMapLoader {
    public static final int LOADER_VERSION = 001;

    public static GameModel loadMap(String filePath){
        try {
            DataInputStream is = new DataInputStream(new FileInputStream(new File(filePath)));
            GameModel model = new GameModel();

            is.close();
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveMap(GameModel model, String filePath){
        try {
            DataOutputStream os = new DataOutputStream(new FileOutputStream(new File(filePath)));

            os.write(LOADER_VERSION);
            os.write(model.getWorldXSize());
            os.write(model.getWorldYSize());

            int[] butColor = model.getUnderGroundColor();
            int[] tile = model.getTileID();
            HashMap<Integer, SpecialTile> specialTiles = model.getSpecialTiles();

            for(int i = 0; i < model.getWorldXSize(); i++){
                for(int j = 0; j < model.getWorldYSize(); j++){
                    int t = i + j * model.getWorldXSize();
                    os.write(butColor[t]);
                    os.write(tile[t]);
                }
            }

            for(Map.Entry<Integer, SpecialTile> entry : specialTiles.entrySet()){
                os.write(entry.getKey());
                entry.getValue().saveToFile(os);
            }

            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO load world from file (Controller)
}
