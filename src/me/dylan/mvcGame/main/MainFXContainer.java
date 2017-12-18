package me.dylan.mvcGame.main;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;

import javax.swing.*;
import java.awt.event.WindowAdapter;

public class MainFXContainer {
    private JFrame frame;
    private JFXPanel fxPanel;

    public MainFXContainer(){
        frame = new JFrame("");//we have to use jframe because stage has to be run from the main thread
        fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public void setScene(Scene scene){
        Platform.runLater(() -> {
            fxPanel.setScene(scene);
        });
    }

    public void setSize(int width, int height){
        frame.setSize(width, height);
    }

    public void setVisible(boolean visible){
        frame.setVisible(visible);
    }

    public void distroy(){
        frame.dispose();
        frame = null;
        fxPanel = null;
    }

    public void setFocus(){
        frame.setAlwaysOnTop(true);
        frame.setAlwaysOnTop(false);
    }

    public void setCloseACtion(WindowAdapter action){
        frame.addWindowListener(action);
    }
}
