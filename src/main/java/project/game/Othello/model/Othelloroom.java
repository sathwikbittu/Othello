package project.game.Othello.model;



public class Othelloroom {
	private Player playerA;
    private Player playerB;
    private int[][] board = new int[8][8];
    private int[][] next = new int[8][8];
    private int turn = 1;
    private boolean finished = false;
    
    public Player getPlayerA() {
		return playerA;
	}

	public void setPlayerA(Player playerA) {
		this.playerA = playerA;
	}

	public Player getPlayerB() {
		return playerB;
	}

	public void setPlayerB(Player playerB) {
		this.playerB = playerB;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public int[][] getNext() {
		return next;
	}

	public void setNext(int[][] next) {
		this.next = next;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	

    private Othelloroom() {
        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = -1;
        board[3][4] = -1;
    }

    public void reset() {
        playerA = null;
        playerB = null;
        board = new int[8][8];
        board[3][3] = 1;
        board[4][4] = 1;
        board[4][3] = -1;
        board[3][4] = -1;
        next = new int[8][8];
        turn = 1;
        finished = false;
    }

    public static Othelloroom getInstance() {
        return othelloplay.instance;
    }

    private static class othelloplay {
        public static final Othelloroom instance = new Othelloroom();
    }
}
