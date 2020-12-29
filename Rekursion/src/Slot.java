import java.awt.image.BufferedImage;

public class Slot extends CardObject {

	Link link;
	StageFrame host;
	
	boolean isEditor = false;
	private int index;
	
	public Slot(BufferedImage emptyImgIn, StageFrame host) {	//TODO update to Slot(BufferedImage emptyImgIn, StageFrame host)
		super(emptyImgIn);
		
		this.host = host;
		state = State.EMPTY;
	}
	
	public Slot(BufferedImage emptyImgIn, StageFrame host, int index) {
		super(emptyImgIn);
		isEditor = true;

		this.host = host;
		this.index = index;
		
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
		
		if(link == Link.MASTER) host.slotPane.slotList.get(link.getLinkedTo(link)).update(typeIn);
	}
	
	public void makeCard(int loops) {
		type = Card.RCARD;
		img = host.rCard;
		rLoops = loops;
		state = State.SET;
		
		if(link == Link.MASTER) host.slotPane.slotList.get(link.getLinkedTo(link)).update(loops);
	}
	
	private void update(Card typeIn) {
		// TODO Auto-generated method stub
		
	}

	private void update(int loops) {
		// TODO Auto-generated method stub
		
	}

	public void clear() {
		state = State.EMPTY;
	}
	
	public void linkToCard(int linkTo) {
		link = Link.SLAVE;
		link.linkTo(linkTo);
		
		host.cardPane.cardList.get(linkTo).link = Link.MASTER;
		host.cardPane.cardList.get(linkTo).link.linkTo(index);
	}
}
