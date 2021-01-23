package Enums;

public enum Turn {
	CLOCKWISE(1), COUNTERCLOCKWISE(-1);

	private int direction;
	
	Turn(int direction) {
		this.direction = direction;
	}
	
	public int dir() {
		return direction;
	}
}
