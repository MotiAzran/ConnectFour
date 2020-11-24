import javax.swing.*;
import java.awt.*;

/**
 * Represents a board square, where the disc
 * will enter
 */
public class Square extends JPanel {

    private Color discColor;

    /**
     * Initialize square
     * initialize size, and color
     */
    Square(int squareSize) {
        // Set square size
        setPreferredSize(new Dimension(squareSize, squareSize));

        // Set square appearance
        setBackground(Color.BLACK);

        discColor = Color.WHITE;
    }

    public void setDiscColor(Color color) {
        discColor = color;
    }

    public Color getDiscColor() {
        return discColor;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw disc
        g.setColor(discColor);
        g.fillOval(0, 0, getWidth(), getHeight());

        // Draw disc stroke
        g.setColor(Color.BLACK);
        g.drawOval(0, 0, getWidth(), getHeight());
    }
}
