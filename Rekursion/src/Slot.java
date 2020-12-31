import java.awt.image.BufferedImage;

public class Slot extends CardObject {

	StageFrame host;

	public Slot(BufferedImage emptyImgIn, StageFrame host) {
		super(emptyImgIn);
		
		this.host = host;
		state = State.EMPTY;
	}
	
	public void makeCard(Card typeIn) {
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
			
		case EMPTY:		//called by isEditor == true CardPanel
			img = host.cardSlot;
			break;
			
		default:	//should not be called
			break;
		}
		
		state = State.SET;

	}
	
	public void makeCard(int loops) {
		type = Card.RCARD;
		img = host.rCard;
		rLoops = loops;
		state = State.SET;

	}
	
	public void makeCardAndUpdate(Card typeIn) {
		makeCard(typeIn);
		updateLinked();
	}
	
	public void makeCardAndUpdate(int loops) {
		makeCard(loops);
		updateLinked();
	}
	
	public void clear() {
		state = State.EMPTY;
	}
}
