import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represent the board of the game
 */
public class Board extends JPanel {
    private final Color PLAYER1_COLOR = Color.BLUE;
    private final String PLAYER1_COLOR_NAME = "Blue";
    private final Color PLAYER2_COLOR = Color.RED;
    private final String PLAYER2_COLOR_NAME = "Red";
    private final int DISCS_IN_A_ROW_TO_WIN = 4;
    private final int rowsCount;
    private final int colsCount;
    private Square[][] board;
    private JButton[] colsButtons;
    private Color currentColor;
    private boolean hasWon;

    /**
     * Initialize the board panel
     * @param rowsCount number of rows of the board
     * @param colsCount number of columns of the board
     * @param squareSize the size of every square in the board
     */
    Board(int rowsCount, int colsCount, int squareSize) {
        this.rowsCount = rowsCount;
        this.colsCount = colsCount;
        currentColor = PLAYER1_COLOR;
        hasWon = false;

        setLayout(new GridLayout(this.rowsCount + 1, this.colsCount));

        // Add board squares to panel
        board = new Square[this.rowsCount][this.colsCount];
        for (int i = 0; i < this.rowsCount; ++i) {
            for (int j = 0; j < this.colsCount; ++j) {
                board[i][j] = new Square(squareSize);
                add(board[i][j]);
            }
        }

        // Add "adding disc" buttons to panel
        colsButtons = new JButton[this.colsCount];
        for (int i = 0; i < this.colsCount; ++i) {
            colsButtons[i] = new JButton(Integer.toString(i+1));
            colsButtons[i].setBackground(currentColor);
            colsButtons[i].setForeground(Color.BLACK);
            colsButtons[i].addActionListener(new BoardButtonListener(i));

            add(colsButtons[i]);
        }
    }

    /**
     * Clears all discs from the board
     */
    public void clearBoard() {
        for (int i = 0; i < rowsCount; ++i) {
            for (int j = 0; j < colsCount; ++j) {
                board[i][j].setDiscColor(Color.WHITE);
            }
        }

        // Set members to their default values
        currentColor = PLAYER1_COLOR;
        hasWon = false;
    }

    /**
     * Change the colors of the buttons
     * @param g graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set buttons color to the current color
        setButtonsColor();
    }

    /**
     * Set all the buttons color
     * to the current color
     */
    private void setButtonsColor() {
        for (int i = 0; i < colsCount; ++i) {
            colsButtons[i].setBackground(currentColor);
        }
    }

    private String getCurrentColorName() {
        return (PLAYER1_COLOR == currentColor ? PLAYER1_COLOR_NAME : PLAYER2_COLOR_NAME);
    }

    /**
     * listener for the buttons
     */
    private class BoardButtonListener implements ActionListener {
        /**
         * the class adds a disc if there is available square
         * and checks for win
         */

        int column;

        /**
         * Initialize the listener
         * @param column the column of the button
         */
        BoardButtonListener(int column) {
            this.column = column;
        }

        /**
         * Get available row in some column
         * @param col_index the column to search for square
         * @return if there is no available square returns -1,
         *          otherwise the row number of the available square
         */
        private int getAvailableSquareRow(int col_index) {
            // Search for an empty square
            for (int i = (rowsCount - 1); i >= 0; --i) {
                if (Color.WHITE == board[i][col_index].getDiscColor()) {
                    return i;
                }
            }

            return -1;
        }

        /**
         * Swap the current color to the other one
         */
        private void swapCurrentColor() {
            currentColor = (PLAYER1_COLOR == currentColor ? PLAYER2_COLOR : PLAYER1_COLOR);
        }

