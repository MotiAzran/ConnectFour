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
        ConnectFourWindow mainWindow = new ConnectFourWindow();

        // Show the window
        mainWindow.setVisible(true);
    }
}