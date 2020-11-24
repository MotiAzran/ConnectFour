import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main window of the program
 * holds the board and all the buttons
 */
public class ConnectFourWindow extends JFrame {
    private final int ROWS = 5;
    private final int COLS = 7;
    private final int SQUARE_LENGTH = 100;
    private final int BUTTONS_HEIGHT = 100;
    private final int WINDOW_WIDTH = SQUARE_LENGTH * COLS;
    // +1 for the columns buttons
    private final int WINDOW_HEIGHT = SQUARE_LENGTH * (ROWS + 1) + BUTTONS_HEIGHT;
    private Board boardPanel;
    private ButtonsPanel buttonsPanel;
    private JButton clearButton;

    /**
     * Initialize the main window,
     * set it preferences and add the
     * game objects
     */
    public ConnectFourWindow() {
        // Set window title
        super("Connect Four");

        // Set window exit preferences
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set window size
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        // Set window location to the center of the screen
        setLocationRelativeTo(null);

        // Initialize clear button
        clearButton = new JButton("clear");
        clearButton.setBackground(Color.WHITE);
        clearButton.setForeground(Color.BLACK);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardPanel.clearBoard();

                repaint();
            }
        });

        boardPanel = new Board(ROWS, COLS, SQUARE_LENGTH);
        buttonsPanel = new ButtonsPanel(COLS, BUTTONS_HEIGHT, WINDOW_WIDTH);

        // Add clear button in the middle of the buttons panel
        buttonsPanel.addInPanel(Math.floorDiv(COLS, 2), clearButton, BorderLayout.CENTER);

        // Set frame layout
        setLayout(new BorderLayout());

        add(boardPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
