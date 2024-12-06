package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;

    // Private variables
    private final GameBoardPanel board;
    private final JButton btnRestartGame = new JButton("Back to Menu");
    private final JLabel levelLabel = new JLabel("Level: ", JLabel.CENTER);  // Label to show level
    private final Timer gameTimer;
    private int elapsedTime = 0;  // Elapsed time in seconds
    private boolean isPaused = false;  // To track if timer is paused
    private JLabel timerLabel;
    private int seconds = 0;
    private int minutes = 0;

    // Buttons for controlling the timer
    private final JButton btnPauseTimer = new JButton("Pause Timer");
    private final JButton btnResumeTimer = new JButton("Resume Timer");
    private final JButton btnResetTimer = new JButton("Reset Timer");

    // Variabel untuk menghitung kotak kosong
    private int remainingCells = SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE;
    private JLabel remainingCellsLabel = new JLabel("Remaining: " + remainingCells, JLabel.CENTER);  // Label to show remaining cells

    // Constructor modified to accept difficulty level and player name
    public Sudoku(int difficultyLevel, String playerName) {
        // Inisialisasi board dengan difficultyLevel
        board = new GameBoardPanel(difficultyLevel);

        // Set up layout utama dengan BorderLayout
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());  // Menggunakan BorderLayout untuk menempatkan komponen

        // Add the game board to the center
        cp.add(board, BorderLayout.CENTER);

        // Panel untuk Timer dan Level (Upper area)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Set up timer label (tampilkan waktu) dan level label
        levelLabel.setFont(new Font("SciFi", Font.PLAIN, 18));
        topPanel.add(levelLabel);  // Menambahkan level label ke topPanel

        // Timer label setup
        timerLabel = new JLabel(String.format("Time: %02d:%02d", minutes, seconds), SwingConstants.CENTER);
        timerLabel.setFont(new Font("SciFi", Font.BOLD, 18));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(timerLabel);

        // Add the remaining cells label
        remainingCellsLabel.setFont(new Font("SciFi", Font.PLAIN, 18));
        topPanel.add(remainingCellsLabel);  // Menambahkan label remaining cells ke topPanel

        cp.add(topPanel, BorderLayout.NORTH);  // Panel timer dan level ditempatkan di atas frame

        // Panel untuk kontrol timer (Pause, Resume, Reset) dan Restart button (Bottom area)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());  // Mengatur tombol kontrol timer dalam layout FlowLayout

        // Add the timer control buttons (Pause, Resume, Reset)
        bottomPanel.add(btnPauseTimer);   // Menambahkan tombol Pause
        bottomPanel.add(btnResumeTimer);  // Menambahkan tombol Resume
        bottomPanel.add(btnResetTimer);   // Menambahkan tombol Reset
        bottomPanel.add(btnRestartGame);  // Menambahkan tombol Restart Game

        cp.add(bottomPanel, BorderLayout.SOUTH);  // Menambahkan panel kontrol timer dan tombol restart ke SOUTH

        // Set up button actions for controlling the timer
        btnPauseTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                pauseTimer();
            }
        });
        btnRestartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                AudioPlayer.stopSound();
                dispose();
                AudioPlayer.stopSound();
                ScreenAwal baru = new ScreenAwal();
                baru.setVisible(true);
                pack();
            }
        });

        btnResumeTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeTimer();
            }
        });

        btnResetTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
            }
        });

        // Inisialisasi dan mulai timer
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    seconds++;
                    if (seconds == 60) {
                        seconds = 0;
                        minutes++;
                    }
                    // Update Timer Label
                    timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
                }
            }
        });
        gameTimer.start();  // Memulai timer saat game dimulai

        // Set the level label according to the difficulty
        setLevelLabel(difficultyLevel);

        // Revalidate dan repaint frame
        cp.revalidate();
        cp.repaint();

        // Set the window title with the player's name
        setTitle(playerName + "'s Sudoku");  // Mengubah title sesuai dengan nama pemain

        // Pengaturan ukuran frame
        pack();  // Sesuaikan ukuran window berdasarkan komponen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setVisible(true);
    }

    // Method to set the level label based on difficulty
    public void setLevelLabel(int difficultyLevel) {
        String levelText = "Level: ";
        switch (difficultyLevel) {
            case 4:
                levelText = levelText + "1";
                break;
            case 8:
                levelText = levelText + "2";
                break;
            case 12:
                levelText = levelText + "3";
                break;
            case 15:
                levelText = levelText + "4";
                break;
            case 18:
                levelText = levelText + "5";
                break;
            case 21:
                levelText = levelText + "6";
                break;
            case 24:
                levelText = levelText + "7";
                break;
            case 27:
                levelText = levelText + "8";
                break;
            case 30:
                levelText = levelText + "9";
                break;
            case 35:
                levelText = levelText + "10";
                break;
            default:
                System.out.println("default");
        }
        levelLabel.setText(levelText);
    }

    // Method to pause the timer
    public void pauseTimer() {
        isPaused = true;
        btnPauseTimer.setEnabled(false);  // Disable Pause button
        btnResumeTimer.setEnabled(true);  // Enable Resume button
    }

    // Method to resume the timer
    public void resumeTimer() {
        isPaused = false;
        btnPauseTimer.setEnabled(true);   // Enable Pause button
        btnResumeTimer.setEnabled(false); // Disable Resume button
    }

    // Method to reset the timer
    public void resetTimer() {
        seconds = 0;
        minutes = 0;
        isPaused = false;
        btnPauseTimer.setEnabled(true);
        btnResumeTimer.setEnabled(false);
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds)); // Reset the timer label
    }

    // Method to handle when the game is won
    private void checkGameWon() {
        if (board.isSolved()) {  // `board.isSolved()` is a placeholder for the actual method to check if the game is won
        }
    }
}
