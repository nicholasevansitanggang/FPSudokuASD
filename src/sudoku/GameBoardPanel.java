package sudoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int CELL_SIZE = 70;
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();

    public GameBoardPanel(int difficultyLevel) {
        super.setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE, 0, 0));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);

                int top = 1, left = 1, bottom = 1, right = 1;

                // Thicker borders for the 3x3 subgrids
                if (row % SudokuConstants.SUBGRID_SIZE == 0) top = 3;
                if (col % SudokuConstants.SUBGRID_SIZE == 0) left = 3;
                if ((row + 1) % SudokuConstants.SUBGRID_SIZE == 0) bottom = 3;
                if ((col + 1) % SudokuConstants.SUBGRID_SIZE == 0) right = 3;

                if (top == 3 || left == 3 || bottom == 3 || right == 3) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLUE));  // Thicker blue borders
                } else {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));  // Thin black borders
                }

                cells[row][col].setFont(new Font("Arial", Font.BOLD, 24));  // Larger, clearer font
                cells[row][col].setHorizontalAlignment(SwingConstants.CENTER);
//                cells[row][col].setVerticalAlignment(SwingConstants.CENTER);

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

        CellInputListener listener = new CellInputListener();
        for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);
                }
            }
        }

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    public void newGame(int difficultyLevel) {
        AudioPlayer.playbackSound("backSound.wav");
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

    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cell sourceCell = (Cell) e.getSource();
            String text = sourceCell.getText();

            if (text.isEmpty() || text.equals("0")) {
                JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 9!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numberIn = Integer.parseInt(sourceCell.getText());

            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
                AudioPlayer.playSound("tuting.wav");
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
                AudioPlayer.playSound2("inputsalah.wav");
            }
            sourceCell.paint();

            if (isSolved()) {
                AudioPlayer.playSound("menangronde.wav");
                JOptionPane.showMessageDialog(null, "Congratulations! You've solved the puzzle!",
                        "Puzzle Solved", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
