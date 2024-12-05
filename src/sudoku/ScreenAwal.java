package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScreenAwal extends JFrame {
    private JButton startGame;
    private JLabel welcomeLabel;
    private JPanel panel;
    private JComboBox<String> difficultyComboBox;
    private JLabel imageLabel;
    private JLayeredPane layeredPane;

    public ScreenAwal() {
        AudioPlayer.playbackSound("backSound.wav");
        setTitle("Sudoku Game");

        // Initialize the layered pane
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 500)); // Set preferred size for the entire panel

        // Add the image label as the background
        ImageIcon icon = new ImageIcon(getClass().getResource("/vector-sudoku-game.jpg")); // Change path as needed
        imageLabel = new JLabel(icon);
        imageLabel.setBounds(0, 0, 900, 500); // Position and size of the background image
        layeredPane.add(imageLabel, Integer.valueOf(0)); // Add to the background layer

        // Create the top panel for controls
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS)); // Vertical layout
        controlsPanel.setOpaque(false); // Make the panel transparent

        // Welcome label
        welcomeLabel = new JLabel("Welcome to Sudoku!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Difficulty ComboBox
        String[] difficulties = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9", "Level 10"};
        difficultyComboBox = new JComboBox<>(difficulties);
        difficultyComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        difficultyComboBox.setMaximumSize(new Dimension(200, 30));
        difficultyComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Start Game button
        startGame = new JButton("Start Game");
        startGame.setFont(new Font("Arial", Font.PLAIN, 16));
        startGame.setMaximumSize(new Dimension(200, 30));
        startGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String difficulty = (String) difficultyComboBox.getSelectedItem();
                int difficultyLevel = getDifficultyLevel(difficulty);

                setVisible(false);
                GameBoardPanel gameBoard = new GameBoardPanel(difficultyLevel);
                JFrame gameFrame = new JFrame("Sudoku Game");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.add(gameBoard);
                gameFrame.pack();
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);
            }
        });

        // Add controls to the panel
        controlsPanel.add(welcomeLabel);
        controlsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        controlsPanel.add(difficultyComboBox);
        controlsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        controlsPanel.add(startGame);

        // Position the controls panel
        controlsPanel.setBounds(300, 150, 300, 200); // Set position and size
        layeredPane.add(controlsPanel, Integer.valueOf(1)); // Add to the top layer

        // Add layeredPane to the frame
        add(layeredPane);
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private int getDifficultyLevel(String difficulty) {
        switch (difficulty) {
            case "Level 1":
                return 4;
            case "Level 2":
                return 8;
            case "Level 3":
                return 12;
            case "Level 4":
                return 15;
            case "Level 5":
                return 18;
            case "Level 6":
                return 21;
            case "Level 7":
                return 24;
            case "Level 8":
                return 27;
            case "Level 9":
                return 30;
            case "Level 10":
                return 35;
            default:
                return 2;
        }
    }
}
