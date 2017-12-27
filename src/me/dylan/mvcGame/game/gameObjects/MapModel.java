package me.dylan.mvcGame.game.gameObjects;

import me.dylan.mvcGame.game.gameObjects.robot.RobotModel;
import me.dylan.mvcGame.main.MainModel;
import me.dylan.mvcGame.worldEditor.WorldEditorModel;

/**
 * The map model.
 *
 * @author Dylan Gybels
 */
public class MapModel {
    private MainModel mainModel;
    //player stuff
    private RobotModel robot;

    //world stuff
    private int worldXSize;
    private int worldYSize;
    private int[] underGroundColor;
    private byte[] tileID;
    private boolean mapChanged = true;
    private int startX, startY;
    private int finishX, finishY;

    //code
    private String code;
    private boolean codeChanged = true;
    private boolean loseOnWallHit = true;

    /**
     * Create a new map.
     *
     * @param mainModel The main model.
     * @param worldXSize The x size of the world.
     * @param worldYSize The y size of the world.
     * @param underGroundColor The array that holds all the underground colors.
     * @param tileID The array that holds the tile ID's.
     */
    public MapModel(MainModel mainModel, int worldXSize, int worldYSize, int[] underGroundColor, byte[] tileID){
        this.mainModel = mainModel;

        this.worldXSize = worldXSize;
        this.worldYSize = worldYSize;

        int totalArraySize = worldXSize * worldYSize;
        if(underGroundColor == null) underGroundColor = new int[totalArraySize];
        if(tileID == null) tileID = new byte[totalArraySize];
        if(underGroundColor.length != totalArraySize) return;
        if(tileID.length != totalArraySize) return;
        this.underGroundColor = underGroundColor;
        this.tileID = tileID;

        findStartAndFinish();

        code = "\n" +
                "def tick():\n" +
                "     global MotorL\n" +
                "     global MotorR\n";
    }

    /*GETTERS*/

    /**
     * Get the main model this map uses.
     * @return The main model this map uses.
     */
    public MainModel getMainModel() { return mainModel; }

    /**
     * Get the world x size.
     * @return The world x size.
     */
    public int getWorldXSize() { return worldXSize; }

    /**
     * Get the world y size.
     * @return The world y size.
     */
    public int getWorldYSize() { return worldYSize; }

    /**
     * Get the underground color at specific coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The color as an integer. (MSB /RRRRRRRR/GGGGGGGG/BBBBBBBB/ LSB)
     */
    public int getUnderGroundColor(int x, int y) {
        if(x < 0 || x >= worldXSize || y < 0 || y >= worldYSize) return -1;
        return underGroundColor[x + y * worldXSize];
    }

    /**
     * Get the tile ID at specific coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The tile ID.
     */
    public int getTileID(int x, int y) {
        if(x < 0 || x >= worldXSize || y < 0 || y >= worldYSize) return -1;
        return tileID[x + y * worldXSize];
    }

    /**
     * Get if the map has changed.
     * @return If the map was changed.
     */
    public boolean getMapChanged(){if(mapChanged){mapChanged = false; return true;} return false;}

    /**
     * Get the x coordinate of the start.
     * @return The x coordinate of the start.
     */
    public int getStartX() { return startX; }

    /**
     * Get the y coordinate of the start.
     * @return The y coordinate of the start.
     */
    public int getStartY() { return startY; }

    /**
     * Get the x coordinate of the finish.
     * @return The x coordinate of the finish.
     */
    public int getFinishX() { return finishX; }

    /**
     * Get the y coordinate of the finish.
     * @return The y coordinate of the finish.
     */
    public int getFinishY() { return finishY; }

    /**
     * Get the code.
     * @return The code.
     */
    public String getCode() { return code; }

    /**
     * Get if the code is changed.
     * @return If the code is changed.
     */
    public boolean getCodeChanged() { return codeChanged; }

    /**
     * Get if a wall hit is losing.
     * @return If a wall hit is losing.
     */
    public boolean getLoseOnWallHit() { return loseOnWallHit; }

    /**
     * Get the robot in this map.
     * @return The robot of this map.
     */
    public RobotModel getRobot() { return robot; }

    /*SETTERS*/

    /**
     * Set the world x size.
     * @param worldXSize The new world x size.
     */
    public void setWorldXSize(int worldXSize) {
        changeActualMapSize(worldXSize, worldYSize, 0, 0);
        this.worldXSize = worldXSize;
        mapChanged = true;
    }

    /**
     * Set the world y size.
     * @param worldYSize The new world y size.
     */
    public void setWorldYSize(int worldYSize) {
        changeActualMapSize(worldXSize, worldYSize, 0, 0);
        this.worldYSize = worldYSize;
        mapChanged = true;
    }

    /**
     * Set the underground color at the specified coordinates.
     * @param underGroundColor The new underground color.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void setUnderGroundColor(int underGroundColor, int x, int y) { this.underGroundColor[x + y * worldXSize] = underGroundColor; mapChanged = true; }

    /**
     * Set the tile id at the specified coordinates.
     * @param tileID The new tile id.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public void setTileID(byte tileID, int x, int y) { this.tileID[x + y * worldXSize] = tileID; mapChanged = true;}

    /**
     * Set the code.
     * @param code The new code.
     */
    public void setCode(String code) { this.code = code;codeChanged = true; }

