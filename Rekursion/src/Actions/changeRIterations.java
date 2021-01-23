package Actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Enums.Card;
import Enums.State;
import Globals.Main;

public class changeRIterations extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2455384751975056281L;
	
	Card putCard;
	private int loops;
	private int change;

	public changeRIterations(int change) {
		putCard = Card.RCARD;
		this.change = change;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(Main.cardPane.mouseInFrame) {
			
			this.loops = Main.cardPane.cardList.get(Main.cardPane.focusedCard).rLoops;
			
			System.out.println("current loops: " + loops);
			System.out.println("change: " + change);
			System.out.println("updated loops " + (loops + change));
			
			if(loops + change < 0) Main.cardPane.cardList.get(Main.cardPane.focusedCard).makeCard(9, State.SET);
			else if (loops + change > 9) Main.cardPane.cardList.get(Main.cardPane.focusedCard).makeCard(0, State.SET);
			else Main.cardPane.cardList.get(Main.cardPane.focusedCard).makeCard(loops + change, State.SET);
			Main.cardPane.repaint();
			Main.slotPane.updateLinked();
		}
	}

}
