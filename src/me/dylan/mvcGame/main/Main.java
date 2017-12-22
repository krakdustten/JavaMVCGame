package me.dylan.mvcGame.main;

/**
 * The main class.
 * Everything starts here!
 *
 * @author Dylan Gybels
 */
public class Main {

    /**
     * The main method. (Starts the program)
     *
     * @param args Arguments given on the command line.
     */
    public static void main(String[] args) {
        MainGameThread game = new MainGameThread();
        Thread thread = new Thread(game);
        thread.start();
    }
}
