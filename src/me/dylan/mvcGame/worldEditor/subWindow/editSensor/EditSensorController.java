package me.dylan.mvcGame.worldEditor.subWindow.editSensor;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import me.dylan.mvcGame.game.gameObjects.robot.DistanceSensor;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;
import me.dylan.mvcGame.worldEditor.WorldEditorModel;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSensorController {
    private WorldEditorModel gameModel;
    private Sensor sensor;
    private int sensorIndex;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Slider YPos;

    @FXML
    private Slider Xpos;

    @FXML
    private Accordion TypeSelector;

    @FXML
    private TitledPane TypeDistance;

    @FXML
    private TextField Name;

    @FXML
    private Slider DistanceRotation;

    @FXML
    void initialize() {
        TypeDistance.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if(sensor == null) return;
            if(newValue){
                sensor = new DistanceSensor(sensor.getMapModel(),
                        (float)Xpos.getValue() / 64.0f,
                        (float)YPos.getValue() / 64.0f,
                        (float)(DistanceRotation.getValue() * 2 * Math.PI / 64),
                        Name.getText());

            }else if(TypeSelector.getExpandedPane() == null){
                TypeSelector.setExpandedPane(TypeDistance);
            }
        });

        Xpos.valueProperty().addListener((observable, oldValue, newValue) -> {
            sensor.setX(newValue.floatValue() / 64.0f);
        });
        YPos.valueProperty().addListener((observable, NoldValue, newValue) -> {
            sensor.setY(newValue.floatValue() / 64.0f);
        });
        Name.textProperty().addListener((observable, oldValue, newValue) -> {
            sensor.setName(0, newValue);
        });

        DistanceRotation.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if(sensor instanceof DistanceSensor)
                ((DistanceSensor) sensor).setRotation((float) (newValue.floatValue() * 2 * Math.PI / 64));
        });
    }

    @FXML
    void BackClick(ActionEvent event) {
        gameModel.setEditingSensor(false);
    }

    public void gameTick() {
    }

    public void reinit(int sensorIndex){
        sensor = gameModel.getRobot().getSensors()[sensorIndex];
        this.sensorIndex = sensorIndex;

        Xpos.setValue(sensor.getX() * 64);
        YPos.setValue(sensor.getY() * 64);
        Name.setText(sensor.getNames()[0]);

        switch (sensor.getType()){
            case 1:
                TypeSelector.setExpandedPane(TypeDistance);
        }
    }

    public void setGameModel(WorldEditorModel gameModel) {
        this.gameModel = gameModel;
    }
}
