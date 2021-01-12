import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

public class SlotPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
	public Card[] types;
	public int[] loops;
	public int slotAmount;
	Slot[] slots;
	int[] origin;
	
	
	private int slotGap;
	
	private boolean isEditor = false;
	
	public ArrayList<Slot> slotList;
	public ArrayList<Integer> slotLinks;
	
	private int mousePos;
	private int focusedSlot;
	public int selectedSlot = -1;
	
	
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
		
		slotList = new ArrayList<Slot>();
		slotLinks = new ArrayList<Integer>();
		slotList.add(new Slot());
		slotLinks.add(null);
		
		addMouseWheelListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBounds(Main.xNull - Main.lineSize, Main.yNull  + Main.fullSize + Main.size / 2 - Main.lineSize, Main.fullSize + 2 * Main.lineSize, (int) (Main.size * Main.cardRatio) + 2 * Main.lineSize);
		
		if(!isEditor) {
			slotGap = (Main.fullSize - slotAmount * Main.size) / (slotAmount + 1);
					
			int xPos;
			
			for(int i = 0; i < slotAmount; i++) {
				
				xPos = (slotGap * (i + 1) + Main.size * i);
				
				slots[i].draw(g, xPos, Main.lineSize, -1);
			}
		}
		else {
			slotGap = (Main.fullSize - slotList.size() * Main.size) / (slotList.size() + 1);
			
			int xPos;
			
			for(int i = 0; i < slotList.size(); i++) {
				
				xPos = (slotGap * (i + 1) + Main.size * i);
				
				slotList.get(i).draw(g, xPos, Main.lineSize, slotLinks.get(i));
			}
			Main.stageEditorFrame.calculatePath();
		}
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		int pos = e.getX() / (slotGap + Main.size);
		
		if(!isEditor) {
					
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
					
					if(types[pos] != Card.RCARD) slots[pos].makeCard(types[pos], State.SET);
					else slots[pos].makeCard(loops[pos], State.SET);
					
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
		
		else {							//TODO fix click clearing slots
			if(selectedSlot != pos) {
				selectedSlot = pos;
				
				for (int i = 0; i < slotList.size(); i++) {
					if(i == selectedSlot) slotList.get(i).state = State.SELECTEDEMPTY;
					else if (slotList.get(i).state == State.SELECTEDEMPTY) slotList.get(i).state = State.EMPTY;
				}
			}
			else {
				slotList.get(selectedSlot).state = State.EMPTY;
				selectedSlot = -1;
			}
			repaint();
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
			if (slotList.size() < Main.cardPane.cardList.size()) {
				slotList.add(new Slot(slotList.size()));
				slotLinks.add(null);
			}
		}
		else if(slotList.size() > 0) {
			slotList.remove(slotList.size() - 1);
			slotLinks.remove(slotList.size() - 1);
		}
		
		focusedSlot = (int) ((mousePos - 0.5 * slotGap) / (slotGap + Main.size));
		
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousePos = e.getX();
		slotGap = (Main.fullSize - slotList.size() * Main.size) / (slotList.size() + 1);
		focusedSlot = (int) ((mousePos - 0.5 * slotGap) / (slotGap + Main.size));
	}
	
	public void cutSlotsTo(int nSlots) {
		for (int i = slotList.size(); i > nSlots; i--) {
			slotList.remove(slotList.size() - 1);
		}
		repaint();
	}

	public void updateLinked() {
		int k = 0;
		Slot master;
		for (Slot slave : slotList) {
			if(slotLinks.get(k) != null) {
				master = Main.cardPane.cardList.get(slotLinks.get(k));
				
				if(master.type == Card.RCARD)slave.makeCard(master.rLoops, State.LINKED);
				else slave.makeCard(master.type, State.LINKED);
			}
			k++;
		}
		repaint();
	}
}
