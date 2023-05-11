package com.Cs681.Game.Model;

import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class GameMoves {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private List<Move> moves;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Move> getMoves() {
		return moves;
	}
	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}

}
