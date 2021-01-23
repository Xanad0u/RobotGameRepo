package Enums;

public enum Move {
	BACKWARD(-1), FORWARD(1), FASTFORWARD(2);

	private int step;
	
	Move(int step) {
		this.step = step;
	}
	
	public int step() {
		return step;
	}
}