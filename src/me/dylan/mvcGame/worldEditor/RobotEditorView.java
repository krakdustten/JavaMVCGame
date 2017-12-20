package me.dylan.mvcGame.worldEditor;

import me.dylan.mvcGame.game.gameObjects.robot.Sensor;

public class RobotEditorView {
    private WorldEditorModel model;

    public RobotEditorView(WorldEditorModel model) {
        this.model = model;
    }

    public void update() {
        boolean change = false;
        if(model.getRobot().getChanged()) change = true;
        for(Sensor s : model.getRobot().getSensors()) if(s.getChanged()) change = true;
        if(!change)return;


        //STUFF



        model.getRobot().setChange(false);
        for(Sensor s : model.getRobot().getSensors()) s.setChanged(false);
    }

    public void render() {
    }

    public void distroy() {
    }
}
