//package sudoku;
///**
// * The Sudoku number puzzle to be solved
// */
//public class Puzzle {
//    // All variables have package access
//    // The numbers on the puzzle
//    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
//    // The clues - isGiven (no need to guess) or need to guess
//    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
//
//    // Constructor
//    public Puzzle() {
//        super();
//    }
//
//    // Generate a new puzzle given the number of cells to be guessed, which can be used
//    //  to control the difficulty level.
//    // This method shall set (or update) the arrays numbers and isGiven
//    public void newPuzzle(int cellsToGuess) { //benerin pake algoritma kapan dia bener kapan dia salah
//        // I hardcode a puzzle here for illustration and testing.
//        int[][] hardcodedNumbers =
//                {{5, 3, 4, 6, 7, 8, 9, 1, 2},
//                        {6, 7, 2, 1, 9, 5, 3, 4, 8},
//                        {1, 9, 8, 3, 4, 2, 5, 6, 7},
//                        {8, 5, 9, 7, 6, 1, 4, 2, 3},
//                        {4, 2, 6, 8, 5, 3, 7, 9, 1},
//                        {7, 1, 3, 9, 2, 4, 8, 5, 6},
//                        {9, 6, 1, 5, 3, 7, 2, 8, 4},
//                        {2, 8, 7, 4, 1, 9, 6, 3, 5},
//                        {3, 4, 5, 2, 8, 6, 1, 7, 9}};
//
//        // Copy from hardcodedNumbers into the array "numbers"
//        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
//            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
//                numbers[row][col] = hardcodedNumbers[row][col];
//            }
//        }
//
//        // Need to use input parameter cellsToGuess!
//        // Hardcoded for testing, only 2 cells of "8" is NOT GIVEN
//        boolean[][] hardcodedIsGiven =
//                {{true, true, true, true, true, false, true, true, true},
//                        {true, true, true, true, true, true, true, true, false},
//                        {true, true, true, true, true, true, true, true, true},
//                        {true, true, true, true, true, true, true, true, true},
//                        {true, true, true, true, true, true, true, true, true},
//                        {true, true, true, true, true, true, true, true, true},
//                        {true, true, true, true, true, true, true, true, true},
//                        {true, true, true, true, true, true, true, true, true},
//                        {true, true, true, true, true, true, true, true, true}};
//
//        // Copy from hardcodedIsGiven into array "isGiven"
//        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
//            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
//                isGiven[row][col] = hardcodedIsGiven[row][col];
//            }
//        }
//    }
//
//    //(For advanced students) use singleton design pattern for this class
//}
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
        for (int x = 0; x < SudokuConstants.GRID_SIZE; x++) {
            if (numbers[row][x] == num || numbers[x][col] == num) {
                return false;
            }
        }

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