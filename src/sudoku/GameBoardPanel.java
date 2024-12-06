package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final int CELL_SIZE = 70;
    private int BOARD_WIDTH;
    private int BOARD_HEIGHT;

    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Puzzle puzzle = new Puzzle();


    public GameBoardPanel(int difficultyLevel) {
        setLayout(new BorderLayout());

        // Set initial board width and height
        BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
        BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;

        // Create a JLayeredPane for background and grid layers
        JLayeredPane layeredPane = new JLayeredPane();

        // Create BackgroundPanel and set its size to fill the whole board area
        BackgroundPanel backgroundPanel = new BackgroundPanel("/screenawal.gif");
        backgroundPanel.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT); // Set background to cover the whole board
        layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

        // Panel for Level and Timer
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false); // Make this transparent to see the background


        // Add Level and Timer to the top panel
        topPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer

        // Add top panel to the North part of the layout
        topPanel.setBounds(0, 0, BOARD_WIDTH, 50);  // Set position for timer
        layeredPane.add(topPanel, JLayeredPane.PALETTE_LAYER);

        // Panel for Sudoku Grid
        JPanel gridPanel = new JPanel(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE, 0, 0));

        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);

                int top = 1, left = 1, bottom = 1, right = 1;

                // Thicker borders for the 3x3 subgrids
                if (row % SudokuConstants.SUBGRID_SIZE == 0) top = 2;
                if (col % SudokuConstants.SUBGRID_SIZE == 0) left = 2;
                if ((row + 1) % SudokuConstants.SUBGRID_SIZE == 0) bottom = 2;
                if ((col + 1) % SudokuConstants.SUBGRID_SIZE == 0) right = 2;

                if (top == 2 || left == 2 || bottom == 2 || right == 2) {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, new Color(45, 45, 45))); // Thicker borders
                } else {
                    cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK)); // Thin borders
                }

                cells[row][col].setFont(new Font("Arial", Font.BOLD, 24)); // Larger font
                cells[row][col].setHorizontalAlignment(SwingConstants.CENTER);
                gridPanel.add(cells[row][col]);
            }
        }

        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Center the gridPanel within the layeredPane
        gridPanel.setBounds((BOARD_WIDTH - gridPanel.getPreferredSize().width) / 2,
                (BOARD_HEIGHT - gridPanel.getPreferredSize().height - 50) / 2, // Leave space for timer
                BOARD_WIDTH,
                BOARD_HEIGHT - 50); // Leave space for the timer
        layeredPane.add(gridPanel, JLayeredPane.MODAL_LAYER);

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

        // Timer Initialization


        // Add the JLayeredPane to the main panel
        add(layeredPane, BorderLayout.CENTER);

        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        BOARD_WIDTH = d.width;
        BOARD_HEIGHT = d.height;
        revalidate();
        repaint();
    }

    public void newGame(int difficultyLevel) {
        AudioPlayer.playbackSound("backSound.wav", 0.65f);
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
