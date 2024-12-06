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

import java.util.Random;

public class Puzzle {
    // All variables have package access
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    // to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven
    public void newPuzzle(int cellsToGuess) {
        Random rand = new Random();

        // Fill the numbers array with a valid Sudoku puzzle
        generateSudokuSolution();

        // Initialize all cells in isGiven as true
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                isGiven[row][col] = true;
            }
        }

        // Set random cells to be guessed (false in isGiven)
        for (int i = 0; i < cellsToGuess; i++) {
            int row;
            int col;
            do {
                row = rand.nextInt(SudokuConstants.GRID_SIZE);
                col = rand.nextInt(SudokuConstants.GRID_SIZE);
            } while (!isGiven[row][col]);
            isGiven[row][col] = false;
        }
    }

    // Generate a complete and valid Sudoku solution
    private void generateSudokuSolution() {
        Random rand = new Random();

        // Fill the diagonal 3x3 boxes
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i += 3) {
            fillDiagonalBox(i, i, rand);
        }

        // Fill the remaining cells
        fillRemaining(0, 3, rand);
    }

    // Fill a 3x3 diagonal box
    private void fillDiagonalBox(int row, int col, Random rand) {
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = rand.nextInt(9) + 1;
                } while (!isSafeToPlace(row + i, col + j, num));
                numbers[row + i][col + j] = num;
            }
        }
    }

    // Fill the remaining cells
    private boolean fillRemaining(int i, int j, Random rand) {
        // Check if we have filled all cells
        if (i >= SudokuConstants.GRID_SIZE && j >= SudokuConstants.GRID_SIZE) {
            return true;
        }

        // Move to next row if we are at the end of a row
        if (j >= SudokuConstants.GRID_SIZE) {
            i++;
            j = 0;
        }

        // Skip the diagonal boxes
        if (i < 3) {
            if (j < 3) {
                j = 3;
            }
        } else if (i < SudokuConstants.GRID_SIZE - 3) {
            if (j == (i / 3) * 3) {
                j += 3;
            }
        } else {
            if (j == SudokuConstants.GRID_SIZE - 3) {
                i++;
                j = 0;
                if (i >= SudokuConstants.GRID_SIZE) {
                    return true;
                }
            }
        }

        // Try placing numbers randomly in remaining cells
        for (int num = 1; num <= 9; num++) {
            if (isSafeToPlace(i, j, num)) {
                numbers[i][j] = num;
                if (fillRemaining(i, j + 1, rand)) {
                    return true;
                }
                numbers[i][j] = 0;
            }
        }
        return false;
    }

    // Check if it's safe to place a number in a cell
    private boolean isSafeToPlace(int row, int col, int num) {
        // Check row
        for (int x = 0; x < SudokuConstants.GRID_SIZE; x++) {
            if (numbers[row][x] == num) {
                return false;
            }
        }

        // Check column
        for (int x = 0; x < SudokuConstants.GRID_SIZE; x++) {
            if (numbers[x][col] == num) {
                return false;
            }
        }

        // Check 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (numbers[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

}