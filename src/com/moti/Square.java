package com.moti;

import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {

    final static int SQUARE_LENGTH = 100;
    private Color _disc_color;

    Square() {
        // Set square size
        setPreferredSize(new Dimension(SQUARE_LENGTH, SQUARE_LENGTH));

        // Set square appearance
        setBackground(Color.BLACK);

        _disc_color = Color.WHITE;
    }

    public void set_disc_color(Color color) {
        _disc_color = color;
    }

    public Color get_disc_color() {
        return _disc_color;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get square center coordinates
        final int DISC_RADIUS = 75;
        int xCenter = (SQUARE_LENGTH / 2) - (DISC_RADIUS / 2);
        int yCenter = (SQUARE_LENGTH / 2) - (DISC_RADIUS / 2);

        // Draw disc
        g.setColor(_disc_color);
        g.fillOval(xCenter, yCenter, DISC_RADIUS, DISC_RADIUS);

        // Draw disc stroke
        g.setColor(Color.BLACK);
        g.drawOval(xCenter, yCenter, DISC_RADIUS, DISC_RADIUS);
    }
}
