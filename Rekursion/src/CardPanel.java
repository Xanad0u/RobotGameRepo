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

	int cardAmount;
	public CardObject[] cards;
	
	private boolean isEditor = false;
	
	boolean mouseInFrame = false;
	
	ArrayList<Slot> cardList;
	
	public int selected = -1;
	
	int cardGap = 0;

	private int mousePos;

	public int focusedCard;
	
	public CardPanel(Card[] cardArray, int cardAmountIn) {
		cardAmount = cardAmountIn;
		
		cards = new CardObject[cardAmount];
		
		int j = 0;
		for(int i = 0; i < cardAmount; i++) {

			switch(cardArray[j]) {
			case BACKCARD:
				cards[i] = new CardObject(Card.BACKCARD);
				break;
			case FORWARDCARD:
				cards[i] = new CardObject(Card.FORWARDCARD);
				break;
			case FASTFORWARDCARD:
				cards[i] = new CardObject(Card.FASTFORWARDCARD);
				break;
			case RTURNCARD:
				cards[i] = new CardObject(Card.RTURNCARD);
				break;
			case LTURNCARD:
				cards[i] = new CardObject(Card.LTURNCARD);
				break;
			case UTURNCARD:
				cards[i] = new CardObject(Card.UTURNCARD);
				break;
			case RCARD:
				j++;
				cards[i] = new CardObject(Card.RCARD, cardArray[j].ordinal() + 1);
				break;

			default:		//should not be called
				cards[i] = new CardObject();
				break;
			}
			j++;
		}
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	
	public CardPanel() {
		isEditor = true;
		
		cardList = new ArrayList<>();
		cardList.add(new Slot());
		
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
		getActionMap().put("putBackward", new PutCardAction(Card.BACKCARD));
		getActionMap().put("putForward", new PutCardAction(Card.FORWARDCARD));
		getActionMap().put("putFastforward", new PutCardAction(Card.FASTFORWARDCARD));
		getActionMap().put("putRTurn", new PutCardAction(Card.RTURNCARD));
		getActionMap().put("putLTurn", new PutCardAction(Card.LTURNCARD));
		getActionMap().put("putUTurn", new PutCardAction(Card.UTURNCARD));
		getActionMap().put("putRCard", new PutCardAction(0));
		getActionMap().put("clearCard", new PutCardAction(Card.EMPTY));
		getActionMap().put("increaseRIterations", new changeRIterations(1));
		getActionMap().put("decreaseRIterations", new changeRIterations(-1));
	}

	public CardObject getCard(int index) {
		return cards[index];
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBounds(Main.xNull - Main.lineSize, Main.yNull - 2 * Main.size - Main.lineSize, Main.fullSize + 2 * Main.lineSize, (int) (Main.size * Main.cardRatio) + 2 * Main.lineSize);
		
		int xPos;
		
		if(!isEditor) {
			cardGap = (Main.fullSize - cardAmount * Main.size) / (cardAmount + 1);

			for(int i = 0; i < cardAmount; i++) {
				
				xPos = (cardGap * (i + 1) + Main.size * i);
				
				cards[i].draw(g, xPos, Main.lineSize, -1);
	
			}
		}
		
		else {
			cardGap = (Main.fullSize - cardList.size() * Main.size) / (cardList.size() + 1);
			
			for(int i = 0; i < cardList.size(); i++) {
				
				xPos = (cardGap * (i + 1) + Main.size * i);

				cardList.get(i).draw(g, xPos, Main.lineSize, -1);
				
				Main.slotPane.updateLinked();}
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
				repaint();
			}
		}
		else {
			if(Main.slotPane.selectedSlot == -1) {
				if(focusedCard <= cardList.size()) {
					if(cardList.get(focusedCard).type.change(1) != Card.RCARD) cardList.get(focusedCard).makeCard(cardList.get(focusedCard).type.change(1), State.SET);
					else cardList.get(focusedCard).makeCard(0, State.SET);
				}
			}
			else {
				Main.slotPane.slotLinks.set(Main.slotPane.selectedSlot, focusedCard);
				Main.slotPane.slotList.get(Main.slotPane.selectedSlot).state = State.LINKED;
				Main.slotPane.selectedSlot = -1;
			}
			
		repaint();
		Main.slotPane.updateLinked();
		}
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
		if(e.getWheelRotation() == -1) cardList.add(new Slot());
		else if(cardList.size() > 0) cardList.remove(cardList.size() - 1);
		
		focusedCard = (int) ((mousePos - 0.5 * cardGap) / (cardGap + Main.size));
		
		Main.slotPane.cutSlotsTo(cardList.size());
		
		repaint();
		Main.slotPane.updateLinked();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		if(!isEditor) {
			mousePos = e.getX();
			cardGap = (Main.fullSize - cardAmount * Main.size) / (cardAmount + 1);
			focusedCard = (int) ((mousePos - 0.5 * cardGap) / (cardGap + Main.size));
		}
		else {
			mousePos = e.getX();
			cardGap = (Main.fullSize - cardList.size() * Main.size) / (cardList.size() + 1);
			focusedCard = (int) ((mousePos - 0.5 * cardGap) / (cardGap + Main.size));
		}
	}
}

