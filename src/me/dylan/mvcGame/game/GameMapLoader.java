package me.dylan.mvcGame.game;

import me.dylan.mvcGame.game.gameObjects.MapModel;
import me.dylan.mvcGame.game.gameObjects.robot.RobotModel;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.other.ResourceHandling;

import java.io.*;

/**
 * This class loads or save the games levels and maps.
 *
 * @author Dylan Gybels
 */
public class GameMapLoader {
    /**The version of the loader.*/
    public static final int LOADER_VERSION = 1;

    /**
     * Load a map from a specified path.
     * The path will be the running path + the given path.
     *
     * @param mainModel The main model.
     * @param filePath The path of the map.
     * @return The loaded map.
     */
    public static MapModel loadMap(MainModel mainModel, String filePath){
        try {
            DataInputStream is = new DataInputStream(ResourceHandling.getFileOrResource(filePath));

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

            MapModel mapModel= new MapModel(mainModel, worldXSize, worldYSize, butCol, tiles);

            int starterCodeChars = is.readInt();
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < starterCodeChars; i++) stringBuilder.append(is.readChar());
            mapModel.setCode(stringBuilder.toString());

            mapModel.setRobot(new RobotModel(mapModel));
            int sensorAmount = is.readInt();
            for(int i = 0; i < sensorAmount; i++){
                Sensor sensor = Sensor.getNewSensorFromId(is.readInt(), mapModel);
                if(sensor == null) break;
                sensor.loadSensor(is);
                mapModel.getRobot().addSensor(sensor);
            }

            mapModel.setLoseOnWallHit(is.readBoolean());
            is.close();

            return mapModel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save a map to the specified path.
     * The path will be the running path + the given path.
     *
     * @param model The map to save.
     * @param filePath The path to save it to.
     * @return True if the map was saved successfully.
     */
    public static boolean saveMap(MapModel model, String filePath){
        try {
            filePath = ResourceHandling.GetExecutionPath() + "\\" + filePath;
            File file = new File(filePath);
            if(!file.exists()){
                file.getParentFile().mkdirs();
            }

            DataOutputStream os = new DataOutputStream(new FileOutputStream(file));

            os.writeInt(LOADER_VERSION);
            os.writeInt(model.getWorldXSize());
            os.writeInt(model.getWorldYSize());

            for(int i = 0; i < model.getWorldXSize(); i++){
                for(int j = 0; j < model.getWorldYSize(); j++){
                    os.writeInt(model.getUnderGroundColor(i, j));
                    os.writeByte(model.getTileID(i, j));
                }
            }

            char[] starterCodeChars = model.getCode().toCharArray();
            os.writeInt(starterCodeChars.length);
            for (char starterCodeChar : starterCodeChars) os.writeChar(starterCodeChar);

            Sensor[] sensors = model.getRobot().getSensors();
            os.writeInt(sensors.length);
            for (Sensor sensor : sensors) {
                os.writeInt(sensor.getType());
                sensor.saveSensor(os);
            }

            os.writeBoolean(model.getLoseOnWallHit());

            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Load a save file from a specified path.
     * The path will be the running path + the given path.
     *
     * @param mainModel The main model.
     * @param filePath The path of the file to load.
     * @return The loaded map.
     */
    public static MapModel loadSave(MainModel mainModel, String filePath) {
        try {
            filePath = ResourceHandling.GetExecutionPath() + "/" + filePath;
            File file = new File(filePath);
            if(!file.exists()){
                return null;
            }

            DataInputStream is = new DataInputStream(new FileInputStream(file));

            int version = is.readInt();
            if(version != LOADER_VERSION + 10000)return null;

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

            MapModel mapModel= new MapModel(mainModel, worldXSize, worldYSize, butCol, tiles);

            int codeChars = is.readInt();
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < codeChars; i++) stringBuilder.append(is.readChar());
            mapModel.setCode(stringBuilder.toString());


            mapModel.setRobot(new RobotModel(mapModel));
            int sensorAmount = is.readInt();
            for(int i = 0; i < sensorAmount; i++){
                Sensor sensor = Sensor.getNewSensorFromId(is.readInt(), mapModel);
                if(sensor == null) break;
                sensor.loadSensor(is);
                mapModel.getRobot().addSensor(sensor);
            }

            mapModel.setLoseOnWallHit(is.readBoolean());

            is.close();
            return mapModel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save a save file to the specified path.
     * The path will be the running path + the given path.
     *
     * @param model The map to save.
     * @param filePath The path of the save file.
     * @return True if the save was saved successfully.
     */
    public static boolean saveSave(MapModel model, String filePath) {
        try {
            filePath = ResourceHandling.GetExecutionPath() + "\\" + filePath;
            File file = new File(filePath);
            if(!file.exists())
                file.getParentFile().mkdirs();


            DataOutputStream os = new DataOutputStream(new FileOutputStream(file));

            os.writeInt(LOADER_VERSION + 10000);
            os.writeInt(model.getWorldXSize());
            os.writeInt(model.getWorldYSize());

            for(int i = 0; i < model.getWorldXSize(); i++){
                for(int j = 0; j < model.getWorldYSize(); j++){
                    os.writeInt(model.getUnderGroundColor(i, j));
                    os.writeByte(model.getTileID(i, j));
                }
            }

            char[] codeChars = model.getCode().toCharArray();
            os.writeInt(codeChars.length);
            for (char codeChar : codeChars) os.writeChar(codeChar);

            Sensor[] sensors = model.getRobot().getSensors();
            os.writeInt(sensors.length);
            for (Sensor sensor : sensors) {
                os.writeInt(sensor.getType());
                sensor.saveSensor(os);
            }


            os.writeBoolean(model.getLoseOnWallHit());

            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
