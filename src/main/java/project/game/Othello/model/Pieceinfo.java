package project.game.Othello.model;

public enum Pieceinfo {
	
	WHITE(1),
	BLACK(-1);

	    Pieceinfo(int value) {
	        this.value = value;
	    }

	    private int value;

	    public int getValue() {
	        return value;
	    }

	    public void setValue(int value) {
	        this.value = value;
	    }
}
