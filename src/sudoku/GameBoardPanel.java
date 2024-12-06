//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final int CELL_SIZE = 70;
    public static final int BOARD_WIDTH = 630;
    public static final int BOARD_HEIGHT = 630;
    private Cell[][] cells = new Cell[9][9];
    private Puzzle puzzle = new Puzzle();
    private JLabel timerLabel;
    private int seconds = 0;
    private int minutes = 0;
    private Timer timer;

    public GameBoardPanel(int difficultyLevel) {
        super.setLayout(new BorderLayout());
        this.timerLabel = new JLabel(String.format("%02d:%02d", this.minutes, this.seconds), 0);
        this.timerLabel.setFont(new Font("Arial", 1, 24));
        this.timerLabel.setForeground(Color.BLACK);
        JPanel gridPanel = new JPanel(new GridLayout(9, 9, 0, 0));

        int row;
        int col;
        for(row = 0; row < 9; ++row) {
            for(col = 0; col < 9; ++col) {
                this.cells[row][col] = new Cell(row, col);
                int top = 1;
                int left = 1;
                int bottom = 1;
                int right = 1;
                if (row % 3 == 0) {
                    top = 2;
                }

                if (col % 3 == 0) {
                    left = 2;
                }

                if ((row + 1) % 3 == 0) {
                    bottom = 2;
                }

                if ((col + 1) % 3 == 0) {
                    right = 2;
                }

                if (top != 2 && left != 2 && bottom != 2 && right != 2) {
                    this.cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                } else {
                    this.cells[row][col].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, new Color(45, 45, 45)));
                }

                this.cells[row][col].setFont(new Font("Arial", 1, 24));
                this.cells[row][col].setHorizontalAlignment(0);
                gridPanel.add(this.cells[row][col]);
            }
        }

        this.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204), 2));
        this.add(gridPanel, "Center");
        this.puzzle.newPuzzle(difficultyLevel);

        for(row = 0; row < 9; ++row) {
            for(col = 0; col < 9; ++col) {
                this.cells[row][col].newGame(this.puzzle.numbers[row][col], this.puzzle.isGiven[row][col]);
            }
        }

        this.timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ++GameBoardPanel.this.seconds;
                if (GameBoardPanel.this.seconds == 60) {
                    GameBoardPanel.this.seconds = 0;
                    ++GameBoardPanel.this.minutes;
                }

                GameBoardPanel.this.timerLabel.setText(String.format("%02d:%02d", GameBoardPanel.this.minutes, GameBoardPanel.this.seconds));
            }
        });
        this.timer.start();
        JPanel timerPanel = new JPanel();
        timerPanel.setLayout(new FlowLayout(1));
        timerPanel.setOpaque(false);
        timerPanel.add(this.timerLabel);
        this.add(timerPanel, "North");
        this.setPreferredSize(new Dimension(630, 630));
    }

    public void newGame(int difficultyLevel) {
        this.puzzle.newPuzzle(difficultyLevel);

        for(int row = 0; row < 9; ++row) {
            for(int col = 0; col < 9; ++col) {
                this.cells[row][col].newGame(this.puzzle.numbers[row][col], this.puzzle.isGiven[row][col]);
            }
        }

    }

    public boolean isSolved() {
        for(int row = 0; row < 9; ++row) {
            for(int col = 0; col < 9; ++col) {
                if (this.cells[row][col].status == CellStatus.TO_GUESS || this.cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }

        return true;
    }
}