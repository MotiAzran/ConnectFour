package com.moti;

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
    private int _rows;
    private int _cols;
    private Square[][] _board;
    private JButton[] _colsButtons;
    private Color _currentColor;
    private boolean _hasWon;

    /**
     * Initialize the board panel
     * @param rows number of rows of the board
     * @param cols number of columns of the board
     */
    Board(int rows, int cols) {
        _rows = rows;
        _cols = cols;
        _currentColor = PLAYER1_COLOR;
        _hasWon = false;

        setLayout(new GridLayout(_rows+1, _cols));

        // Add board squares to panel
        _board = new Square[_rows][_cols];
        for (int i = 0; i < _rows; ++i) {
            for (int j = 0; j < _cols; ++j) {
                _board[i][j] = new Square();
                add(_board[i][j]);
            }
        }

        // Add "adding disc" buttons to panel
        _colsButtons = new JButton[_cols];
        for (int i = 0; i < _cols; ++i) {
            _colsButtons[i] = new JButton(Integer.toString(i+1));
            _colsButtons[i].setBackground(_currentColor);
            _colsButtons[i].setForeground(Color.BLACK);
            _colsButtons[i].addActionListener(new BoardButtonListener(i));

            add(_colsButtons[i]);
        }
    }

    /**
     * Clears all discs from the board
     */
    public void clearBoard() {
        for (int i = 0; i < _rows; ++i) {
            for (int j = 0; j < _cols; ++j) {
                _board[i][j].setDiscColor(Color.WHITE);
            }
        }

        // Set members to their default values
        _currentColor = PLAYER1_COLOR;
        _hasWon = false;
    }

    /**
     * Change the colors of the buttons
     * @param g graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set buttons color to the current color
        _setButtonsColor();
    }

    /**
     * Get the name of current color
     * @return name of the current color
     */
    private String _getCurrentColorName() {
        if (PLAYER1_COLOR == _currentColor) {
            return PLAYER1_COLOR_NAME;
        } else {
            return PLAYER2_COLOR_NAME;
        }
    }

    /**
     * Set all the buttons color
     * to the current color
     */
    private void _setButtonsColor() {
        for (int i = 0; i < _cols; ++i) {
            _colsButtons[i].setBackground(_currentColor);
        }
    }

    /**
     * listener for the buttons
     */
    private class BoardButtonListener implements ActionListener {
        /**
         * the class adds a disc if there is available square
         * and checks for win
         */

        int _column;

        /**
         * Initialize the listener
         * @param column the column of the button
         */
        BoardButtonListener(int column) {
            _column = column;
        }

        /**
         * Get available row in some column
         * @param col_index the column to search for square
         * @return if there is no available square returns -1,
         *          otherwise the row number of the available square
         */
        private int _getAvailableSquareRow(int col_index) {
            // Search for an empty square
            for (int i = (_rows - 1); i >= 0; --i) {
                if (Color.WHITE == _board[i][col_index].getDiscColor()) {
                    return i;
                }
            }

            return -1;
        }

        /**
         * Swap the current color to the other one
         */
        private void _swapCurrentColor() {
            if (PLAYER1_COLOR == _currentColor) {
                _currentColor = PLAYER2_COLOR;
            } else {
                _currentColor = PLAYER1_COLOR;
            }
        }

        /**
         * Check if there is a 4-streak of discs
         * in the same color in some row
         * @return true if there is a 4-streak, otherwise false
         */
        private boolean _checkVictoryRow() {
            for (int i = 0; i < _rows; ++i) {
                int discCounter = 0;
                for (int j = 0; j < _cols; ++j) {
                    if (_board[i][j].getDiscColor() == _currentColor) {
                        ++discCounter;
                    } else {
                        discCounter = 0;
                    }

                    if (DISCS_IN_A_ROW_TO_WIN == discCounter) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * Check if there is 4-streak if discs in
         * the same color in some column
         * @return true if the 4-streak found, otherwise false
         */
        private boolean _checkVictoryColumn() {
            for (int i = 0; i < _cols; ++i) {
                int discCounter = 0;
                for (int j = 0; j < _rows; ++j) {
                    if (_board[j][i].getDiscColor() == _currentColor) {
                        ++discCounter;
                    } else {
                        discCounter = 0;
                    }

                    if (DISCS_IN_A_ROW_TO_WIN == discCounter) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * Check if there is a 4-streak of
         * discs in the same color in some diagonal
         * from left to right
         * @return true if the 4-streak is found, otherwise false
         */
        public boolean _checkVictoryDiagonalForward() {
            int diagsNumber = _rows + _cols - 1;

            // Iterate on diagonals from left to right
            for (int diagIndex = 0; diagIndex < diagsNumber; ++diagIndex) {
                int discCounter = 0;
                int rowStart = Math.min(diagIndex, _rows - 1);
                int rowStop = Math.max(0, diagIndex - _cols + 1);
                for (int row = rowStart; row >= rowStop; --row) {
                    int col = diagIndex - row;

                    if (_board[row][col].getDiscColor() == _currentColor) {
                        ++discCounter;
                    } else {
                        discCounter = 0;
                    }

                    if (DISCS_IN_A_ROW_TO_WIN == discCounter) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * Check if there is a 4-streak of
         * discs in the same color in some diagonal
         * from right to left
         * @return true if the 4-streak is found, otherwise false
         */
        public boolean _checkVictoryDiagonalBackward() {
            // Iterate on diagonals from left to right
            for(int i = (_cols - 1); i >= 0; --i) {
                int discCounter = 0;
                for(int j = 0; j < _rows; ++j) {
                    int row = j;
                    int col = i + j;
                    if(col < _cols) {
                        if (_board[row][col].getDiscColor() == _currentColor) {
                            ++discCounter;
                        } else {
                            discCounter = 0;
                        }

                        if (4 == discCounter) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
            }

            for(int i = 1; i < _rows; i++) {
                int discCounter = 0;
                for(int j = i, k = 0; j < _rows && k < _cols; j++, k++) {
                    if (_board[j][k].getDiscColor() == _currentColor) {
                        ++discCounter;
                    } else {
                        discCounter = 0;
                    }

                    if (4 == discCounter) {
                        return true;
                    }
                }
            }

            return false;
        }

        /**
         * Checks if there is a 4-streak of discs
         * in the same color in some diagonal
         * @return true if the 4-streak found, otherwise false
         */
        private boolean _checkVictoryDiagonal() {
            return _checkVictoryDiagonalForward() || _checkVictoryDiagonalBackward();
        }

        /**
         * Checks if there is a 4-streak of discs
         * in the same color in some row, column or diagonal
         * @return true if the 4-streak found, otherwise false
         */
        private boolean _checkVictory() {
            return _checkVictoryRow() || _checkVictoryColumn() || _checkVictoryDiagonal();
        }

        /**
         * The button pressed, add the disc if can
         * and check for winning
         * @param e Event information
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (_hasWon) {
                return;
            }

            int row = _getAvailableSquareRow(_column);
            if (-1 == row) {
                JOptionPane.showMessageDialog(null, "The column is full");
                return;
            }

            // Add disc to the available square
            _board[row][_column].setDiscColor(_currentColor);

            repaint();

            // Check for victory
            _hasWon = _checkVictory();
            if (_hasWon) {
                String victory_msg = String.format("%s player has won", _getCurrentColorName());
                JOptionPane.showMessageDialog(null, victory_msg);
                return;
            }

            _swapCurrentColor();

            repaint();
        }
    }
}
