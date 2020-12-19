import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class CardPanel extends JPanel implements MouseListener {

	private final int lineSize = 4;
	
	int cardAmount;
	Card[] cards;
	StageFrame host;
	
	public int selected = -1;
	
	int gap = 0;
	
	public CardPanel(byte[] cardArray, int cardAmountIn, StageFrame hostIn) {
		host = hostIn;
		cardAmount = cardAmountIn;
		
		cards = new Card[cardAmount];
		
		int j = 0;
		for(int i = 0; i < cardAmount; i++) {

			switch(cardArray[j]) {
			case 1:
				cards[i] = new Card(host.backCard, host.cardSlot, 1);
				break;
			case 2:
				cards[i] = new Card(host.forwardCard, host.cardSlot, 2);
				break;
			case 3:
				cards[i] = new Card(host.fastForwardCard, host.cardSlot, 3);
				break;
			case 4:
				cards[i] = new Card(host.rTurnCard, host.cardSlot, 4);
				break;
			case 5:
				cards[i] = new Card(host.lTurnCard, host.cardSlot, 5);
				break;
			case 6:
				cards[i] = new Card(host.uTurnCard, host.cardSlot, 6);
				break;
			case 7:
				j++;
				cards[i] = new Card(host.rCard, host.cardSlot, 7, cardArray[j]);
				break;
			}
			j++;
		}
		
		addMouseListener(this);
	}
	
	public Card getCard(int index) {
		return cards[index];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBounds(host.xNull - lineSize, host.yNull - 2 * host.size - lineSize, host.fullSize + 2 * lineSize, (int) (host.size * host.cardRatio) + 2 * lineSize);
		
		gap = (host.fullSize - cardAmount * host.size) / (cardAmount + 1);
				
		int xPos;
		
		for(int i = 0; i < cardAmount; i++) {
			
			xPos = (gap * (i + 1) + host.size * i);
			
			cards[i].draw(g, xPos, lineSize, host.size);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int pos = e.getX() / (gap + host.size);
		if(pos <= cardAmount) {
			if(cards[pos].state != 2) {
				cards[pos].state = 1;
			
				if(selected != -1) cards[selected].state = 0;
				
				if(selected == pos) selected = -1;
				else selected = pos;
				
				repaint();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

