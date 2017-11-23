package me.dylan.mvcGame.game;

import me.dylan.mvcGame.main.MainModel;

import java.util.HashMap;

public class GameModel {
    private MainModel mainModel;
    private int worldXSize;
    private int worldYSize;

    private int[] underGroundColor;
    private int[] tileID;
    private HashMap<Integer, SpecialTile> specialTiles;

    private RobotPlayer player;

    //TODO make world drawer --> push data once or push in chunks or calc parts that are visible and push that
    //TODO load world from file (Controller)

}
