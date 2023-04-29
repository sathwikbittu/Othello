package project.game.Othello.model;

public class Movesinfo {

	private int player;
    private int x;
    private int y;
    
    public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}
	public int getX() {
		return x;
	}
	public Movesinfo(int player, int x, int y) {
		super();
		this.player = player;
		this.x = x;
		this.y = y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
