package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScreenAwal extends JFrame {
    private JButton startGame;
    private JLabel welcomeLabel;
    private JPanel panel;
    private JComboBox<String> difficultyComboBox;
    private JLayeredPane layeredPane;

    public ScreenAwal() {
        AudioPlayer.playbackSound("game_backsound.wav");
        setTitle("Sudoku Game");

        // Initialize the layered pane
        layeredPane = new JLayeredPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/screenawal.gif"));
                Image img = icon.getImage(); // Mengambil image dari ImageIcon

                // Menyesuaikan gambar agar menutupi seluruh window (stretch to fit)
                int width = getWidth(); // Menyesuaikan lebar dengan ukuran window
                int height = getHeight(); // Menyesuaikan tinggi dengan ukuran window

                // Menggambar gambar yang sudah di-stretch untuk menutupi seluruh area
                g.drawImage(img, 0, 0, width, height, this);
            }
        };
        layeredPane.setLayout(new BorderLayout()); // Menggunakan BorderLayout agar panel bisa ditempatkan di tengah
        layeredPane.setPreferredSize(new Dimension(900, 600)); // Set preferred size for the entire panel

        // Create the top panel for controls
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridBagLayout()); // Menggunakan GridBagLayout
        controlsPanel.setOpaque(false); // Make the panel transparent

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 20, 10); // Menambahkan jarak atas dan bawah untuk elemen-elemen kontrol
        gbc.anchor = GridBagConstraints.CENTER; // Menjaga semua elemen tetap di tengah

        // Start Game button
        startGame = new JButton("Start Game");
        startGame.setFont(new Font("SciFi", Font.BOLD, 20)); // Gunakan font SciFi dan tebal
        startGame.setBackground(Color.WHITE); // Latar belakang putih
        startGame.setForeground(Color.BLACK); // Tulisan hitam
        startGame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Border hitam
        startGame.setPreferredSize(new Dimension(250, 40)); // Lebih besar, sesuai dengan ComboBox
        startGame.setFocusable(false); // Menghilangkan efek focus

        // Atur posisi Start Game di baris 0
        gbc.gridx = 0; // Kolom
        gbc.gridy = 0; // Baris
        gbc.gridwidth = 1; // Lebar grid (satu kolom)

        // Menambahkan margin atas untuk memberikan jarak lebih banyak
        gbc.insets = new Insets(180, 10, 10, 10); // Margins untuk tombol start (lebih banyak jarak atas)
        controlsPanel.add(startGame, gbc);

        // Difficulty ComboBox
        String[] difficulties = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9", "Level 10"};
        difficultyComboBox = new JComboBox<>(difficulties);
        difficultyComboBox.setFont(new Font("SciFi", Font.BOLD, 20)); // Gunakan font SciFi dan tebal
        difficultyComboBox.setBackground(new Color(255, 255, 255)); // Latar belakang putih
        difficultyComboBox.setForeground(Color.BLACK); // Tulisan hitam
        difficultyComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Border hitam
        difficultyComboBox.setMaximumSize(new Dimension(250, 40)); // Ukuran lebih besar

        // Atur posisi Difficulty ComboBox di baris 1 (di bawah Start Game)
        gbc.gridx = 0; // Kolom
        gbc.gridy = 1; // Baris
        gbc.gridwidth = 1; // Lebar grid (satu kolom)

        // Menambahkan margin atas untuk memberikan jarak lebih banyak di atas ComboBox
        gbc.insets = new Insets(5, 10, 20, 10); // Menambah jarak atas untuk combo box
        controlsPanel.add(difficultyComboBox, gbc);

        // Add controls to the center of the layered pane
        layeredPane.add(controlsPanel, BorderLayout.CENTER); // Add to the center of the layered pane

        // Add layeredPane to the frame
        add(layeredPane);
        setSize(900, 600); // Ukuran window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on the screen

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String difficulty = (String) difficultyComboBox.getSelectedItem();
                int difficultyLevel = getDifficultyLevel(difficulty);

                setVisible(false);
                Sudoku gameBoard = new Sudoku(difficultyLevel);
                JFrame gameFrame = new JFrame("Sudoku Game");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.add(gameBoard);
                gameFrame.pack();
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);
            }
        });
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
