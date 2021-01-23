package Enums;

public enum Tile {
	EMPTY, BLOCK, HOLE, START, FLAG;
	
	public Tile change(int dir) {
		int newPos = ordinal() + dir;
		newPos = (newPos + 5) % 5;

		return values()[newPos];     
	}
}