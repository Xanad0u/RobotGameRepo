import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class putCardAction extends AbstractAction {

	Card putCard;
	CardPanel host;
	private int loops = -1;
	
	public putCardAction(CardPanel host, Card card) {
		putCard = card;
		this.host = host;
	}
	
	public putCardAction(CardPanel host, int loops) {
		putCard = Card.RCARD;
		this.host = host;
		this.loops = loops;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(host.mouseInFrame) {
			if(loops == -1) host.cardList.get(host.focusedCard).makeCard(putCard, host.host);
			else host.cardList.get(host.focusedCard).makeCard(host.host, loops);
			host.repaint();
		}
	}

}
