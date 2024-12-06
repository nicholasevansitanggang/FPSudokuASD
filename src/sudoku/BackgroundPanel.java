package sudoku;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private ImageIcon backgroundImage;

    public BackgroundPanel(String imagePath) {
        // Load the background image
        backgroundImage = new ImageIcon(getClass().getResource(imagePath));
        setLayout(null); // Use null layout to manually control the position of components
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image, scale it to the panel's size
        if (backgroundImage != null) {
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
