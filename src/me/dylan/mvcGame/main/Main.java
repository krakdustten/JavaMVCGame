package me.dylan.mvcGame.main;

public class Main {
    public static void main(String[] args) {
        MainGameThread game = new MainGameThread();
        Thread thread = new Thread(game);

        thread.start();
    }
}
