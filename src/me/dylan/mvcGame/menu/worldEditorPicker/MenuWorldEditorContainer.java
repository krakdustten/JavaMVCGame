package me.dylan.mvcGame.menu.worldEditorPicker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.dylan.mvcGame.main.MainFXContainer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MenuWorldEditorContainer {
    private MenuWorldEditorController controller;
    private MenuWorldEditorModel model;

    public MenuWorldEditorContainer(MenuWorldEditorModel model){
        this.model = model;
        MainFXContainer container = model.getMainModel().getFxContainer();
        container.setSize(800, 600);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuWorldEditorView.fxml"));
            Parent root = loader.load();

            this.controller = loader.getController();
            Scene scene = new Scene(root, 800, 600);
            container.setScene(scene);

            controller.setGameModel(model);
        } catch (IOException e) { e.printStackTrace(); }

        container.setCloseACtion(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                model.setWindowClosing(true);
            }
        });

        container.setVisible(true);
        container.setFocus();
    }

    public void update(){
        if(controller != null) controller.gameTick();
    }

    public void distroy() {
        model.getMainModel().getFxContainer().setVisible(false);
        controller = null;
    }
}
