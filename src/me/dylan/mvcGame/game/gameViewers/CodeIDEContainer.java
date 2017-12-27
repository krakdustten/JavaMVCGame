package me.dylan.mvcGame.game.gameViewers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.controllers.CodeIDEController;
import me.dylan.mvcGame.main.MainFXContainer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The conainer that starts the IDE view and controller.
 *
 * @author Dylan Gybels
 */
public class CodeIDEContainer{
    private CodeIDEController codeIDEController;
    private GameModel model;

    /**
     * Create a new IDE container.
     * @param model The game model.
     */
    public CodeIDEContainer(GameModel model){
        this.model = model;
        MainFXContainer container = model.getMainModel().getFxContainer();
        container.setSize(800, 600);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CodeIDEView.fxml"));
            Parent root = loader.load();

            this.codeIDEController = loader.getController();
            Scene scene = new Scene(root, 800, 600);
            container.setScene(scene);

            codeIDEController.setGameModel(model);
        } catch (IOException e) { e.printStackTrace(); }

        container.setCloseAction(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                model.setWindowClosing(true);
            }
        });
        container.setVisible(true);
    }

    /**
     * give a tick to the IDE controller.
     */
    public void update(){
        if(codeIDEController != null) codeIDEController.gameTick();
    }

    /**
     * Clear all of the variables and close the window.
     */
    public void distroy() {
        model.getMainModel().getFxContainer().setVisible(false);
        codeIDEController = null;
    }
}
