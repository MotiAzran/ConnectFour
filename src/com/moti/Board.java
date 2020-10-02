package com.moti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel {

    private final Color PLAYER1_COLOR = Color.BLUE;
    private final String PLAYER1_COLOR_NAME = "Blue";
    private final Color PLAYER2_COLOR = Color.RED;
    private final String PLAYER2_COLOR_NAME = "Red";
    private int _rows;
    private int _cols;
    private Square[][] _board;
    private JButton[] _cols_buttons;
    private Color _current_color;
    private boolean _has_won;


    Board(int rows, int cols) {
        _rows = rows;
        _cols = cols;
        _current_color = PLAYER1_COLOR;
        _has_won = false;

        setLayout(new GridLayout(_rows+1, _cols));

        // Add board squares to panel
        _board = new Square[_rows][_cols];
        for (int i = 0; i < _rows; ++i) {
            for (int j = 0; j < _cols; ++j) {
                _board[i][j] = new Square();
                add(_board[i][j]);
            }
        }

        // Add adding disc buttons to panel
        _cols_buttons = new JButton[_cols];
        for (int i = 0; i < _cols; ++i) {
            _cols_buttons[i] = new JButton(Integer.toString(i+1));
            _cols_buttons[i].setBackground(_current_color);
            _cols_buttons[i].setForeground(Color.BLACK);
            _cols_buttons[i].addActionListener(new BoardButtonListener(i));

            add(_cols_buttons[i]);
        }
    }

    public void clear_board() {
        for (int i = 0; i < _rows; ++i) {
            for (int j = 0; j < _cols; ++j) {
                _board[i][j].set_disc_color(Color.WHITE);
            }
        }

        // Set members to their default values
        _current_color = PLAYER1_COLOR;
        _has_won = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set buttons color to the current color
        _set_buttons_color();
    }

    private String _get_current_color_name() {
        if (PLAYER1_COLOR == _current_color) {
            return PLAYER1_COLOR_NAME;
        } else {
            return PLAYER2_COLOR_NAME;
        }
    }

    private void _set_buttons_color() {
        for (int i = 0; i < _cols; ++i) {
            _cols_buttons[i].setBackground(_current_color);
        }
    }

    private class BoardButtonListener implements ActionListener {

        int _column;

        BoardButtonListener(int column) {
            _column = column;
        }

        private int _get_available_square_row(int col_index) {
            // Search for an empty square
            for (int i = (_rows - 1); i >= 0; --i) {
                if (Color.WHITE == _board[i][col_index].get_disc_color()) {
                    return i;
                }
            }

            return -1;
        }

        private void _swap_current_color() {
            if (PLAYER1_COLOR == _current_color) {
                _current_color = PLAYER2_COLOR;
            } else {
                _current_color = PLAYER1_COLOR;
            }
        }

        private boolean _check_victory_row() {
            for (int i = 0; i < _rows; ++i) {
                int disc_counter = 0;
                for (int j = 0; j < _cols; ++j) {
                    if (_board[i][j].get_disc_color() == _current_color) {
                        ++disc_counter;
                    } else {
                        disc_counter = 0;
                    }

                    if (4 == disc_counter) {
                        return true;
                    }
                }
            }

            return false;
        }

        private boolean _check_victory_column() {
            for (int i = 0; i < _cols; ++i) {
                int disc_counter = 0;
                for (int j = 0; j < _rows; ++j) {
                    if (_board[j][i].get_disc_color() == _current_color) {
                        ++disc_counter;
                    } else {
                        disc_counter = 0;
                    }

                    if (4 == disc_counter) {
                        return true;
                    }
                }
            }

            return false;
        }

        public boolean _check_victory_diagonal_forward() {
            int diags_number = _rows + _cols - 1;

            // Iterate on diagonals from left to right
            for (int diag_index = 0; diag_index < diags_number; ++diag_index) {
                int disc_counter = 0;
                int row_start = Math.min(diag_index, _rows - 1);
                int row_stop = Math.max(0, diag_index - _cols + 1);
                for (int row = row_start; row >= row_stop; --row) {
                    int col = diag_index - row;

                    if (_board[row][col].get_disc_color() == _current_color) {
                        ++disc_counter;
                    } else {
                        disc_counter = 0;
                    }

                    if (4 == disc_counter) {
                        return true;
                    }
                }
            }

            return false;
        }

        public boolean _check_victory_diagonal_backword() {
            int diags_number = _rows + _cols - 1;

            // Iterate on diagonals from left to right
            for(int i = (_cols - 1); i >= 0; --i) {
                int disc_counter = 0;
                for(int j = 0; j < _rows; ++j) {
                    int row = j;
                    int col = i + j;
                    if(col < _cols) {
                        if (_board[row][col].get_disc_color() == _current_color) {
                            ++disc_counter;
                        } else {
                            disc_counter = 0;
                        }

                        if (4 == disc_counter) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
            }

            for(int i = 1; i < _rows; i++) {
                int disc_counter = 0;
                for(int j = i, k = 0; j < _rows && k < _cols; j++, k++) {
                    if (_board[j][k].get_disc_color() == _current_color) {
                        ++disc_counter;
                    } else {
                        disc_counter = 0;
                    }

                    if (4 == disc_counter) {
                        return true;
                    }
                }
            }

            return false;
        }

        private boolean _check_victory_diagonal() {
            return _check_victory_diagonal_forward() || _check_victory_diagonal_backword();
        }

        private boolean _check_victory() {
            return _check_victory_row() || _check_victory_column() || _check_victory_diagonal();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (_has_won) {
                return;
            }

            int row = _get_available_square_row(_column);
            if (-1 == row) {
                JOptionPane.showMessageDialog(null, "The column is full");
                return;
            }

            // Add disc to the available square
            _board[row][_column].set_disc_color(_current_color);

            repaint();

            _has_won = _check_victory();
            if (_has_won) {
                String victory_msg = String.format("%s player has won", _get_current_color_name());
                JOptionPane.showMessageDialog(null, victory_msg);
                return;
            }

            _swap_current_color();

            repaint();
        }
    }
}
