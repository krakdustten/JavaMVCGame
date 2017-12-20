package me.dylan.mvcGame.worldEditor.subWindow;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import me.dylan.mvcGame.game.gameObjects.Tiles;
import me.dylan.mvcGame.worldEditor.WorldEditorModel;

import java.net.URL;
import java.util.ResourceBundle;

public class WorldEditorSubController {
    private WorldEditorModel model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> TilesSelector;


    //TODO fill sensornames with sensor names
    @FXML
    private ListView<String> SensorNames;

    @FXML
    private TextArea Code;

    @FXML
    void initialize() {
        TilesSelector.getItems().addAll("Floor", "Wall", "Start", "End");
        TilesSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> TilesSelectorClick(oldValue, newValue));

        Code.textProperty().addListener((this::codeTextChanged));
    }

    private void TilesSelectorClick(String oldValue, String newValue) {
        byte tileID = 0;
        switch (newValue){
            case "Floor":
                tileID = Tiles.FLOOR_ID;
                break;
            case "Wall":
                tileID = Tiles.WALL_ID;
                break;
            case "Start":
                tileID = Tiles.START_ID;
                break;
            case "End":
                tileID = Tiles.END_ID;
                break;
        }
        model.setSelectedTile(tileID);
    }

    private void codeTextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Code.setText(newValue);
    }

    public void setGameModel(WorldEditorModel model) {
        this.model = model;
        Code.setText(model.getCode());
    }

    public void gameTick() {
    }
}
