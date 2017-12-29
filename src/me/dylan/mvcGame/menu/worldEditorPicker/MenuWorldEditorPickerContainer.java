package me.dylan.mvcGame.menu.worldEditorPicker;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.dylan.mvcGame.main.MainFXContainer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The container for the world picker for the world editor.
 *
 * @author Dylan Gybels
 */
public class MenuWorldEditorPickerContainer {
    private MenuWorldEditorPickerController controller;
    private MenuWorldEditorPickerModel model;

    /**
     * Create a new world editor picker container.
     *
     * @param model The world editor picker model.
     */
    public MenuWorldEditorPickerContainer(MenuWorldEditorPickerModel model){
        this.model = model;
        MainFXContainer container = model.getMainModel().getFxContainer();
        container.setSize(800, 600);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuWorldEditorView.fxml"));
            Parent root = loader.load();

            this.controller = loader.getController();
            Scene scene = new Scene(root, 800, 600);
            container.setScene(scene);

            controller.setModel(model);
        } catch (IOException e) { e.printStackTrace(); }

        container.setCloseAction(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                model.setWindowClosing(true);
            }
        });

        container.setVisible(true);
        container.setFocus();
    }

    /**
     * Update the container.
     */
    public void update(){
        if(controller != null) controller.gameTick();
    }

    /**
     * Distroy the container.
     */
    public void distroy() {
        model.getMainModel().getFxContainer().setVisible(false);
        controller = null;
    }
}
