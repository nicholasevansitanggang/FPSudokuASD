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

    public ScreenAwal() {
        AudioPlayer.playbackSound("backSound.wav");
        setTitle("Sudoku Game");

        // Panel utama menggunakan BorderLayout
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Label selamat datang
        welcomeLabel = new JLabel("Welcome to Sudoku!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Gambar di bagian atas
        ImageIcon icon = new ImageIcon(getClass().getResource("/vector-sudoku-game.jpg")); // Ganti path gambar sesuai lokasi
        imageLabel = new JLabel(icon);

        // Pilihan tingkat kesulitan
        String[] difficulties = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9"
                , "Level 10"};
        difficultyComboBox = new JComboBox<>(difficulties);
        difficultyComboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        difficultyComboBox.setPreferredSize(new Dimension(200, 30)); // Lebar lebih besar dan tinggi 30
        // Tombol Start Game

        JPanel bottomPanel1 = new JPanel();
        bottomPanel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));  // Gunakan FlowLayout untuk tombol dan ComboBox

        // Menambahkan ComboBox ke panel bawah
        bottomPanel1.add(difficultyComboBox);
        startGame = new JButton("Start Game");
        startGame.setFont(new Font("Arial", Font.PLAIN, 16));
        startGame.setPreferredSize(new Dimension(300, 28)); // Sesuaikan lebar dan tinggi tombol dengan combo box
        startGame.setMaximumSize(new Dimension(300, 28)); // Pastikan maksimal lebar dan tinggi sama
        startGame.setHorizontalAlignment(SwingConstants.CENTER); // Menempatkan teks di tengah tombol
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mendapatkan tingkat kesulitan yang dipilih
                String difficulty = (String) difficultyComboBox.getSelectedItem();
                int difficultyLevel = getDifficultyLevel(difficulty);

                // Menyembunyikan layar awal dan membuka game board
                setVisible(false);
                GameBoardPanel gameBoard = new GameBoardPanel(difficultyLevel);
                JFrame gameFrame = new JFrame("Sudoku Game");
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.add(gameBoard);
                gameFrame.pack();
                gameFrame.setLocationRelativeTo(null); // Memposisikan jendela di tengah
                gameFrame.setVisible(true);
            }
        });

        // Panel bagian bawah untuk ComboBox dan tombol Start Game
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));  // Gunakan FlowLayout untuk tombol dan ComboBox


        // Menambahkan ComboBox dan tombol ke bottomPanel
        bottomPanel.add(difficultyComboBox);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(startGame);

        // Menyusun layout
        panel.add(welcomeLabel, BorderLayout.NORTH); // Label di bagian atas
        panel.add(imageLabel, BorderLayout.CENTER); // Gambar di tengah
        panel.add(bottomPanel, BorderLayout.SOUTH); // Pilihan level dan tombol di bawah

        // Mengatur frame
        add(panel);
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Mengonversi string ke tingkat kesulitan
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
                return 2; // Default ke Easy
        }
    }
}
