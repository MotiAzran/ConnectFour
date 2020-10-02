package com.moti;

import javax.swing.*;
import java.awt.*;

public class ButtonsPanel extends JPanel {

    final static int PANEL_HEIGHT = 50;
    private JPanel[] _panel_holders;

    ButtonsPanel(int cols) {
        // Set panel preferences
        setLayout(new GridLayout(1, cols));
        setPreferredSize(new Dimension(ConnectFourWindow.WINDOW_WIDTH, PANEL_HEIGHT));

        // Add panels to panel
        _panel_holders = new JPanel[cols];
        for (int i = 0; i < cols; ++i) {
            _panel_holders[i] = new JPanel();
            _panel_holders[i].setBackground(Color.WHITE);
            _panel_holders[i].setLayout(new BorderLayout());
            add(_panel_holders[i]);
        }
    }

    public void add_in_panel(int panel_index, Component c, Object constraints) throws IllegalArgumentException {
        if (_panel_holders.length <= panel_index) {
            throw new IllegalArgumentException("ButtonsPanel: Invalid panel index");
        }

        // Add component to panel
        _panel_holders[panel_index].add(c, constraints);
    }
}
