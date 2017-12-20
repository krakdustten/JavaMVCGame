package me.dylan.mvcGame.worldEditor.subWindow;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import me.dylan.mvcGame.game.gameObjects.Tiles;
import me.dylan.mvcGame.game.gameObjects.robot.DistanceSensor;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;
import me.dylan.mvcGame.worldEditor.WorldEditorModel;

import java.net.URL;
import java.util.ResourceBundle;

public class WorldEditorSubController {
    private WorldEditorModel model;

    private final ObservableList<SensorProperty> data = FXCollections.observableArrayList();

    @FXML
    private TabPane TabPane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> TilesSelector;

    @FXML
    private ListView<String> SensorNames;

    @FXML
    private TextArea Code;

    @FXML
    private TableColumn<SensorProperty, String> SensorType;

    @FXML
    private TableColumn<SensorProperty, String> XPosition;

    @FXML
    private TableColumn<SensorProperty, String> YPosition;

    @FXML
    private TableColumn<SensorProperty, String> SensorName;

    @FXML
    private TableColumn<SensorProperty, String> CulomEdit;

    @FXML
    private TableView<SensorProperty> Table;

    @FXML
    void initialize() {
        TilesSelector.getItems().addAll("Floor", "Wall", "Start", "End");
        TilesSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> TilesSelectorClick(oldValue, newValue));

        Code.textProperty().addListener((this::codeTextChanged));

        TabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(model == null) return;
            model.setEditingRobot(newValue.intValue() == 1);
        });
    }

    private void initTable() {
        SensorName.setCellFactory( new Callback<TableColumn<SensorProperty, String>, TableCell<SensorProperty, String>>() {
            @Override
            public TableCell call(final TableColumn<SensorProperty, String> param) {
                final TableCell<SensorProperty, String> cell = new TableCell<SensorProperty, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(null);
                        if (empty) {
                            setText(null);
                        } else {
                            String[] names = getTableView().getItems().get(getIndex()).getName();
                            StringBuilder sb = new StringBuilder();
                            for(int i = 0; i < names.length; i++){
                                sb.append(names[i]);
                                if(i != names.length - 1)
                                    sb.append(";\n");
                            }
                            setText(sb.toString());
                        }
                    }
                };
                return cell;
            }
        });
        SensorType.setCellValueFactory(new PropertyValueFactory<>("type"));
        XPosition.setCellFactory( new Callback<TableColumn<SensorProperty, String>, TableCell<SensorProperty, String>>() {
            @Override
            public TableCell call(final TableColumn<SensorProperty, String> param) {
                final TableCell<SensorProperty, String> cell = new TableCell<SensorProperty, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(null);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(getTableView().getItems().get(getIndex()).getxPos() + "");
                        }
                    }
                };
                return cell;
            }
        });
        YPosition.setCellFactory( new Callback<TableColumn<SensorProperty, String>, TableCell<SensorProperty, String>>() {
            @Override
            public TableCell call(final TableColumn<SensorProperty, String> param) {
                final TableCell<SensorProperty, String> cell = new TableCell<SensorProperty, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(null);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(getTableView().getItems().get(getIndex()).getyPos() + "");
                        }
                    }
                };
                return cell;
            }
        });
        CulomEdit.setCellFactory( new Callback<TableColumn<SensorProperty, String>, TableCell<SensorProperty, String>>() {
            @Override
            public TableCell call(final TableColumn<SensorProperty, String> param) {
                final TableCell<SensorProperty, String> cell = new TableCell<SensorProperty, String>() {
                    final Button btn = new Button("Edit");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(null);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setOnAction(event -> {
                                tableEditClick(getIndex(), getTableView().getItems().get(getIndex()));
                            });
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });
        Table.setItems(data);
    }

    private void populateTableData(){
        data.clear();
        SensorNames.getItems().clear();
        for(Sensor sens : model.getRobot().getSensors()){
            data.add(new SensorProperty(sens));

            for(String s : sens.getNames())
                SensorNames.getItems().add(s);
        }
    }

    private void tableEditClick(int index, SensorProperty sensorProperty) {
        model.setEditingSenserIndex(index);
        model.setEditingSensor(true);
    }

    @FXML
    void NewSensor(ActionEvent event) {
        model.getRobot().addSensor(new DistanceSensor(model, 0, 0, 0, "DEFAULT " + (int)(Math.random() * 10000)));
        populateTableData();
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
        initTable();
        populateTableData();

    }

    public void gameTick() {
    }

    public void reInitialize() {
        populateTableData();
    }
}
