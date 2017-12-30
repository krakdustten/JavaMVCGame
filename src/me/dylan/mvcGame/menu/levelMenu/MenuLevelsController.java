package me.dylan.mvcGame.menu.levelMenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import me.dylan.mvcGame.other.ResourceHandling;

/**
 * The controller for the level chooser menu.
 *
 * @author Dylan Gybels
 */
public class MenuLevelsController {
    private MenuLevelsModel model;
    private List<String> selectedDefault = new ArrayList<>();
    private List<String> selectedUser = new ArrayList<>();

    @FXML
    private ListView<String> LoadUserMapList;

    @FXML
    private ListView<String> LoadDefaultMapList;

    @FXML
    private ListView<String> NewDefaultMapList;

    @FXML
    private ListView<String> NewUserMapList;

    @FXML
    private TextField NewDefaultName;

    @FXML
    private TextField NewUserName;

    /**
     * Initialize this controller.
     */
    @FXML
    void initialize() {
        NewDefaultMapList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> newDefaultMapMouseClick(oldValue, newValue));
        NewUserMapList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> newUserMapMouseClick(oldValue, newValue));

        LoadDefaultMapList.setCellFactory(CheckBoxListCell.forListView(item -> {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) selectedDefault.add(item);
                else selectedDefault.remove(item);
            });
            return observable ;
        }));

        LoadUserMapList.setCellFactory(CheckBoxListCell.forListView(item -> {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> {
                if (isNowSelected) selectedUser.add(item);
                else selectedUser.remove(item);
            });
            return observable ;
        }));
    }

    /**
     * The play button on the first tab is clicked.
     * @param event The button event.
     */
    @FXML
    void PlayDefaultClicked(ActionEvent event) {
        if(selectedDefault.size() != 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Non or to many options found.");
            alert.setHeaderText("Non or to many options found.");
            alert.setContentText("You have to select only one of the options.");
            alert.show();
            return;
        }

        model.getMainModel().setGameFileToLoad("saves/maps/" + selectedDefault.get(0) + ".savd");
        model.setMapSelected(true);
    }

    /**
     * The delete button on the first tab is clicked.
     * @param event The button event.
     */
    @FXML
    void DeleteDefaultClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete files?");
        alert.setHeaderText("Delete files?");
        alert.setContentText("Do you really want to delete these files.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() != ButtonType.OK) return;

        String startPath = ResourceHandling.GetExecutionPath() + "/saves/maps/";
        for(String s : selectedDefault){
            File file = new File(startPath + s + ".savd");
            file.delete();
        }
        LoadDefaultMapList.getItems().clear();
        selectedDefault.clear();
        populateListWithFiles(startPath, ".savd", LoadDefaultMapList);
    }

    /**
     * The rename button on the first tab is clicked.
     * @param event The button event.
     */
    @FXML
    void RenameDefaultClick(ActionEvent event) {
        String startPath = ResourceHandling.GetExecutionPath() + "/saves/maps/";
        if(selectedDefault.size() == 1){
            (new File(startPath + selectedDefault.get(0) + ".savd"))
                    .renameTo(new File(startPath + NewDefaultName.getText() + ".savd"));
        }else {
            for (int i = 0; i < selectedDefault.size(); i++) {
                (new File(startPath + selectedDefault.get(i) + ".savd"))
                        .renameTo(new File(startPath + NewDefaultName.getText() + "_" + (i + 1) + ".savd"));
            }
        }
        LoadDefaultMapList.getItems().clear();
        selectedDefault.clear();
        populateListWithFiles(startPath, ".savd", LoadDefaultMapList);
    }

    /**
     * The play button on the second tab is clicked.
     * @param event The button event.
     */
    @FXML
    void PlayUserClicked(ActionEvent event) {
        if(selectedUser.size() != 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Non or to many options found.");
            alert.setHeaderText("Non or to many options found.");
            alert.setContentText("You have to select only one of the options.");
            alert.show();
            return;
        }

        model.getMainModel().setGameFileToLoad("saves/usermaps/" + selectedUser.get(0) + ".savd");
        model.setMapSelected(true);
    }

    /**
     * The delete button on the second tab is clicked.
     * @param event The button event.
     */
    @FXML
    void DeleteUserClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete files?");
        alert.setHeaderText("Delete files?");
        alert.setContentText("Do you really want to delete these files.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() != ButtonType.OK) return;

        String startPath = ResourceHandling.GetExecutionPath() + "/saves/usermaps/";
        for(String s : selectedUser){
            File file = new File(startPath + s + ".savd");
            file.delete();
        }
        LoadUserMapList.getItems().clear();
        selectedUser.clear();
        populateListWithFiles(startPath, ".savd", LoadUserMapList);
    }

    /**
     * The rename button on the second tab is clicked.
     * @param event The button event.
     */
    @FXML
    void RenameUserClick(ActionEvent event) {
        String startPath = ResourceHandling.GetExecutionPath() + "/saves/usermaps/";
        if(selectedUser.size() == 1){
            (new File(startPath + selectedUser.get(0) + ".savd"))
                    .renameTo(new File(startPath + NewUserName.getText() + ".savd"));
        }else {
            for (int i = 0; i < selectedUser.size(); i++) {
                (new File(startPath + selectedUser.get(i) + ".savd"))
                        .renameTo(new File(startPath + NewUserName.getText() + "_" + (i + 1) + ".savd"));
            }
        }
        LoadUserMapList.getItems().clear();
        selectedUser.clear();
        populateListWithFiles(startPath, ".savd", LoadUserMapList);
    }

    private void newDefaultMapMouseClick(String oldValue, String newValue) {
        if(newValue == null) return;
        model.getMainModel().setGameFileToLoad("maps/" + newValue + ".mapd");
        model.setMapSelected(true);
    }

    private void newUserMapMouseClick(String oldValue, String newValue) {
        if(newValue == null) return;
        model.getMainModel().setGameFileToLoad("usermaps/" + newValue + ".mapd");
        model.setMapSelected(true);
    }

    /**
     * Set the model of this controller.
     * @param model The new model.
     */
    public void setModel(MenuLevelsModel model) {
        this.model = model;

        populateNewDefaultMapList();
        populateOtherLists();
    }

    private void populateNewDefaultMapList(){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ResourceHandling.getFileOrResource("maps/maps.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                NewDefaultMapList.getItems().add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateOtherLists(){
        String startPath = ResourceHandling.GetExecutionPath();
        populateListWithFiles(startPath + "/usermaps/", ".mapd", NewUserMapList);
        populateListWithFiles(startPath + "/saves/usermaps/", ".savd", LoadUserMapList);
        populateListWithFiles(startPath + "/saves/maps/", ".savd", LoadDefaultMapList);
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
     * Give an update to this controller.
     */
    public void gameTick() {
    }
}

