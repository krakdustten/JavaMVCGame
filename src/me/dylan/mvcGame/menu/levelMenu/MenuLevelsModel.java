package me.dylan.mvcGame.menu.levelMenu;

import me.dylan.mvcGame.main.MainModel;

/**
 * The model for the level chooser.
 *
 * @author Dylan Gybels
 */
public class MenuLevelsModel {
    private MainModel mainModel;

    private boolean windowClosing = false;
    private boolean mapSelected = false;

    /**
     * Create a new level model.
     * @param mainModel The main model.
     */
    public MenuLevelsModel(MainModel mainModel){
        this.mainModel = mainModel;
    }

    /**
     * Get the main model.
     * @return The main model.
     */
    public MainModel getMainModel() { return mainModel; }

    /**
     * Get if the window is closing.
     * @return True, if the window is closing.
     */
    public Boolean getWindowClosing() { return windowClosing; }

    /**
     * Get if there is a selected map.
     * @return True, if there is a selected map.
     */
    public boolean getMapSelected() { return mapSelected; }

    /**
     * Set the main model.
     * @param mainModel The new main model.
     */
    public void setMainModel(MainModel mainModel) { this.mainModel = mainModel; }

    /**
     * Set that the window is closing or not.
     * @param windowClosing Is the window closing.
     */
    public void setWindowClosing(boolean windowClosing) { this.windowClosing = windowClosing; }

    /**
     * Set if there is a map selected or not.
     * @param mapSelected Is there a map selected.
     */
    public void setMapSelected(boolean mapSelected) { this.mapSelected = mapSelected; }
}
