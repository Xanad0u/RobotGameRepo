import java.awt.image.BufferedImage;

public class Slot extends CardObject {

	public Slot(BufferedImage emptyImgIn) {
		super(emptyImgIn);

		state = State.EMPTY;
	}
	
	public void makeCard(Card typeIn, StageFrame host) {
		type = typeIn;
		switch(typeIn) {
		case BACKCARD:
			img = host.backCard;
			break;
		case FORWARDCARD:
			img = host.forwardCard;
			break;
		case FASTFORWARDCARD:
			img = host.fastForwardCard;
			break;
		case RTURNCARD:
			img = host.rTurnCard;
			break;
		case LTURNCARD:
			img = host.lTurnCard;
			break;
		case UTURNCARD:
			img = host.uTurnCard;
			break;
		}
		state = State.SET;
	}
	
	public void makeCard(StageFrame host, int loops) {
		type = Card.RCARD;
		img = host.rCard;
		rLoops = loops;
		state = State.SET;
	}
	
	public void clear() {
		state = State.EMPTY;
	}
}
