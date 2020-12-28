import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class CardPanel extends JPanel implements MouseListener {

	private final int lineSize = 4;
	
	int cardAmount;
	CardObject[] cards;
	StageFrame host;
	
	public int selected = -1;
	
	int gap = 0;
	
	public CardPanel(Card[] cardArray, int cardAmountIn, StageFrame hostIn) {
		host = hostIn;
		cardAmount = cardAmountIn;
		
		cards = new CardObject[cardAmount];
		
		int j = 0;
		for(int i = 0; i < cardAmount; i++) {

			switch(cardArray[j]) {
			case BACKCARD:
				cards[i] = new CardObject(host.backCard, host.cardSlot, Card.BACKCARD);
				break;
			case FORWARDCARD:
				cards[i] = new CardObject(host.forwardCard, host.cardSlot, Card.FORWARDCARD);
				break;
			case FASTFORWARDCARD:
				cards[i] = new CardObject(host.fastForwardCard, host.cardSlot, Card.FASTFORWARDCARD);
				break;
			case RTURNCARD:
				cards[i] = new CardObject(host.rTurnCard, host.cardSlot, Card.RTURNCARD);
				break;
			case LTURNCARD:
				cards[i] = new CardObject(host.lTurnCard, host.cardSlot, Card.LTURNCARD);
				break;
			case UTURNCARD:
				cards[i] = new CardObject(host.uTurnCard, host.cardSlot, Card.UTURNCARD);
				break;
			case RCARD:
				j++;
				cards[i] = new CardObject(host.rCard, host.cardSlot, Card.RCARD, cardArray[j].ordinal() + 1);
				break;

			default:		//should not be called
				cards[i] = new CardObject(host.cardSlot);
				break;
			}
			j++;
		}
		
		addMouseListener(this);
	}
	
	public CardObject getCard(int index) {
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
			if(cards[pos].state != State.EMPTY) {
				cards[pos].state = State.SELECTED;
			
				if(selected != -1) cards[selected].state = State.SET;
				
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

