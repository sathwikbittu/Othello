package project.game.Othello.service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import project.game.Othello.model.Movesinfo;
import project.game.Othello.model.Othelloroom;
import project.game.Othello.model.Player;

@Service
public class Playothello {

	private static Othelloroom othelloroom = Othelloroom.getInstance();
	
	public Othelloroom playerInfo(String name) {
	    Player P_a = othelloroom.getPlayerA();
	    Player P_b = othelloroom.getPlayerB();
	    int numPlayers = 0;
	    if (P_a != null) numPlayers++;
	    if (P_b != null) numPlayers++;
	    if (numPlayers == 2) {
	        // room is full, cannot add another player
	        return othelloroom;
	    } else {
	        // add new player to the room
	        int playerNum = (P_a == null) ? 1 : -1;
	        Player newPlayer = new Player(name, playerNum);
	        if (P_a == null) {
	            othelloroom.setPlayerA(newPlayer);
	        } else {
	            othelloroom.setPlayerB(newPlayer);
	        }
	        return othelloroom;
	    }
	}
	public void startGame() {
        if (othelloroom.isFinished()) {
        	othelloroom.reset();
        }
        Optional<int[][]> next = Optional.of(calcNextMoves(othelloroom.getTurn()));
        next.ifPresent(othelloroom::setNext);
}


    public Othelloroom move(Movesinfo details) throws Exception {
        if (!isItPlayersTurn(details)) {
            throw new Exception("It's not your turn");
        }
        if (!isMoveValid(details)) {
            throw new Exception("It's not a valid movement");
        }

        doMove(othelloroom.getBoard(), details);

        if (isGameFinished()) {
        	othelloroom.setFinished(true);
        } else {
            setTurn(details);
            othelloroom.setNext(calcNextMoves(othelloroom.getTurn()));
        }

        return othelloroom;
    }

