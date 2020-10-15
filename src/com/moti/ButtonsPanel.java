package com.moti;

import javax.swing.*;
import java.awt.*;

/**
 * The panel of all the buttons that not
 * related to the game
 */
public class ButtonsPanel extends JPanel {

    final static int PANEL_HEIGHT = 50;
    private JPanel[] _panelHolders;

    /**
     * Initialize buttons panel,
     * add some panels to the panel
     * @param cols number of panels to add
     */
    ButtonsPanel(int cols) {
        // Set panel preferences
        setLayout(new GridLayout(1, cols));
        setPreferredSize(new Dimension(ConnectFourWindow.WINDOW_WIDTH, PANEL_HEIGHT));

        // Add panels to panel
        _panelHolders = new JPanel[cols];
        for (int i = 0; i < cols; ++i) {
            _panelHolders[i] = new JPanel();
            _panelHolders[i].setBackground(Color.WHITE);
            _panelHolders[i].setLayout(new BorderLayout());
            add(_panelHolders[i]);
        }
    }

    /**
     * Add component to specific panel
     * @param panel_index the index of the panel
     * @param component the component to add
     * @param constraints the place to add the component
     * @throws IllegalArgumentException in case of invalid index, this exception thrown
     */
    public void addInPanel(int panel_index, Component component, Object constraints) throws IllegalArgumentException {
        if (_panelHolders.length <= panel_index) {
            throw new IllegalArgumentException("ButtonsPanel: Invalid panel index");
        }

        // Add component to panel
        _panelHolders[panel_index].add(component, constraints);
    }
}
