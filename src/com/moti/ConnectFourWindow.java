package com.moti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The main window of the program
 * holds the board and all the buttons
 */
public class ConnectFourWindow extends JFrame {
    private final static int ROWS = 5;
    private final static int COLS = 7;
    final static int WINDOW_WIDTH = Square.SQUARE_LENGTH * COLS;
    final static int WINDOW_HEIGHT = Square.SQUARE_LENGTH * (ROWS + 1) + ButtonsPanel.PANEL_HEIGHT;
    private final int CLEAR_BUTTON_COL = 3;
    private Board _boardPanel;
    private ButtonsPanel _buttonsPanel;
    private JButton _clearButton;

    /**
     * Initialize the main window,
     * set it preferences and add the
     * game objects
     */
    ConnectFourWindow() {
        // Set window title
        super("Connect four");

        // Set window exit preferences
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set window size
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        // Set window location to the center of the screen
        setLocationRelativeTo(null);

        // Initialize clear button
        _clearButton = new JButton("clear");
        _clearButton.setBackground(Color.WHITE);
        _clearButton.setForeground(Color.BLACK);

        _clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _boardPanel.clearBoard();

                repaint();
            }
        });

        // Initialize panels
        _boardPanel = new Board(ROWS, COLS);
        _buttonsPanel = new ButtonsPanel(COLS);

        // Add clear button in the middle of the buttons panel
        _buttonsPanel.addInPanel(CLEAR_BUTTON_COL, _clearButton, BorderLayout.CENTER);

        // Set frame layout
        setLayout(new BorderLayout());

        // Add panels to frame
        add(_boardPanel, BorderLayout.CENTER);
        add(_buttonsPanel, BorderLayout.SOUTH);
    }
}
