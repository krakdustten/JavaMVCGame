package me.dylan.mvcGame.menu.worldEditorPicker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import me.dylan.mvcGame.other.ResourceHandling;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuWorldEditorController {
    private MenuWorldEditorModel model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> LoadMapList;

    @FXML
    private TextField NewName;

    @FXML
    void NewMap(ActionEvent event) {
        model.getMainModel().setGameFileToLoad("usermaps/" + NewName.getText() + ".mapd");
        model.setMapSelected(true);
        //TODO delete function
    }

    @FXML
    void initialize() {
        LoadMapList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> LoadMapListClick(oldValue, newValue));

        String startPath = ResourceHandling.GetExecutionPath();
        populateListWithFiles(startPath + "/usermaps/", ".mapd", LoadMapList);
    }

    private void LoadMapListClick(String oldValue, String newValue) {
        if(newValue == null)return;
        model.getMainModel().setGameFileToLoad("usermaps/" + newValue + ".mapd");
        model.setMapSelected(true);
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

    public void setGameModel(MenuWorldEditorModel model) {
        this.model = model;
    }

    public void gameTick() {

    }
}
