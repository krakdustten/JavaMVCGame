package me.dylan.mvcGame.worldEditor.subWindow;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that converts a sensor to an item for the list.
 *
 * @author Dylan Gybels
 */
public class SensorProperty {
    private final SimpleFloatProperty xPos;
    private final SimpleFloatProperty yPos;
    private final SimpleStringProperty type;
    private final SimpleListProperty<String> names;

    /**
     * Create a new sensor property from a given sensor.
     * @param sensor The sensor to get the data from.
     */
    public SensorProperty(Sensor sensor){
        xPos = new SimpleFloatProperty(sensor.getX());
        yPos = new SimpleFloatProperty(sensor.getY());
        type = new SimpleStringProperty(sensor.getTypeName());

        List<String> nameList = new ArrayList<>(Arrays.asList(sensor.getNames()));

        names = new SimpleListProperty<>(FXCollections.observableArrayList(nameList));
    }

    /**
     * Get the x position of the sensor this property presents.
     * @return The x position of the sensor this property presents.
     */
    public float getxPos() { return xPos.get(); }
    /**
     * Get the y position of the sensor this property presents.
     * @return The y position of the sensor this property presents.
     */
    public float getyPos() { return yPos.get(); }

    /**
     * Get the type of the sensor this property presents.
     * @return The type of the sensor this property presents.
     */
    public String getType() { return type.get(); }

    /**
     * Get the name of the sensor this property presents.
     * @return The name of the sensor this property presents.
     */
    public String[] getName() { return names.get().toArray(new String[0]); }

    /**
     * Set the x position of the sensor this property presents.
     * The position of the sensor itself won't change.
     * @param xPos The new x position of the sensor this property presents.
     */
    public void setxPos(float xPos) { this.xPos.set(xPos); }

    /**
     * Set the y position of the sensor this property presents.
     * The position of the sensor itself won't change.
     * @param yPos The new y position of the sensor this property presents.
     */
    public void setyPos(float yPos) { this.yPos.set(yPos); }

    /**
     * Set the type of the sensor this property presents.
     * The type of the sensor itself won't change.
     * @param type The new type of the sensor this property presents.
     */
    public void setType(String type) { this.type.set(type); }

    /**
     * Set the name of the sensor this property presents.
     * The name of the sensor itself won't change.
     * @param name The new name of the sensor this property presents.
     */
    public void setName(ObservableList<String> name) { this.names.set(name); }
}
