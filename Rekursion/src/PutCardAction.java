import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class PutCardAction extends AbstractAction {

	Card putCard;
	private int loops = -1;
	
	public PutCardAction(Card card) {
		putCard = card;
	}
	
	public PutCardAction(int loops) {
		putCard = Card.RCARD;
		this.loops = loops;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(Main.cardPane.mouseInFrame) {
			if(loops == -1) Main.cardPane.cardList.get(Main.cardPane.focusedCard).makeCard(putCard, State.SET);
			else Main.cardPane.cardList.get(Main.cardPane.focusedCard).makeCard(loops, State.SET);
			Main.cardPane.repaint();
			Main.slotPane.updateLinked();
		}
	}

}
