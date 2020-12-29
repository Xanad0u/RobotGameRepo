import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class PutCardAction extends AbstractAction {

	Card putCard;
	CardPanel host;
	private int loops = -1;
	
	public PutCardAction(CardPanel host, Card card) {
		putCard = card;
		this.host = host;
	}
	
	public PutCardAction(CardPanel host, int loops) {
		putCard = Card.RCARD;
		this.host = host;
		this.loops = loops;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(host.mouseInFrame) {
			if(loops == -1) host.cardList.get(host.focusedCard).makeCard(putCard);
			else host.cardList.get(host.focusedCard).makeCard(loops);
			host.repaint();
		}
	}

}
