package me.dylan.mvcGame.game.gameViewers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.dylan.mvcGame.game.gameObjects.GameModel;
import me.dylan.mvcGame.game.controllers.CodeIDEController;
import me.dylan.mvcGame.main.MainFXContainer;

import java.io.IOException;

public class CodeIDEContainer{
    private CodeIDEController codeIDEController;
    private GameModel model;

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

        container.setVisible(true);
        /*frame = new JFrame("Code editor");//we have to use jframe because stage has to be run from the main thread
        fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        Platform.runLater(() -> {
            try {

                fxPanel.setScene(scene);
            } catch (IOException e) {
            e.printStackTrace();
        }
        });*/
    }

    public void update(){
        if(codeIDEController != null) codeIDEController.gameTick();
    }

    public void distroy() {
        model.getMainModel().getFxContainer().setVisible(false);
        codeIDEController = null;
    }
}
