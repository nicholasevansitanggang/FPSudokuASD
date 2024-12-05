package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sudoku extends JFrame {
    private static final long serialVersionUID = 1L;

    // Private variables
    private final GameBoardPanel board;
    private final JButton btnRestartGame = new JButton("Restart Game");
    private final JLabel timerLabel = new JLabel("Time: 0 seconds", JLabel.CENTER);  // Label to show timer
    private final Timer gameTimer;
    private int elapsedTime = 0;  // Elapsed time in seconds
    private boolean isPaused = false;  // To track if timer is paused

    // Buttons for controlling the timer
    private final JButton btnPauseTimer = new JButton("Pause Timer");
    private final JButton btnResumeTimer = new JButton("Resume Timer");
    private final JButton btnResetTimer = new JButton("Reset Timer");

    // Constructor modified to accept difficulty level
    public Sudoku(int difficultyLevel) {
        // Initialize GameBoardPanel with difficultyLevel
        board = new GameBoardPanel(difficultyLevel);

        // Set up the container with BorderLayout
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());  // Layout untuk menempatkan komponen di dalam frame

        // Add the game board to the center of the layout
        cp.add(board, BorderLayout.CENTER);

        // Create a panel for the timer and buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Set up the timer label (Top of the frame)
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        topPanel.add(timerLabel, BorderLayout.CENTER);

        // Add the top panel with timer label to the NORTH of the main frame
        cp.add(topPanel, BorderLayout.NORTH);

        // Panel untuk tombol kontrol timer
        JPanel timerControlsPanel = new JPanel();
        timerControlsPanel.setLayout(new FlowLayout());

        // Pause button action
        btnPauseTimer.setFont(new Font("Arial", Font.PLAIN, 16));
        btnPauseTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseTimer();
            }
        });

        // Resume button action
        btnResumeTimer.setFont(new Font("Arial", Font.PLAIN, 16));
        btnResumeTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeTimer();
            }
        });

        // Reset button action
        btnResetTimer.setFont(new Font("Arial", Font.PLAIN, 16));
        btnResetTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
            }
        });

        // Add the buttons for controlling the timer
        timerControlsPanel.add(btnPauseTimer);
        timerControlsPanel.add(btnResumeTimer);
        timerControlsPanel.add(btnResetTimer);

        // Add timer controls panel to the top of the frame
        cp.add(timerControlsPanel, BorderLayout.NORTH);

        // Create a panel for buttons (FlowLayout will make it easier to center the buttons)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));  // Using BoxLayout for vertical arrangement
        bottomPanel.setPreferredSize(new Dimension(100, 30));  // Make sure the panel is big enough

        // Set up the Restart Game button
        btnRestartGame.setFont(new Font("Arial", Font.PLAIN, 16));
        btnRestartGame.setPreferredSize(new Dimension(100, 30));  // Set button size
        btnRestartGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Dispose the current frame and return to ScreenAwal
                dispose();  // Close the current Sudoku frame
                ScreenAwal awal = new ScreenAwal();  // Create a new instance of ScreenAwal
                awal.setVisible(true);  // Show the ScreenAwal frame
            }
        });

        // Add the Restart Game button to the bottom panel
        bottomPanel.add(btnRestartGame, BorderLayout.CENTER);  // Button at the center of the bottom panel

        // Add the bottom panel with the button to the SOUTH of the main frame
        cp.add(bottomPanel, BorderLayout.SOUTH);  // Panel at the bottom

        // Initialize the game board to start the game with the given difficulty
        board.newGame(difficultyLevel);

        // Timer setup
        gameTimer = new Timer(1000, new ActionListener() {  // Timer fires every second
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPaused) {
                    elapsedTime++;  // Increment elapsed time every second
                    timerLabel.setText("Time: " + elapsedTime + " seconds");
                }
            }
        });
        gameTimer.start();  // Start the timer immediately

        // Ensure proper re-layout and rendering
        cp.revalidate();
        cp.repaint();

        // Make sure the frame size is adjusted and set to visible
        pack();  // Adjust the frame size to fit all components
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // To close the application when the frame is closed
        setTitle("Sudoku");
        setSize(600, 600);  // Set an appropriate window size
        setVisible(true);  // Make sure the frame is visible
    }

    // Method to pause the timer
    public void pauseTimer() {
        isPaused = true;
        btnPauseTimer.setEnabled(false);  // Disable Pause button when the timer is paused
        btnResumeTimer.setEnabled(true);  // Enable Resume button
    }

    // Method to resume the timer
    public void resumeTimer() {
        isPaused = false;
        btnPauseTimer.setEnabled(true);  // Enable Pause button when the timer is resumed
        btnResumeTimer.setEnabled(false);  // Disable Resume button
    }

    // Method to reset the timer
    public void resetTimer() {
        elapsedTime = 0;
        timerLabel.setText("Time: 0 seconds");
        isPaused = false;
        btnPauseTimer.setEnabled(true);
        btnResumeTimer.setEnabled(false);
    }

    public static void main(String[] args) {
        // Sample starting difficulty level
        new Sudoku(2); //Easy
    }
}