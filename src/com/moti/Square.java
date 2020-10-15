package com.moti;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a board square, where the disc
 * will enter
 */
public class Square extends JPanel {

    final static int SQUARE_LENGTH = 100;
    private Color _discColor;

    /**
     * Initialize square
     * initialize size, and color
     */
    Square() {
        // Set square size
        setPreferredSize(new Dimension(SQUARE_LENGTH, SQUARE_LENGTH));

        // Set square appearance
        setBackground(Color.BLACK);

        _discColor = Color.WHITE;
    }

    public void setDiscColor(Color color) {
        _discColor = color;
    }

    public Color getDiscColor() {
        return _discColor;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw disc
        g.setColor(_discColor);
        g.fillOval(0, 0, getWidth(), getHeight());

        // Draw disc stroke
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, getWidth(), getHeight());
    }
}
