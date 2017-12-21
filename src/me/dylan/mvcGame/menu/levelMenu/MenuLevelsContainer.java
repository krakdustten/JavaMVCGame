package me.dylan.mvcGame.menu.levelMenu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.dylan.mvcGame.game.controllers.CodeIDEController;
import me.dylan.mvcGame.main.MainFXContainer;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

//TODO javadoc
public class MenuLevelsContainer{
    private MenuLevelsController menuLevelsController;
    private MenuLevelsModel model;

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

            menuLevelsController.setGameModel(model);
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
        if(menuLevelsController != null) menuLevelsController.gameTick();
    }

    public void distroy() {
        model.getMainModel().getFxContainer().setVisible(false);
        menuLevelsController = null;
    }
}
