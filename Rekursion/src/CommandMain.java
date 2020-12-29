
public enum CommandMain {
	QuarterTurnRight(Rotation.EAST, 0), HalfTurn(Rotation.SOUTH), QuarterTurnLeft(Rotation.WEST), Step(1),
	DoubleStep(2), StepBack(-1);

	private final Rotation rot;
	private final int steps;

	/**
	 * No Command should be a rotation and a movement
	 * 
	 * @param rot
	 * @param steps
	 */
	@Deprecated
	private CommandMain(Rotation rot, int steps) {
		this.rot = rot;
		this.steps = steps;
	}

	private CommandMain(int steps) {
		rot = Rotation.NORTH;
		this.steps = steps;
	}

	private CommandMain(Rotation rot) {
		this.rot = rot;
		steps = 0;
	}

	public Rotation rot() {
		return rot;
	}

	public int steps() {
		return steps;
	}

	public void act() {
		rotate();
		move();
	}

	public void rotate() {
		// TODO
		System.out.println("rotate " + rot);
	}

	public void move() {
		// TODO
		System.out.println("move " + steps);
	}

	public static void main(String[] args) {
		//System.out.println(Command.DoubleStep);
		//System.out.println(Command.QuarterTurnLeft);
		System.out.println(Rotation.NORTH.add(Turn.COUNTERCLOCKWISE));
		System.out.println(Rotation.WEST.add(Turn.CLOCKWISE));
		//Command.DoubleStep.act();
	}
}

enum Rotation {
	NORTH, EAST, SOUTH, WEST;

	public Rotation add(Turn rot) {
		return values()[(ordinal() + rot.dir() + 4) % 4];
	}
}

enum Turn {
	CLOCKWISE(1), COUNTERCLOCKWISE(-1);

	private int direction;
	
	Turn(int direction) {
		this.direction = direction;
	}
	
	public int dir() {
		return direction;
	}
}

enum Move {
	BACKWARD(-1), FORWARD(1), FASTFORWARD(2);

	private int step;
	
	Move(int step) {
		this.step = step;
	}
	
	public int step() {
		return step;
	}
}

enum Card {
	BACKCARD, FORWARDCARD, FASTFORWARDCARD, RTURNCARD, LTURNCARD, UTURNCARD, RCARD, EMPTY;
	
	public Card change(int dir) {
		int newPos = ordinal() + dir;
		newPos = (newPos + 8) % 8;

		return values()[newPos];  
	}
}

enum Command {
	MOVEBACKWARD, MOVEFORWARD, TURNRIGHT, TURNLEFT, INSERTRECUSION;
}

enum State {
	SET, SELECTED, EMPTY;
}

enum Tile {
	EMPTY, BLOCK, HOLE, START, FLAG;
	
	public Tile change(int dir) {
		int newPos = ordinal() + dir;
		newPos = (newPos + 5) % 5;

		return values()[newPos];     
	}
}

enum Link {
	MASTER(-1), SLAVE(-1);

	private int linkedTo;
	
	Link(int linkedTo) {
		this.linkedTo = linkedTo;
	}
	
	public void linkTo(int linkTo) {
		linkedTo = linkTo;
	}
	
	public int getLinkedTo(Link link) {
		return linkedTo;
	}
}