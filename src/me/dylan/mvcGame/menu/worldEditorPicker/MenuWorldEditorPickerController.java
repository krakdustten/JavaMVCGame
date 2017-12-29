package me.dylan.mvcGame.menu.worldEditorPicker;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import me.dylan.mvcGame.other.ResourceHandling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The controller for the world picker for the world editor.
 *
 * @author Dylan Gybels
 */
public class MenuWorldEditorPickerController {
    private MenuWorldEditorPickerModel model;
    private List<String> selected = new ArrayList<>();

    @FXML
    private ListView<String> LoadMapList;

    @FXML
    private TextField NewName;

    /**
     * The new map button was clicked.
     * @param event The event from the button.
     */
    @FXML
    void NewMap(ActionEvent event) {
        model.getMainModel().setGameFileToLoad("usermaps/" + NewName.getText() + ".mapd");
        model.setMapSelected(true);
    }

    /**
     * The delete button was clicked.
     * @param event The event from the button.
     */
    @FXML
    void DeleteClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete files?");
        alert.setHeaderText("Delete files?");
        alert.setContentText("Do you really want to delete these files.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() != ButtonType.OK) return;

        String startPath = ResourceHandling.GetExecutionPath() + "/usermaps/";
        for(String s : selected){
            File file = new File(startPath + s + ".mapd");
            file.delete();
        }
        LoadMapList.getItems().clear();
        selected.clear();
        populateListWithFiles(startPath, ".mapd", LoadMapList);
    }

    /**
     * Edit button was clicked.
     * @param event The event from the button.
     */
    @FXML
    void EditClick(ActionEvent event) {
        if(selected.size() != 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Non or to many options found.");
            alert.setHeaderText("Non or to many options found.");
            alert.setContentText("You have to select only one of the options.");
            alert.show();
            return;
        }

        model.getMainModel().setGameFileToLoad("usermaps/" + selected.get(0) + ".mapd");
        model.setMapSelected(true);
    }

    /**
     * Rename button was clicked.
     * @param event The event from the button.
     */
    @FXML
    void RenameClick(ActionEvent event) {
        String startPath = ResourceHandling.GetExecutionPath() + "/usermaps/";
        if(selected.size() == 1){
            (new File(startPath + selected.get(0) + ".mapd"))
                    .renameTo(new File(startPath + NewName.getText() + ".mapd"));
        }else {
            for (int i = 0; i < selected.size(); i++) {
                (new File(startPath + selected.get(i) + ".mapd"))
                        .renameTo(new File(startPath + NewName.getText() + "_" + (i + 1) + ".mapd"));
            }
        }
        LoadMapList.getItems().clear();
        selected.clear();
        populateListWithFiles(startPath, ".mapd", LoadMapList);
    }

    /**
     * Initialize this controller.
     */
    @FXML
    void initialize() {
        LoadMapList.setCellFactory(CheckBoxListCell.forListView(item -> {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) selected.add(item);
                else selected.remove(item);
            });
            return observable ;
        }));
    }

    private void populateListWithFiles(String folder, String end, ListView<String> listView){
        File file = new File(folder);
        if(!file.exists())file.mkdirs();

        for(File f : file.listFiles()){
            String s  = f.getName();
            if(s.endsWith(end)){
                listView.getItems().add(s.substring(0, s.length() - end.length()));
            }
        }
    }

    /**
     * Set the model of this controller.
     * @param model The new model.
     */
    public void setModel(MenuWorldEditorPickerModel model) {
        this.model = model;

        String startPath = ResourceHandling.GetExecutionPath();
        populateListWithFiles(startPath + "/usermaps/", ".mapd", LoadMapList);
    }

    /**
     * Give this controller an update.
     */
    public void gameTick() {

    }
}
