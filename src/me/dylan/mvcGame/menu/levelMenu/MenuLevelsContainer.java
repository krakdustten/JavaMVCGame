package me.dylan.mvcGame.menu.levelMenu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.dylan.mvcGame.main.MainFXContainer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * The container for the window that holds the level chouser for the main game.
 *
 * @author Dylan Gybels
 */
public class MenuLevelsContainer{
    private MenuLevelsController menuLevelsController;
    private MenuLevelsModel model;

    /**
     * Create a new container for the levels menu.
     * @param model The levels model.
     */
    public MenuLevelsContainer(MenuLevelsModel model){
        this.model = model;
        MainFXContainer container = model.getMainModel().getFxContainer();
        container.setSize(800, 600);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuLevelsView.fxml"));
            Parent root = loader.load();

            this.menuLevelsController = loader.getController();
            Scene scene = new Scene(root, 800, 600);
            container.setScene(scene);

            menuLevelsController.setModel(model);
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
     * Update the controller.
     */
    public void update(){
        if(menuLevelsController != null) menuLevelsController.gameTick();
    }

    /**
     * Clear all vars.
     */
    public void distroy() {
        model.getMainModel().getFxContainer().setVisible(false);
        menuLevelsController = null;
    }
}
