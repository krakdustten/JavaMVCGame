package me.dylan.mvcGame.menu.levelMenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import me.dylan.mvcGame.other.ResourceHandling;

public class MenuLevelsController {
    private MenuLevelsModel model;

    @FXML
    private TextArea LevelFileInfo;

    @FXML
    private ListView<String> LoadUserMapList;

    @FXML
    private Button PlayLevel;

    @FXML
    private ListView<String> LoadDefaultMapList;

    @FXML
    private Button OpenFile;

    @FXML
    private ListView<String> NewDefaultMapList;

    @FXML
    private ListView<String> NewUserMapList;

    @FXML
    void openFileClicked(ActionEvent event) {

    }

    @FXML
    void playClicked(ActionEvent event) {

    }

    @FXML
    void initialize() {
        NewDefaultMapList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> newDefaultMapMouseClick(oldValue, newValue));
        NewUserMapList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> newUserMapMouseClick(oldValue, newValue));
        LoadDefaultMapList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> loadDefaultMapMouseClick(oldValue, newValue));
        LoadUserMapList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> loadUserMapMouseClick(oldValue, newValue));
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

    private void loadUserMapMouseClick(String oldValue, String newValue) {
        if(newValue == null) return;
        model.getMainModel().setGameFileToLoad("saves/usermaps/" + newValue + ".savd");
        model.setMapSelected(true);
    }

    private void loadDefaultMapMouseClick(String oldValue, String newValue) {
        if(newValue == null) return;
        model.getMainModel().setGameFileToLoad("saves/maps/" + newValue + ".savd");
        model.setMapSelected(true);
    }

    public void setGameModel(MenuLevelsModel model) {
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

    public void gameTick() {

    }
}

