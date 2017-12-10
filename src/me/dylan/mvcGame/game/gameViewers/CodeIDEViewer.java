package me.dylan.mvcGame.game.gameViewers;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.dylan.mvcGame.game.GameModel;
import me.dylan.mvcGame.game.controllers.CodeIDEController;

import javax.swing.*;
import java.io.IOException;

public class CodeIDEViewer{
    private JFrame frame;
    private JFXPanel fxPanel;

    public CodeIDEViewer(GameModel model){
        frame = new JFrame("Code editor");//we have to use jframe because stage has to be run from the main thread
        fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CodeIDEView.fxml"));
                Parent root = loader.load();
                ((CodeIDEController)loader.getController()).setGameModel(model);

                Scene scene = new Scene(root, frame.getWidth(), frame.getHeight());
                fxPanel.setScene(scene);
            } catch (IOException e) {
            e.printStackTrace();
        }
        });
    }

    public void distroy() {
        frame.dispose();
        frame = null;
        fxPanel = null;
    }
}
