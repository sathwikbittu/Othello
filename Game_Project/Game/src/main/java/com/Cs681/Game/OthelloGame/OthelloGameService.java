package com.Cs681.Game.OthelloGame;

import org.springframework.stereotype.Service;

@Service
public class OthelloGameService {

    private static final int BOARD_SIZE = 8;

    private char[][] board;
    private char currentPlayer;

    public OthelloGameService() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = ' ';
            }
        }
        board[3][3] = 'W';
        board[3][4] = 'B';
        board[4][3] = 'B';
        board[4][4] = 'W';
        currentPlayer = 'B';
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean makeMove(int row, int col) {
        if (board[row][col] != ' ') {
            return false; // Invalid move, square already occupied
        }

        boolean validMove = false;

        // Check all 8 directions from the chosen square
        for (int rowInc = -1; rowInc <= 1; rowInc++) {
            for (int colInc = -1; colInc <= 1; colInc++) {
                if (rowInc == 0 && colInc == 0) {
                    continue; // Skip the current square
                }

                // Check if this direction contains at least one opponent piece that can be flipped
                int r = row + rowInc;
                int c = col + colInc;
                boolean foundOpponent = false;

                while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE) {
                    if (board[r][c] == ' ') {
                        break; // Reached an empty square, stop searching in this direction
                    } else if (board[r][c] != currentPlayer) {
                        foundOpponent = true;
                    } else if (foundOpponent) {
                        // Found a sequence of opponent pieces that can be flipped, so make the move
                        validMove = true;
                        flipDirection(row, col, rowInc, colInc);
                        break;
                    } else {
                        break; // Reached a sequence of own pieces with no opponent pieces in between, stop searching in this direction
                    }

                    // Move to the next square in this direction
                    r += rowInc;
                    c += colInc;
                }
            }
        }

        if (validMove) {
            board[row][col] = currentPlayer;
        }
        if (!hasValidMove()) {
            // Current player is out of valid moves, switch to other player
            currentPlayer = (currentPlayer == 'B') ? 'W' : 'B';
        }

        

        return validMove;
    }
    public boolean hasValidMove() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == ' ') {
                    // Check all 8 directions from the chosen square
                    for (int rowInc = -1; rowInc <= 1; rowInc++) {
                        for (int colInc = -1; colInc <= 1; colInc++) {
                            if (rowInc == 0 && colInc == 0) {
                                continue; // Skip the current square
                            }

                            // Check if this direction contains at least one opponent piece that can be flipped
                            int r = row + rowInc;
                            int c = col + colInc;
                            boolean foundOpponent = false;

                            while (r >= 0 && r < BOARD_SIZE && c >= 0 && c < BOARD_SIZE) {
                                if (board[r][c] == ' ') {
                                    break; // Reached an empty square, stop searching in this direction
                                } else if (board[r][c] != currentPlayer) {
                                    foundOpponent = true;
                                } else if (foundOpponent) {
                                    // Found a sequence of opponent pieces that can be flipped, so this is a valid move
                                    return true;
                                } else {
                                    break; // Reached a sequence of own pieces with no opponent pieces in between, stop searching in this direction
                                }

                                // Move to the next square in this direction
                                r += rowInc;
                                c += colInc;
                            }
                        }
                    }
                }
            }
        }

        // No valid moves found
        return false;
    }

    public void switchPlayer() {
        if (currentPlayer == 'B') {
            currentPlayer = 'W';
        } else {
            currentPlayer = 'B';
        }
    }
    private void flipDirection(int row, int col, int rowInc, int colInc) {
        int r = row + rowInc;
        int c = col + colInc;

        while (board[r][c] != currentPlayer) {
            board[r][c] = currentPlayer;
            r += rowInc;
            c += colInc;
        }
    }


    public char getWinner() {
        int blackCount = 0;
        int whiteCount = 0;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == 'B') {
                    blackCount++;
                } else if (board[row][col] == 'W') {
                    whiteCount++;
                }
            }
        }

        if (blackCount > whiteCount) {
            return 'B';
        } else if (whiteCount > blackCount) {
            return 'W';
        } else {
            return ' '; // Tie game
        }
    }
    public char getCurrentPlayer() {
    	return this.currentPlayer;
    }
}