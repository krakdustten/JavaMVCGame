package me.dylan.mvcGame.main;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.awt.event.WindowAdapter;

/**
 * This class is a container to hold the javaFX panel and frame.
 * This solves the problem we had that we couldn't reopen a window that was closed.
 *
 * @author Dylan Gybels
 */
public class MainFXContainer {
    private JFrame frame;
    private JFXPanel fxPanel;
    private boolean visible;

    /**
     * Create a new main FX container.
     */
    public MainFXContainer(){
        frame = new JFrame("");//we have to use jframe because stage has to be run from the main thread
        fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setScene(new Scene(new StackPane(), 300, 300));//set scene so this thread doesn't wait for one
    }

    /**
     * Distroy the window.
     */
    public void distroy(){
        frame.dispose();
        frame = null;
        fxPanel = null;
    }

    /*SETTERS*/
    /**
     * Set a scene to the fx window.
     *
     * @param scene The scene to set to the fx window.
     */
    public void setScene(Scene scene){
        Platform.runLater(() -> fxPanel.setScene(scene));
    }

    /**
     * Set the size of the window.
     *
     * @param width The width of the window.
     * @param height The height of the window.
     */
    public void setSize(int width, int height){
        frame.setSize(width, height);
    }

    /**
     * Set the visibility of the window.
     *
     * @param visible Should the window be visible.
     */
    public void setVisible(boolean visible){
        frame.setVisible(visible);
        this.visible = visible;
    }

    /**
     * Set the focus on this window.
     */
    public void setFocus(){
        frame.setAlwaysOnTop(true);
        frame.setAlwaysOnTop(false);
    }

    /**
     * Set the close action on this window.
     *
     * @param action The action to do when the window closes.
     */
    public void setCloseAction(WindowAdapter action){
        frame.addWindowListener(action);
    }

    /*GETTERS*/
    /**
     * Get the visibility of the window.
     * @return Is the window visible.
     */
    public boolean getVisible() {
        return visible;
    }
}
