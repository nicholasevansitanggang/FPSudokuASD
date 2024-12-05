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
    private final JLabel timerLabel = new JLabel("Time: 0 seconds", JLabel.CENTER);  // Label to show timer
    private final JLabel levelLabel = new JLabel("Level: Easy", JLabel.CENTER);  // Label to show level
    private final Timer gameTimer;
    private int elapsedTime = 0;  // Elapsed time in seconds
    private boolean isPaused = false;  // To track if timer is paused

    // Buttons for controlling the timer
    private final JButton btnPauseTimer = new JButton("Pause Timer");
    private final JButton btnResumeTimer = new JButton("Resume Timer");
    private final JButton btnResetTimer = new JButton("Reset Timer");

    // Constructor modified to accept difficulty level
    public Sudoku(int difficultyLevel) {
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
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        topPanel.add(timerLabel);
        topPanel.add(levelLabel);  // Menambahkan level label ke topPanel
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
            public void actionPerformed(ActionEvent e) {
                pauseTimer();
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

        // Tombol restart game yang memperbaiki freeze
        btnRestartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hentikan timer sebelum memulai game ulang
                gameTimer.stop();
                dispose();
                AudioPlayer.stopSound();
                ScreenAwal sa = new ScreenAwal();
                sa.setVisible(true);

                // Reset timer dan mulai timer dari 0
                resetTimer();  // Reset timer
                gameTimer.start();  // Mulai kembali timer setelah game dimulai ulang
            }
        });

        // Inisialisasi dan mulai timer
        gameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    elapsedTime++;
                    timerLabel.setText("Time: " + elapsedTime + " seconds");
                }
            }
        });
        gameTimer.start();  // Memulai timer saat game dimulai

        // Set the level label according to the difficulty level
        setLevelLabel(difficultyLevel);

        // Revalidate dan repaint frame
        cp.revalidate();
        cp.repaint();

        // Pengaturan ukuran frame
        pack();  // Sesuaikan ukuran window berdasarkan komponen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sudoku");
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
        elapsedTime = 0;
        timerLabel.setText("Time: 0 seconds");
        isPaused = false;
        btnPauseTimer.setEnabled(true);
        btnResumeTimer.setEnabled(false);
    }
}
