package com.Cs681.Game.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name ="saveMoves")
public class Move {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int moveRow;
    private int moveColumn;
    private String player;
    private String gameId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getMoveRow() {
		return moveRow;
	}
	public void setMoveRow(int moveRow) {
		this.moveRow = moveRow;
	}
	public int getMoveColumn() {
		return moveColumn;
	}
	public void setMoveColumn(int moveColumn) {
		this.moveColumn = moveColumn;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	

}
