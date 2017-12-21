package me.dylan.mvcGame.worldEditor.subWindow;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import me.dylan.mvcGame.game.gameObjects.robot.Sensor;

import java.util.ArrayList;
import java.util.List;

//TODO javadoc
public class SensorProperty {
    private final SimpleFloatProperty xPos;
    private final SimpleFloatProperty yPos;
    private final SimpleStringProperty type;
    private final SimpleListProperty<String> names;


    public SensorProperty(Sensor sensor){
        xPos = new SimpleFloatProperty(sensor.getX());
        yPos = new SimpleFloatProperty(sensor.getY());
        type = new SimpleStringProperty(sensor.getTypeName());

        List<String> nameList = new ArrayList<>();
        for(String name : sensor.getNames()){
            nameList.add(name);
        }

        names = new SimpleListProperty<String>(FXCollections.observableArrayList(nameList));
    }

    public float getxPos() { return xPos.get(); }
    public float getyPos() { return yPos.get(); }
    public String getType() { return type.get(); }

    public String[] getName() { return names.get().toArray(new String[0]); }


    public void setxPos(float xPos) { this.xPos.set(xPos); }
    public void setyPos(float yPos) { this.yPos.set(yPos); }
    public void setType(String type) { this.type.set(type); }

    public void setName(ObservableList<String> name) { this.names.set(name); }
}
