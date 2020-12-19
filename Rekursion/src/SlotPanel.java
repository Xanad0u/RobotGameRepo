import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class SlotPanel extends JPanel implements MouseListener {
	public int[] types;
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
		types = new int[slotAmount];
		loops = new int[slotAmount];
		origin = new int[slotAmount];
		slots = new Slot[slotAmount];
		for(int i = 0; i < slotAmount; i++) {
			types[i] = 0;
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
		case 0:
			slots[pos].clear();
			cardPanel.cards[origin[pos]].state = 0;
			origin[pos] = -1;
			types[pos] = 0;
			loops[pos] = 0;
			
			repaint();
			cardPanel.repaint();
			break;
		case 2:
			if(cardPanel.selected != -1) {
				origin[pos] = cardPanel.selected;
				cardPanel.selected = -1;
				cardPanel.cards[origin[pos]].state = 2;
				
				Card movingCard = cardPanel.getCard(origin[pos]);
				
				types[pos] = movingCard.getType();
				
				if(types[pos] == 7) loops[pos] = movingCard.getLoops();
				
				if(types[pos] != 7) slots[pos].makeCard(types[pos], host);
				else slots[pos].makeCard(host, loops[pos]);
				
				repaint();
				cardPanel.repaint();
				break;
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
