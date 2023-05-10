package com.Cs681.Game.OthelloGame;

public class OthelloResponse {
	  private int[][] board;
	  private boolean success;

	  public int[][] getBoard() {
	    return board;
	  }

	  public void setBoard(int[][] board) {
	    this.board = board;
	  }

	  public boolean isSuccess() {
	    return success;
	  }

	  public void setSuccess(boolean success) {
	    this.success = success;
	  }
	}