    /**
     * Set the code changed.
     * @param codeChanged If the code is changed.
     */
    public void setCodeChanged(boolean codeChanged){ this.codeChanged = codeChanged; }

    /**
     * Set the lose on wall hit paramater.
     * @param loseOnWallHit The lose on wall hit paramater.
     */
    public void setLoseOnWallHit(boolean loseOnWallHit) { this.loseOnWallHit = loseOnWallHit; }

    /**
     * Set the robot of this world.
     * @param robot The new robot of this world.
     */
    public void setRobot(RobotModel robot) { this.robot = robot; }

    /*OTHER SMALL LOGIC*/

    /**
     * Change the map size to the new given size.
     *
     * You can also offset the hole map by changing the x, y offsets. This can be 1<.
     * @param newXSize The new x size.
     * @param newYSize The new y size.
     * @param xOffset The x offset.
     * @param yOffset The y offset.
     */
    public void changeActualMapSize(int newXSize, int newYSize, int xOffset, int yOffset){
        int[] underGroundColor = new int[newXSize * newYSize];
        byte[] tileID = new byte[newXSize * newYSize];

        int xSize;
        int ySize;
        int negXOffset = 0;
        int negYOffset = 0;

        if(xOffset < 0){
            xSize = worldXSize < newXSize ? worldXSize : newXSize;
            negXOffset = -xOffset;
            xOffset = 0;
        }else xSize = worldXSize < (newXSize + xOffset) ? worldXSize : newXSize + xOffset;

        if(yOffset < 0){
            ySize = worldYSize < newYSize ? worldYSize : newXSize;
            negYOffset = -yOffset;
            yOffset = 0;
        }else ySize = worldYSize < (newYSize + yOffset) ? worldYSize : newYSize + yOffset;


        for(int i = 0; i < xSize; i++){
            for(int j = 0; j < ySize; j++){
                underGroundColor[i + (j + yOffset) * newXSize + xOffset] = this.underGroundColor[i + (j + negYOffset) * worldXSize + negXOffset];
                tileID[i + (j + yOffset) * newXSize + xOffset] = this.tileID[i + (j + negYOffset) * worldXSize + negXOffset];
            }
        }
        this.underGroundColor = underGroundColor;
        this.tileID = tileID;
        this.worldXSize = newXSize;
        this.worldYSize = newYSize;
    }

    /**
     * Check if the map can be smaller than it is now.
     * And make the map smaller if it can be.
     * @param model The world editor model.
     */
    public void checkIfMapCanBeSmaller(WorldEditorModel model) {
        if(worldXSize <= 0 || worldYSize <= 0)return;

        boolean tileHereTop = false;
        boolean tileHereBottom = false;
        boolean tileHereLeft = false;
        boolean tileHereRight = false;

        for(int i = 0; i < worldXSize; i++){
            if(!tileHereTop){
                if(getTileID(i, worldYSize - 1) != Tiles.NO_TILE) tileHereTop = true;
            }
            if(!tileHereBottom){
                if(getTileID(i, 0) != Tiles.NO_TILE) tileHereBottom = true;
            }
            if(tileHereTop && tileHereBottom) break;
        }
        for(int i = 0; i < worldYSize; i++){
            if(!tileHereLeft){
                if(getTileID(0, i) != Tiles.NO_TILE) tileHereLeft = true;
            }
            if(!tileHereRight){
                if(getTileID(worldXSize - 1, i) != Tiles.NO_TILE) tileHereRight = true;
            }
            if(tileHereLeft && tileHereRight) break;
        }

        if(!tileHereBottom || !tileHereTop || !tileHereLeft || !tileHereRight){
            changeActualMapSize(
                    worldXSize - (!tileHereLeft ? 1 : 0) - (!tileHereRight ? 1 : 0),
                    worldYSize - (!tileHereTop ? 1 : 0) - (!tileHereBottom ? 1 : 0),
                    !tileHereLeft ? -1 : 0,
                    !tileHereBottom ? -1 : 0);

            model.moveView(!tileHereLeft ? 64f : 0f, !tileHereBottom ? 64f : 0f);

            checkIfMapCanBeSmaller(model);
        }
    }

    /**
     * Find the start and finish of the map.
     */
    public void findStartAndFinish(){
        //find start and finish
        boolean startFound = false;
        boolean finishFound = false;
        for(int i = 0; i < worldXSize - 3; i++) {
            for (int j = 0; j < worldYSize - 3; j++) {
                if(!startFound){
                    if(getTileID(i, j) == Tiles.START_ID){
                        startX = i;
                        startY = j;
                        startFound = true;
                    }
                }else if(!finishFound){
                    if(getTileID(i, j) == Tiles.FINISH_ID){
                        finishX = i;
                        finishY = j;
                        finishFound = true;
                    }
                }else{
                    break;
                }
            }
        }
    }
}
