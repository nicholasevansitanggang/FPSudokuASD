/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #14
 * 1 - 5026231146 - Nicholas Evan Sitanggang
 * 2 - 5026231169 - Daniel Bara Seftino
 * 3 - 5026231182 - Sahilah Amru
 */
package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int CELL_SIZE = 70;
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();

    private int seconds = 0;
    private int minutes = 0;
    private Timer timer;

    // Variabel untuk jumlah hint yang tersisa
    private int remainingHints = 3;

    public GameBoardPanel(int difficultyLevel) {
        super.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        // Menambahkan tombol Hint
        JButton hintButton = new JButton("Hint (" + remainingHints + " left)");
        hintButton.setFont(new Font("SciFi", Font.BOLD, 16));
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                giveHint(hintButton);  // Pass the hintButton to update the label
            }
        });

        // Panel untuk tombol hint dan timer
        JPanel hintPanel = new JPanel();
        hintPanel.setLayout(new BorderLayout());
        hintPanel.add(hintButton, BorderLayout.CENTER);
        topPanel.add(hintPanel);

        add(topPanel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE, 0, 0));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);

                int top = 1, left = 1, bottom = 1, right = 1;

                if (row % SudokuConstants.SUBGRID_SIZE == 0) top = 2;
                if (col % SudokuConstants.SUBGRID_SIZE == 0) left = 2;
                if ((row + 1) % SudokuConstants.SUBGRID_SIZE == 0) bottom = 2;
                if ((col + 1) % SudokuConstants.SUBGRID_SIZE == 0) right = 2;

                if (top == 2 || left == 2 || bottom == 2 || right == 2) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, new Color(45, 45, 45)));
                } else {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                }

                cells[row][col].setFont(new Font("SciFi", Font.BOLD, 24));
                cells[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                gridPanel.add(cells[row][col]);
            }
        }

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(gridPanel, BorderLayout.CENTER);

        puzzle.newPuzzle(difficultyLevel);

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }

        // Menambahkan KeyListener pada setiap Cell
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addKeyListener(new CellInputListener());
                    cells[row][col].setFocusable(true); // Pastikan cell bisa mendapatkan fokus
                }
            }
        }

        // Inisialisasi Timer
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
            }
        });
        timer.start();

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void newGame(int difficultyLevel) {
        AudioPlayer.playbackSound("src/game_backsound.wav", 0.80f);
        puzzle.newPuzzle(difficultyLevel);

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // Fungsi untuk memberikan hint
    public void giveHint(JButton hintButton) {
        if (remainingHints > 0) {
            // Cari sel kosong yang belum terisi
            boolean hintGiven = false;
            for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
                for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                    if (cells[row][col].getText().isEmpty() && !puzzle.isGiven[row][col]) {
                        // Isi sel dengan angka yang benar dari puzzle
                        cells[row][col].setText(String.valueOf(puzzle.numbers[row][col]));
                        cells[row][col].status = CellStatus.CORRECT_GUESS;
                        cells[row][col].paint();
                        remainingHints--;
                        hintGiven = true;

                        // Suara hint
                        AudioPlayer.playSound("hint_sound.wav");  // Ganti dengan suara hint yang sesuai

                        break;
                    }
                }
                if (hintGiven) break;
            }

            // Update tombol dengan jumlah hint yang tersisa
            hintButton.setText("Hint (" + remainingHints + " left)");

            // Jika hint habis
            if (remainingHints == 0) {
                hintButton.setEnabled(false);  // Menonaktifkan tombol hint jika habis
            }

            // Periksa jika game sudah selesai setelah memberikan hint
            if (isSolved()) {
                AudioPlayer.playSound("menangronde.wav");
                JOptionPane.showMessageDialog(this, "Congratulations! You've solved the puzzle!",
                        "Puzzle Solved", JOptionPane.INFORMATION_MESSAGE);
                AudioPlayer.stopSound();

            }

        } else {
            JOptionPane.showMessageDialog(this, "No more hints left!", "Hint Limit Reached", JOptionPane.WARNING_MESSAGE);
        }
    }

    // KeyListener untuk memproses input langsung
    private class CellInputListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            // Tidak digunakan
        }

        @Override
        public void keyReleased(KeyEvent e) {
            Cell sourceCell = (Cell) e.getSource();
            char keyChar = e.getKeyChar();

            // Pastikan hanya angka 1-9 yang bisa dimasukkan
            if (Character.isDigit(keyChar) && keyChar != '0') {
                int numberIn = Character.getNumericValue(keyChar);

                // Periksa apakah jawaban benar atau salah
                if (numberIn == sourceCell.number) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                    AudioPlayer.playSound("tuting.wav");
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                    AudioPlayer.playSound2("inputsalah.wav");
                }

                sourceCell.paint();

                // Cek apakah game sudah selesai
                if (isSolved()) {
                    AudioPlayer.playSound("menangronde.wav");
                    JOptionPane.showMessageDialog(null, "Congratulations! You've solved the puzzle!",
                            "Puzzle Solved", JOptionPane.INFORMATION_MESSAGE);
                    AudioPlayer.stopSound();
                }
            } else {
                // Jika input bukan angka yang valid, abaikan
                sourceCell.setText(""); // Clear the text if it's invalid input
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // Tidak digunakan
        }
    }
}