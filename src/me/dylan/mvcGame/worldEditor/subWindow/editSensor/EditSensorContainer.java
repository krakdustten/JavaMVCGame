package me.dylan.mvcGame.worldEditor.subWindow.editSensor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.dylan.mvcGame.main.MainFXContainer;
import me.dylan.mvcGame.worldEditor.WorldEditorModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The container for the sensor editor.
 *
 * @author Dylan Gybels
 */
public class EditSensorContainer {
    private EditSensorController controller;
    private WorldEditorModel model;
    private Scene scene;

    /**
     * Create a new sensor editor container.
     *
     * @param model The world editor model.
     */
    public EditSensorContainer(WorldEditorModel model){
        this.model = model;
        MainFXContainer container = model.getMainModel().getFxContainer();
        container.setSize(800, 600);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditSensorView.fxml"));
            Parent root = loader.load();

            this.controller = loader.getController();
            scene = new Scene(root, 800, 600);
            container.setScene(scene);

            controller.setEditorModel(model);
        } catch (IOException e) { e.printStackTrace(); }

        container.setCloseAction(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                model.setEditingSensor(false);
            }
        });

        container.setVisible(true);
        container.setFocus();
    }

    /**
     * Update the sensor editor controller.
     */
    public void update() {
        if(controller != null) controller.gameTick();
    }

    /**
     * Set the current scene to this scene.
     */
    public void setSceneToThis(){
        controller.reinit(model.getEditingSensorIndex());
        model.getMainModel().getFxContainer().setScene(scene);
    }

    /**
     * Distory this object.
     */
    public void distroy() {
        model.getMainModel().getFxContainer().setVisible(false);
        controller = null;
    }
}
