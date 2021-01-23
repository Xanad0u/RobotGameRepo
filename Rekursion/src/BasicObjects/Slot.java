package BasicObjects;
import Enums.Card;
import Enums.State;
import Globals.Main;

public class Slot extends CardObject {

	
	boolean isEditor = false;
	public Slot() {
		super();

		state = State.EMPTY;
	}
	
	public Slot(int index) {
		super();
		isEditor = true;
		
		state = State.EMPTY;
	}
	

	public void makeCard(Card typeIn, State setToState) {
		type = typeIn;
		switch(typeIn) {
		case BACKCARD:
			img = Main.backCard;
			break;
		case FORWARDCARD:
			img = Main.forwardCard;
			break;
		case FASTFORWARDCARD:
			img = Main.fastForwardCard;
			break;
		case RTURNCARD:
			img = Main.rTurnCard;
			break;
		case LTURNCARD:
			img = Main.lTurnCard;
			break;
		case UTURNCARD:
			img = Main.uTurnCard;
			break;
			
		case EMPTY:		//called by isEditor == true CardPanel
			img = Main.cardSlot;
			break;
			
		default:	//should not be called
			break;
		}
		state = setToState;
		
	}
	
	public void makeCard(int loops, State setToState) {
		type = Card.RCARD;
		img = Main.rCard;
		rLoops = loops;
		state = setToState;
		
	}

	public void clear() {
		state = State.EMPTY;
	}
}
