import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class CardPanel extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener {

	private final int lineSize = 4;
	
	int cardAmount;
	CardObject[] cards;
	StageFrame host;
	
	private boolean isEditor = false;
	
	boolean mouseInFrame = true;
	
	ArrayList<Slot> cardList;
	
	public int selected = -1;
	
	int gap = 0;

	private int mousePos;

	int focusedCard;
	
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
		addMouseMotionListener(this);
	}
	
	public CardPanel(StageFrame hostIn) {
		isEditor = true;
		host = hostIn;
		
		cardList = new ArrayList<>();
		cardList.add(new Slot(host.cardSlot));
		
		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Q"), "putBackward");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "putForward");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("E"), "putFastforward");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("R"), "putRTurn");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("T"), "putLTurn");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Z"), "putUTurn");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("U"), "putRCard");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("I"), "clearCard");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("PLUS"), "increaseRIterations");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("MINUS"), "decreaseRIterations");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "increaseRIterations");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "decreaseRIterations");
		getActionMap().put("putBackward", new putCardAction(this, Card.BACKCARD));
		getActionMap().put("putForward", new putCardAction(this, Card.FORWARDCARD));
		getActionMap().put("putFastforward", new putCardAction(this, Card.FASTFORWARDCARD));
		getActionMap().put("putRTurn", new putCardAction(this, Card.RTURNCARD));
		getActionMap().put("putLTurn", new putCardAction(this, Card.LTURNCARD));
		getActionMap().put("putUTurn", new putCardAction(this, Card.UTURNCARD));
		getActionMap().put("putRCard", new putCardAction(this, 0));
		getActionMap().put("clearCard", new putCardAction(this, Card.EMPTY));
		getActionMap().put("increaseRIterations", new changeRIterations(this, 1));
		getActionMap().put("decreaseRIterations", new changeRIterations(this, -1));
		
	}

	public CardObject getCard(int index) {
		return cards[index];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBounds(host.xNull - lineSize, host.yNull - 2 * host.size - lineSize, host.fullSize + 2 * lineSize, (int) (host.size * host.cardRatio) + 2 * lineSize);
		
		int xPos;
		
		if(!isEditor) {
			gap = (host.fullSize - cardAmount * host.size) / (cardAmount + 1);

			for(int i = 0; i < cardAmount; i++) {
				
				xPos = (gap * (i + 1) + host.size * i);
				
				cards[i].draw(g, xPos, lineSize, host.size);
	
			}
		}
		
		else {
			gap = (host.fullSize - cardList.size() * host.size) / (cardList.size() + 1);
			
			for(int i = 0; i < cardList.size(); i++) {
				
				xPos = (gap * (i + 1) + host.size * i);

				cardList.get(i).draw(g, xPos, lineSize, host.size);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!isEditor) {
			if(focusedCard <= cardAmount) {
				if(cards[focusedCard].state != State.EMPTY) {
					cards[focusedCard].state = State.SELECTED;
				
					if(selected != -1) cards[selected].state = State.SET;
					
					if(selected == focusedCard) selected = -1;
					else selected = focusedCard;
				}
			}
		}
		else {
			if(focusedCard <= cardList.size()) {
				if(cardList.get(focusedCard).type.change(1) != Card.RCARD) cardList.get(focusedCard).makeCard(cardList.get(focusedCard).type.change(1), host);
				else cardList.get(focusedCard).makeCard(host, 0);
			}
		}
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInFrame = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInFrame = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() == -1) cardList.add(new Slot(host.cardSlot));
		else if(cardList.size() > 0) cardList.remove(cardList.size() - 1);
		
		focusedCard = (int) ((mousePos - 0.5 * gap) / (gap + host.size));
		
		host.slotPane.cutSlotsTo(cardList.size());
		
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(!isEditor) {
			mousePos = e.getX();
			gap = (host.fullSize - cardAmount * host.size) / (cardAmount + 1);
			focusedCard = (int) ((mousePos - 0.5 * gap) / (gap + host.size));
		}
		else {
			mousePos = e.getX();
			gap = (host.fullSize - cardList.size() * host.size) / (cardList.size() + 1);
			focusedCard = (int) ((mousePos - 0.5 * gap) / (gap + host.size));
		}
	}
}

