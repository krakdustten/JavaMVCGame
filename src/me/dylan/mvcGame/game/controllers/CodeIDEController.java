package me.dylan.mvcGame.game.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
    private ListView<String> Variables;

    @FXML
    private MenuItem MenuFileSave;

    @FXML
    private TextArea Log;

    @FXML
    private TextArea Code;

    @FXML
    private MenuItem MenuFileImportCode;

    @FXML
    void initialize() {
        Code.textProperty().addListener((this::codeTextChanged));
    }

    @FXML
    void startPressed(ActionEvent event) {
        if(Start.getText().contains("Stop")) {
            model.setShouldGameReset(true);
            model.setGameStarted(false);
            Start.setText("Start");
            Pause.setDisable(true);
            Pause.setText("Pause");
            Code.setDisable(false);
            model.setCodeChanged(true);
        }
        else{
            model.setGameStarted(true);
            Start.setText("Stop");
            Pause.setDisable(false);
            Code.setDisable(true);
            model.setError("");
        }
    }

    @FXML
    void pausePressed(ActionEvent event) {
        if(Pause.getText().contains("Pause")){
            model.setGameStarted(false);
            Pause.setText("Un pause");
        }
        else {
            model.setGameStarted(true);
            Pause.setText("Pause");
        }
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

        for(String str : model.getPlayer().getSensorNames()){
            Variables.getItems().add(str);
        }
    }

    public void gameTick(){
        if(model.getErrorChanged()){
            Log.setText(model.getError());
            model.setErrorChanged(false);
        }
    }
}

