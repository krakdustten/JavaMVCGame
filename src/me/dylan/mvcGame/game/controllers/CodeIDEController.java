package me.dylan.mvcGame.game.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import me.dylan.mvcGame.game.GameModel;

public class CodeIDEController {
    private GameModel model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Pause;

    @FXML
    private Button Start;

    @FXML
    private MenuItem MenuFileSave;

    @FXML
    private TextArea Code;

    @FXML
    private MenuItem MenuFileImportCode;

    @FXML
    void initialize() {
        Code.textProperty().addListener(((observable, oldValue, newValue) -> {
            codeTextChanged(observable, oldValue, newValue);
        }));
    }

    @FXML
    void startPressed(ActionEvent event) {
        System.out.println("Start");
    }

    @FXML
    void pausePressed(ActionEvent event) {
        System.out.println("Pause");
    }

    @FXML
    void menuFileSavePressed(ActionEvent event) {
        System.out.println("Save");
    }

    @FXML
    void menuFileImportCodePressed(ActionEvent event) {
        System.out.println("Import");
    }

    private void codeTextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue){
        model.setCode(newValue);
    }

    public void setGameModel(GameModel model) {
        this.model = model;
        Code.setText(model.getCode());
    }
}