        /**
         * Check if there is a 4-streak of discs
         * in the same color in some row around specific square
         * @param row the row index of the last inserted disc
         * @param col the column index of the last inserted disc
         * @return true if there is a 4-streak, otherwise false
         */
        private boolean checkVictoryRow(int row, int col) {
            int discCounter = 0;
            for (int currentCol = Math.max(0, col - 4); currentCol < Math.min(col + 4, colsCount); ++currentCol) {
                if (board[row][currentCol].getDiscColor() == currentColor) {
                    ++discCounter;
                } else {
                    discCounter = 0;
                }

                if (DISCS_IN_A_ROW_TO_WIN == discCounter) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Check if there is 4-streak if discs in
         * the same color in some column around specific square
         * @param row the row index of the last inserted disc
         * @param col the column index of the last inserted disc
         * @return true if the 4-streak found, otherwise false
         */
        private boolean checkVictoryColumn(int row, int col) {
            int discCounter = 0;
            for (int currentRow = Math.max(0, row - 4); currentRow < Math.min(row + 4, rowsCount); ++currentRow) {
                if (board[currentRow][col].getDiscColor() == currentColor) {
                    ++discCounter;
                } else {
                    discCounter = 0;
                }

                if (DISCS_IN_A_ROW_TO_WIN == discCounter) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Check if there is a 4-streak of
         * discs in the same color in some diagonal
         * from left to right
         * @param row the row index of the last inserted disc
         * @param col the column index of the last inserted disc
         * @return true if the 4-streak is found, otherwise false
         */
        public boolean checkVictoryDiagonalLeftToRight(int row, int col) {
            int discCounter = 0;

            int startRow = 0;
            int startCol = 0;
            for (startRow = row, startCol = col;
                 startRow < Math.min(row + 4, rowsCount - 1) && startCol > Math.max(0, col - 4);
                 ++startRow, --startCol);

            int endRow = 0;
            int endCol = 0;
            for (endRow = row, endCol = col;
                 endRow >= Math.max(0, row - 4) && endCol <= Math.min(col + 4, colsCount - 1);
                 --endRow, ++endCol);

            for (int currentRow = startRow, currentCol = startCol;
                 currentRow > endRow && currentCol < endCol;
                 --currentRow, ++currentCol) {
                if (board[currentRow][currentCol].getDiscColor() == currentColor) {
                    ++discCounter;
                } else {
                    discCounter = 0;
                }

                if (DISCS_IN_A_ROW_TO_WIN == discCounter) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Check if there is a 4-streak of
         * discs in the same color in some diagonal
         * from right to left
         * @param row the row index of the last inserted disc
         * @param col the column index of the last inserted disc
         * @return true if the 4-streak is found, otherwise false
         */
        public boolean checkVictoryDiagonalRightToLeft(int row, int col) {
            int discCounter = 0;

            int startRow = 0;
            int startCol = 0;
            for (startRow = row, startCol = col;
                 startRow < Math.min(row + 4, rowsCount - 1) && startCol < Math.min(col + 4, colsCount - 1);
                 ++startRow, ++startCol);

            int endRow = 0;
            int endCol = 0;
            for (endRow = row, endCol = col;
                 endRow >= Math.max(0, row - 4) && endCol >= Math.max(0, col - 4);
                 --endRow, --endCol);

            for (int currentRow = startRow, currentCol = startCol;
                 currentRow > endRow && currentCol > endCol;
                 --currentRow, --currentCol) {
                if (board[currentRow][currentCol].getDiscColor() == currentColor) {
                    ++discCounter;
                } else {
                    discCounter = 0;
                }

                if (DISCS_IN_A_ROW_TO_WIN == discCounter) {
                    return true;
                }
            }

            return false;
        }

        /**
         * Checks if there is a 4-streak of discs
         * in the same color in some diagonal
         * @return true if the 4-streak found, otherwise false
         */
        private boolean checkVictoryDiagonal(int row, int col) {
            return checkVictoryDiagonalLeftToRight(row, col) || checkVictoryDiagonalRightToLeft(row, col);
        }

        /**
         * Checks if there is a 4-streak of discs
         * in the same color in some row, column or diagonal
         * @return true if the 4-streak found, otherwise false
         */
        private boolean checkVictory(int row, int col) {
            return checkVictoryRow(row, col) || checkVictoryColumn(row, col) || checkVictoryDiagonal(row, col);
        }

        /**
         * The button pressed, add the disc if can
         * and check for winning
         * @param e Event information
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hasWon) {
                return;
            }

            int availableRow = getAvailableSquareRow(column);
            if (-1 == availableRow) {
                JOptionPane.showMessageDialog(null, "The column is full");
                return;
            }

            // Add disc to the available square
            board[availableRow][column].setDiscColor(currentColor);

            // Paint the square
            repaint();

            // Check for victory
            hasWon = checkVictory(availableRow, column);
            if (hasWon) {
                String victory_msg = String.format("%s player has won", getCurrentColorName());
                JOptionPane.showMessageDialog(null, victory_msg);
                return;
            }

            swapCurrentColor();

            repaint();
        }
    }
}
