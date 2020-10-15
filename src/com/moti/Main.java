package com.moti;

/**
 * The main class of the program
 */
public class Main {

    /**
     * The entry point of the program
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Create the game window
        ConnectFourWindow MainWindow = new ConnectFourWindow();

        // Show the window
        MainWindow.setVisible(true);
    }
}