    private boolean doMove(int[][] board, Movesinfo  details) {
    	// Create a copy of the board
    	int[][] target = new int[board.length][board[0].length];
    	for (int i = 0; i < board.length; i++) {
    	    for (int j = 0; j < board[i].length; j++) {
    	        target[i][j] = board[i][j];
    	    }
    	}

    	// Flag to track whether any changes were made to the board
    	boolean changed = false;

    	// Iterate over the rows of the board
    	for (int i = 0; i < details.getX(); i++) {
    	    // Check if the current row contains the player's piece
    	    if (board[i][details.getY()] == details.getPlayer()) {
    	        // Iterate over the remaining rows in the same column
    	        for (int j = i + 1; j < details.getX(); j++) {
    	            // If a non-opponent piece is found, skip to the next row
    	            if (board[j][details.getY()] != -details.getPlayer()) {
    	                continue;
    	            }
    	            // Otherwise, update the target board to contain the player's piece
    	            target[j][details.getY()] = details.getPlayer();
    	            changed = true;
    	        }
    	    }
    	}

    	outer: for (int i = 0; i < details.getX(); i++) {
    	    int row = 7 - i;
    	    if (board[row][details.getY()] == details.getPlayer()) {
    	        for (int j = row + 1; j < details.getX(); j++) {
    	            if (board[7 - j][details.getY()] != -details.getPlayer()) {
    	                continue outer;
    	            }
    	        }
    	        for (int j = row + 1; j < details.getX(); j++) {
    	            target[7 - j][details.getY()] = details.getPlayer();
    	            changed = true;
    	        }
    	    }
    	}

    	outer: for (int i = 7; i > details.getY(); i--) {
            if (board[details.getX()][i] == details.getPlayer()) {
                for (int j = i-1; j > details.getY(); j--) {
                    if (board[details.getX()][j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i-1; j > details.getY(); j--) {
                    target[details.getX()][j] = details.getPlayer();
                    changed = true;
                }
            }
        }

        outer: for (int i = 7; i > details.getY(); i--) {
            if (board[details.getX()][i] == details.getPlayer()) {
                for (int j = i-1; j > details.getY(); j--) {
                    if (board[details.getX()][j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i-1; j > details.getY(); j--) {
                    target[details.getX()][j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        int min = Math.min(details.getX(), details.getY());
        int startI = details.getX() - min;
        int startJ = details.getY() - min;

        outer: for (int i = 0; i < min; i++) {
            if (board[startI + i][startJ + i] == details.getPlayer()) {
                for (int j = i+1; j < min; j++) {
                    if (board[startI + j][startJ + j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < min; j++) {
                    target[startI + j][startJ + j] = details.getPlayer();
                    changed = true;
                }
            }
        }

        int max = Math.max(details.getX(), details.getY());
        int endI = details.getX() + (7 - max);
        int endJ = details.getY() + (7 - max);
        outer: for (int i = 0; i < (7 - max); i++) {
            if (board[endI - i][endJ - i] == details.getPlayer()) {
                for (int j = i+1; j < (7 - max); j++) {
                    if (board[endI - j][endJ - j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < (7 - max); j++) {
                    target[endI - j][endJ - j] = details.getPlayer();
                    changed = true;
                }
            }
        }

        startI = Math.min(7, details.getX() + details.getY());
        startJ = details.getX() + details.getY() - startI;
        outer: for (int i = 0; i < (startI - details.getX()); i++) {
            if (board[startI - i][startJ + i] == details.getPlayer()) {
                for (int j = i+1; j < (startI - details.getX()); j++) {
                    if (board[startI - j][startJ + j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < (startI - details.getX()); j++) {
                    target[startI - j][startJ + j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        endI = startJ;
        endJ = startI;
        outer: for (int i = 0; i < (endJ - details.getY()); i++) {
            if (board[endI + i][endJ - i] == details.getPlayer()) {
                for (int j = i+1; j < (endJ - details.getY()); j++) {
                    if (board[endI + j][endJ - j] != -details.getPlayer()) {
                        continue outer;
                    }
                }
                for (int j = i+1; j < (endJ - details.getY()); j++) {
                    target[endI + j][endJ - j] = details.getPlayer();
                    changed = true;
                }
            }
        }
        if (changed) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    board[i][j] = target[i][j];
                }
            }
            board[details.getX()][details.getY()] = details.getPlayer();
        }
        return changed;

    }

    private int[][] calcNextMoves(int player) {
        int[][] next = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int[][] board = new int[8][8];
                for (int k = 0; k < 8; k++) {
                    for (int l = 0; l < 8; l++) {
                        board[k][l] = othelloroom.getBoard()[k][l];
                    }
                }
                if (board[i][j] == 0 && doMove(board, new Movesinfo(player, i, j))) {
                    next[i][j] = 2 * player;
                }
            }
        }
        return next;
    }


    private boolean isMoveValid(Movesinfo details) {
        int[][] nextMoves = calcNextMoves(details.getPlayer());
        return nextMoves[details.getX()][details.getY()] == 2*details.getPlayer();
    }

    private boolean isItPlayersTurn(Movesinfo details) {
        return othelloroom .getTurn() == details.getPlayer();
    }

    private boolean canOtherPlayerMove(int player) {
        int otherPlayer = -player;
        int[][] nextMoves = calcNextMoves(otherPlayer);
        for (int[] row : nextMoves) {
            for (int move : row) {
                if (move == 2 * otherPlayer) {
                    return true;
                }
            }
        }
        return false;
    }


    private void setTurn(Movesinfo details) {
        if (canOtherPlayerMove(details.getPlayer())) {
        	othelloroom .setTurn(-othelloroom.getTurn());
        }
    }

    private boolean isGameFinished() {
        return Arrays.stream(othelloroom .getBoard())
                .flatMapToInt(Arrays::stream)
                .noneMatch(item -> item == 0);
    }
}