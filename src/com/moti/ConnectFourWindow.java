package com.moti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFourWindow extends JFrame {

    private final static int ROWS = 5;
    private final static int COLS = 7;
    final static int WINDOW_WIDTH = Square.SQUARE_LENGTH * COLS;
    final static int WINDOW_HEIGHT = Square.SQUARE_LENGTH * (ROWS + 1) + ButtonsPanel.PANEL_HEIGHT;
    private final int CLEAR_BUTTON_COL = 3;
    private Board _board_panel;
    private ButtonsPanel _buttons_panel;
    private JButton _clear_button;

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
        _clear_button = new JButton("clear");
        _clear_button.setBackground(Color.WHITE);
        _clear_button.setForeground(Color.BLACK);

        _clear_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _board_panel.clear_board();

                repaint();
            }
        });

        // Initialize panels
        _board_panel = new Board(ROWS, COLS);
        _buttons_panel = new ButtonsPanel(COLS);

        // Add clear button in the middle of the buttons panel
        _buttons_panel.add_in_panel(CLEAR_BUTTON_COL, _clear_button, BorderLayout.CENTER);

        // Set frame layout
        setLayout(new BorderLayout());

        // Add panels to frame
        add(_board_panel, BorderLayout.CENTER);
        add(_buttons_panel, BorderLayout.SOUTH);
    }
}
