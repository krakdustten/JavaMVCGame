package me.dylan.mvcGame.main;

//TODO javadoc
public class Main {

    public static void main(String[] args) {
        MainGameThread game = new MainGameThread();
        Thread thread = new Thread(game);

        thread.start();
    }
}
