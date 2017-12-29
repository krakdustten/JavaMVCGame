package me.dylan.mvcGame.worldEditor.subWindow;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.dylan.mvcGame.main.MainFXContainer;
import me.dylan.mvcGame.worldEditor.WorldEditorModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The container that holds the extra window for the world editor.
 *
 * @author Dylan Gybels
 */
public class WorldEditorContainer {
    private WorldEditorSubController controller;
    private WorldEditorModel model;
    private Scene scene;

    /**
     * Create a new world editor container.
     * @param model The world editor model.
     */
    public WorldEditorContainer(WorldEditorModel model){
        this.model = model;
        MainFXContainer container = model.getMainModel().getFxContainer();
        container.setSize(800, 600);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorldEditorView.fxml"));
            Parent root = loader.load();

            this.controller = loader.getController();
            scene = new Scene(root, 800, 600);
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
     * Update the controller of the extra window.
     */
    public void update(){
        if(!model.getEditingSensor()) {
            if (controller != null) controller.gameTick();
        }
    }

    /**
     * Set the scene to this window.
     */
    public void setSceneToThis(){
        controller.reInitialize();
        model.getMainModel().getFxContainer().setScene(scene);
    }

    /**
     * Distroy this container and close the window.
     */
    public void distroy() {
        model.getMainModel().getFxContainer().setVisible(false);
        controller = null;
    }
}
