package me.dylan.mvcGame.game.controllers;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.gameObjects.robot.RobotPlayerModel;

/**
 * This is the controller of the IDE part of the game itself.
 *
 * @author Dylan Gybels
 */
public class CodeIDEController {
    private GameModel model;

    @FXML
    private Button Pause;

    @FXML
    private Button Start;

    @FXML
    private ListView<String> Variables;

    @FXML
    private TextArea Log;

    @FXML
    private TextArea Code;

    /**
     * Initialize the controller.
     */
    @FXML
    void initialize() {
        Code.textProperty().addListener((this::codeTextChanged));
    }

    /**
     * The start button is pressed.
     * @param event The event fired.
     */
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

    /**
     * The pause button is pressed.
     * @param event The event fired.
     */
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

    private void codeTextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue){
        model.setCode(newValue);
    }

    /**
     * Set the game model of the controller.
     * @param model The game model.
     */
    public void setGameModel(GameModel model) {
        this.model = model;
        Code.setText(model.getCode());

        Variables.getItems().clear();
        for(String str : ((RobotPlayerModel)model.getRobot()).getSensorNames()){
            Variables.getItems().add(str);
        }
    }

    /**
     * Tick the controller/view.
     */
    public void gameTick(){
        if(model.getErrorChanged()){
            Log.setText(model.getError());
            model.setErrorChanged(false);
        }
    }
}

