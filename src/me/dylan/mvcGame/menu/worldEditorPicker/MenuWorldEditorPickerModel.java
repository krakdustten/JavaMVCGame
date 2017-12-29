package me.dylan.mvcGame.menu.worldEditorPicker;

import me.dylan.mvcGame.main.MainModel;

/**
 * The model
 *
 * @author Dylan Gybels
 */
public class MenuWorldEditorPickerModel {
    private MainModel mainModel;

    private boolean windowClosing;
    private boolean mapSelected;

    /**
     * Create a new world editor picker model.
     *
     * @param mainModel The main model.
     */
    public MenuWorldEditorPickerModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    /*GETTERS*/

    /**
     * Get the main model.
     * @return The main model.
     */
    public MainModel getMainModel() { return mainModel; }

    /**
     * Get if the extra window is closing.
     * @return If the extra window is closing.
     */
    public boolean getWindowClosing() { return windowClosing; }

    /**
     * Get if a map is selected.
     * @return If a map is selected.
     */
    public boolean getMapSelected() { return mapSelected; }

    /*SETTERS*/

    /**
     * Set if the window is closing.
     * @param windowClosing Is the window closing.
     */
    public void setWindowClosing(boolean windowClosing) { this.windowClosing = windowClosing; }

    /**
     * Set if there is a map selected.
     * @param mapSelected Is there a map selected.
     */
    public void setMapSelected(boolean mapSelected) { this.mapSelected = mapSelected; }
}
