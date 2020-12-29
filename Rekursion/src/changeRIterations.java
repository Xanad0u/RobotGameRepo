import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class changeRIterations extends AbstractAction {

	Card putCard;
	CardPanel host;
	private int loops;
	private int change;

	public changeRIterations(CardPanel host, int change) {
		putCard = Card.RCARD;
		this.host = host;
		this.change = change;
		this.loops = host.cardList.get(host.focusedCard).rLoops;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(host.mouseInFrame) {
			
			this.loops = host.cardList.get(host.focusedCard).rLoops;
			
			System.out.println("current loops: " + loops);
			System.out.println("change: " + change);
			System.out.println("updated loops " + (loops + change));
			
			if(loops + change < 0) host.cardList.get(host.focusedCard).makeCard(host.host, 9);
			else if (loops + change > 9) host.cardList.get(host.focusedCard).makeCard(host.host, 0);
			else host.cardList.get(host.focusedCard).makeCard(host.host, loops + change);
			host.repaint();
		}
	}

}
