package Enums;

public enum Rotation {
	NORTH, EAST, SOUTH, WEST;

	public Rotation add(Turn rot) {
		return values()[(ordinal() + rot.dir() + 4) % 4];
	}
}