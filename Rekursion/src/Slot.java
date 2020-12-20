import java.awt.image.BufferedImage;

public class Slot extends Card {

	public Slot(BufferedImage emptyImgIn) {
		super(emptyImgIn);

		state = 2;
	}
	
	public void makeCard(int typeIn, StageFrame host) {
		type = typeIn;
		switch(typeIn) {
		case 1:
			img = host.backCard;
			break;
		case 2:
			img = host.forwardCard;
			break;
		case 3:
			img = host.fastForwardCard;
			break;
		case 4:
			img = host.rTurnCard;
			break;
		case 5:
			img = host.lTurnCard;
			break;
		case 6:
			img = host.uTurnCard;
			break;
		}
		state = 0;
	}
	
	public void makeCard(StageFrame host, int loops) {
		type = 7;
		img = host.rCard;
		rLoops = loops;
		state = 0;
	}
	
	public void clear() {
		state = 2;
	}
}
