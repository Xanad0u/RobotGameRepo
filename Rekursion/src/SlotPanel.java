import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SlotPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
	public Card[] types;
	public int[] loops;
	public int slotAmount;
	Slot[] slots;
	int[] origin;
	
	private int gap;
	
	private boolean isEditor = false;
	
	public ArrayList<Slot> slotList;
	private int mousePos;
	private int focusedSlot;
	
	
	public SlotPanel(int slotAmountIn) {
		slotAmount = slotAmountIn;
		types = new Card[slotAmount];
		loops = new int[slotAmount];
		origin = new int[slotAmount];
		slots = new Slot[slotAmount];
		for(int i = 0; i < slotAmount; i++) {
			types[i] = Card.EMPTY;
			loops[i] = 0;
			origin[i] = -1;
			slots[i] = new Slot();
		}
		
		addMouseListener(this);
	}
	
	public SlotPanel() {
		isEditor = true;
		slotAmount = 1;
		
		slotList = new ArrayList<>();
		slotList.add(new Slot());
		
		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBounds(Main.xNull, Main.yNull  + Main.fullSize + Main.size / 2, Main.fullSize, (int) (Main.size * Main.cardRatio));
		
		if(!isEditor) {
			gap = (Main.fullSize - slotAmount * Main.size) / (slotAmount + 1);
					
			int xPos;
			
			for(int i = 0; i < slotAmount; i++) {
				
				xPos = (gap * (i + 1) + Main.size * i);
				
				slots[i].draw(g, xPos, 0);
			}
		}
		else {
			gap = (Main.fullSize - slotList.size() * Main.size) / (slotList.size() + 1);
			
			int xPos;
			
			for(int i = 0; i < slotList.size(); i++) {
				
				xPos = (gap * (i + 1) + Main.size * i);
				
				slotList.get(i).draw(g, xPos, 0);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(!isEditor) {
			int pos = e.getX() / (gap + Main.size);
					
			switch(slots[pos].state) {
			case SET:
				slots[pos].clear();
				Main.cardPane.cards[origin[pos]].state = State.SET;
				origin[pos] = -1;
				types[pos] = Card.EMPTY;
				loops[pos] = 0;
				
				repaint();
				Main.cardPane.repaint();
				break;
				
			case EMPTY:
				if(Main.cardPane.selected != -1) {
					origin[pos] = Main.cardPane.selected;
					Main.cardPane.selected = -1;
					Main.cardPane.cards[origin[pos]].state = State.EMPTY;
					
					CardObject movingCard = Main.cardPane.getCard(origin[pos]);
					
					types[pos] = movingCard.getType();
					
					System.out.println("moving card type: " + movingCard.getType().toString());
					
					if(types[pos] == Card.RCARD) loops[pos] = movingCard.getLoops();
					
					if(types[pos] != Card.RCARD) slots[pos].makeCard(types[pos]);
					else slots[pos].makeCard(loops[pos]);
					
					repaint();
					Main.cardPane.repaint();
					
					boolean full = true;
					for(int i = 0; i < slotAmount; i++) {
						if(slots[i].state == State.EMPTY) full = false;
					}
					
					if(full == true) Main.robotPane.execute(Main.makeCommandsReal(Main.slotPane.types, Main.slotPane.loops));
					
					break;
				}
				
			default:	//should not be called
				System.out.println("ERROR - Corrupted slot, pos: " + pos);
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() == -1) {
			if (slotList.size() < Main.cardPane.cardList.size()) slotList.add(new Slot(slotList.size()));
		}
		else if(slotList.size() > 0) slotList.remove(slotList.size() - 1);
		
		focusedSlot = (int) ((mousePos - 0.5 * gap) / (gap + Main.size));
		
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos = e.getX();
		gap = (Main.fullSize - slotList.size() * Main.size) / (slotList.size() + 1);
		focusedSlot = (int) ((mousePos - 0.5 * gap) / (gap + Main.size));
	}
	
	public void cutSlotsTo(int nSlots) {
		for (int i = slotList.size(); i > nSlots; i--) {
			slotList.remove(slotList.size() - 1);
		}
		repaint();
	}
}
