import java.util.ArrayList;
import java.util.List;

public abstract class ExtendedCommand {

	public abstract void act(/* Turtle */);

	public List<ExtendedCommand> preAct(List<ExtendedCommand> coms) {
		return coms;
	};

	public static void main(String[] args) {
		List<ExtendedCommand> coms = new ArrayList<ExtendedCommand>();
		coms.add(new MoveCommand(1));
		coms.add(new TurnCommand(Rotation.WEST));
		coms.add(new RecursionCommand(3));
		coms.add(new MoveCommand(1));
		coms.add(new TurnCommand(Rotation.EAST));

		while (coms.stream().anyMatch(e -> e instanceof RecursionCommand)) {
			for (ExtendedCommand c : coms)
				if (c instanceof RecursionCommand) {
					coms = ((RecursionCommand) c).preAct(coms);
					break;
				}
		}

		for (ExtendedCommand c : coms)
			c.act();
	}
}

class MoveCommand extends ExtendedCommand {
	private final int steps;

	public MoveCommand(int steps) {
		this.steps = steps;
	}

	@Override
	public void act() {
		/*
		 * 
		 */
		System.out.println(steps);
	}
}

class TurnCommand extends ExtendedCommand {
	private final Rotation rot;

	public TurnCommand(Rotation rot) {
		this.rot = rot;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		System.out.println(rot);
	}
}

class RecursionCommand extends ExtendedCommand {

	private final int n;

	public RecursionCommand(int i) {
		n = i;
	}

	@Override
	public void act() {
		System.err.println("angvnsdgcbjmbgvwhnjm");
	}

	@Override
	public List<ExtendedCommand> preAct(List<ExtendedCommand> coms) {
		List<ExtendedCommand> first, last, first2, last2;
		first = coms.subList(0, coms.indexOf(this));
		last = coms.subList(coms.indexOf(this) + 1, coms.size());
		first2 = new ArrayList<>();
		last2 = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			first2.addAll(first);
			last2.addAll(last);
		}
		first2.addAll(last2);
		return first2;
	}

}
