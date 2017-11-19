package me.dylan.mvcGame.main;

import me.dylan.mvcGame.state.StateHandler;
import org.lwjgl.glfw.GLFW;

public class MainGameThread implements Runnable{
    private boolean running = true;

    final private long WANTED_UPS = 1_000_000_000 / 50;
    private double ups = 0.0;
    private double fps = 0.0;

    @Override
    public void run() {
        init();
        long tickTimer = System.nanoTime();
        long frameTimer = System.nanoTime();
        while(running){
            long diffTick = (System.nanoTime() - tickTimer);
            if(diffTick >= WANTED_UPS) {
                ups = (1_000_000_000.0 / diffTick);
                tickTimer += WANTED_UPS;
                update();
            }
            long diffFrame = (System.nanoTime() - frameTimer);
            fps = (1_000_000_000.0 / diffFrame);
            frameTimer += diffFrame;
            render();
        }
        deInit();
    }

    private MainController mainController;
    private MainViewer mainViewer;
    private StateHandler stateHandler;

    private void init(){
        mainViewer = new MainViewer("shader");
        stateHandler = new StateHandler(mainViewer, this);
        mainController = new MainController(mainViewer.getWindow(), stateHandler);
    }

    private void update(){
        mainController.update();
        mainViewer.update();
        stateHandler.update();
        if(GLFW.glfwWindowShouldClose(mainViewer.getWindow()))
            stop();
    }

    private void render(){
        mainViewer.startRender();
        stateHandler.render(mainViewer);
        mainViewer.endRender();
    }

    private void deInit(){
        stateHandler.deInit();
        stateHandler = null;
        mainController.deInit();
        mainController = null;
        mainViewer.deInit();
        mainViewer = null;
    }

    public void stop(){
        running = false;
    }
}
