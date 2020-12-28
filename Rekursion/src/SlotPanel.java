import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class SlotPanel extends JPanel implements MouseListener {
	public Card[] types;
	public int[] loops;
	public int slotAmount;
	Slot[] slots;
	int[] origin;
	
	private int gap;
	
	CardPanel cardPanel;
	StageFrame host;
	
	
	public SlotPanel(int slotAmountIn, CardPanel cardPanelIn, StageFrame hostIn) {
		slotAmount = slotAmountIn;
		host = hostIn;
		types = new Card[slotAmount];
		loops = new int[slotAmount];
		origin = new int[slotAmount];
		slots = new Slot[slotAmount];
		for(int i = 0; i < slotAmount; i++) {
			types[i] = Card.EMPTY;
			loops[i] = 0;
			origin[i] = -1;
			slots[i] = new Slot(host.cardSlot);
		}
		
		cardPanel = cardPanelIn;
		
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBounds(host.xNull, host.yNull  + host.fullSize + host.size / 2, host.fullSize, (int) (host.size * host.cardRatio));
		
		gap = (host.fullSize - slotAmount * host.size) / (slotAmount + 1);
				
		int xPos;
		
		for(int i = 0; i < slotAmount; i++) {
			
			xPos = (gap * (i + 1) + host.size * i);
			
			slots[i].draw(g, xPos, 0, host.size);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int pos = e.getX() / (gap + host.size);
				
		switch(slots[pos].state) {
		case SET:
			slots[pos].clear();
			cardPanel.cards[origin[pos]].state = State.SET;
			origin[pos] = -1;
			types[pos] = Card.EMPTY;
			loops[pos] = 0;
			
			repaint();
			cardPanel.repaint();
			break;
			
		case EMPTY:
			if(cardPanel.selected != -1) {
				origin[pos] = cardPanel.selected;
				cardPanel.selected = -1;
				cardPanel.cards[origin[pos]].state = State.EMPTY;
				
				CardObject movingCard = cardPanel.getCard(origin[pos]);
				
				types[pos] = movingCard.getType();
				
				System.out.println("moving card type: " + movingCard.getType().toString());
				
				if(types[pos] == Card.RCARD) loops[pos] = movingCard.getLoops();
				
				if(types[pos] != Card.RCARD) slots[pos].makeCard(types[pos], host);
				else slots[pos].makeCard(host, loops[pos]);
				
				repaint();
				cardPanel.repaint();
				
				boolean full = true;
				for(int i = 0; i < slotAmount; i++) {
					if(slots[i].state == State.EMPTY) full = false;
				}
				
				if(full == true) host.execute();
				
				break;
			}
			
		default:	//should not be called
			System.out.println("ERROR - Corrupted slot, pos: " + pos);
			break;
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
