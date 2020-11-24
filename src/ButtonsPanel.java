import javax.swing.*;
import java.awt.*;

/**
 * The panel of all the buttons that not
 * related to the game
 */
public class ButtonsPanel extends JPanel {
    private JPanel[] panelHolders;

    /**
     * Initialize buttons panel,
     * add some panels to the panel
     * @param cols number of panels to add
     * @param panelHeight the height of the panel
     * @param panelWidth the width of the panel
     */
    ButtonsPanel(int cols, int panelHeight, int panelWidth) {
        // Set panel preferences
        setLayout(new GridLayout(1, cols));
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        // Add panels to panel
        panelHolders = new JPanel[cols];
        for (int i = 0; i < cols; ++i) {
            panelHolders[i] = new JPanel();
            panelHolders[i].setBackground(Color.WHITE);
            panelHolders[i].setLayout(new BorderLayout());
            add(panelHolders[i]);
        }
    }

    /**
     * Add component to specific panel
     * @param panelIndex the index of the panel
     * @param component the component to add
     * @param constraints the place to add the component
     * @throws IllegalArgumentException in case of invalid index, this exception thrown
     */
    public void addInPanel(int panelIndex, Component component, Object constraints) throws IllegalArgumentException {
        if (panelHolders.length <= panelIndex) {
            throw new IllegalArgumentException("ButtonsPanel: Invalid panel index");
        }

        // Add component to panel
        panelHolders[panelIndex].add(component, constraints);
    }
}
