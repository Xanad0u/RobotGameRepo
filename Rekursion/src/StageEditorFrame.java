import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class StageEditorFrame extends JFrame {

	public Tile[] tiles;
	public Card[] cards;
	public int[] loops;
	private Command[] commands;
	public Rotation robotRot;
	public byte[] initPos;
	//public int startTile = -1;
	//public int flagTile = -1;
	public int pointOfDeath;
	public ArrayList<byte[]> positions;
	
	pathPanel pathPane;
	
	public StageEditorFrame() throws IOException {	//constructor used by StageEditorFrame
		
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	//Make closing window exit the program
	setSize(1028, 1228);		//Set the initial size of the window
	setLayout(null);			//Make frame layout null
	setIconImage(Main.icon);	//Set the icon image
	setExtendedState(JFrame.MAXIMIZED_BOTH);	//Extend window
	setTitle("Stage");		//Set title to Stage
	setVisible(true);		//make frame visible

	Main.menu = new MenuPanel(this)  {	//Create popup menu
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);	//Clear all drawn
			
			this.setBounds(getContentPane().getWidth() / 2 - Main.menuWidth / 2, getContentPane().getHeight() / 2 - Main.menuHeight / 2, Main.menuWidth, Main.menuHeight);	//Set popup menu bounds
		}
	};
	
	Main.menu.setVisible(false);		//make popup menu invisible
	Main.menu.setBounds((int) (getContentPane().getWidth() / 2 - Main.menuWidth / 2), (int) (getContentPane().getHeight() / 2 - Main.menuHeight / 2), Main.menuWidth, Main.menuHeight);	//Set popup menu bounds


	Main.gridPane = new GridPanel(true);	//Constructing board pane
	
	Main.gridPane.setBounds(0, 0, 1, 1);		//Setting board size to more then zero, making it rescalable

	pathPane = new pathPanel();
	
	pathPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
	
	Main.menu.setBounds((int) (getContentPane().getWidth() / 2 - Main.menuWidth / 2), (int) (getContentPane().getHeight() / 2 - Main.menuHeight / 2), Main.menuWidth, Main.menuHeight);

	
	Main.cardPane = new CardPanel();
	
	Main.cardPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
	
	Main.slotPane = new SlotPanel();
	
	Main.slotPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
	
	
	add(Main.menu);		//Add menu to frame
	add(Main.cardPane);
	add(Main.slotPane);
	add(Main.gridPane);	//Add board pane to frame
	//add(pathPane);
	}
		
		
	public void calculatePath() {
		//try {
			updateToDependencies();
			
			pointOfDeath = -1;
			commands = new Command[Main.makeCommandsReal(cards, loops).length];
			commands = Main.makeCommandsReal(cards, loops);
			
			byte[] posTemp = new byte[2];
			
			positions = new ArrayList<byte[]>();
			
			//posTemp[0] = initPos[0];
			//posTemp[1] = initPos[1];
			positions.add(initPos);
			
			for (Command command : commands) {
				switch(command) {
				case MOVEBACKWARD:
					switch(getNextTile(Move.BACKWARD)) {
					case BLOCK:
						break;
					case HOLE:
						positions.add(getNextPos(Move.BACKWARD));
						if(pointOfDeath != -1) pointOfDeath = positions.size() - 1;
						break;
					default:	//EMPTY, START or FLAG
						positions.add(getNextPos(Move.BACKWARD));
						break;
					}
					break;
				case MOVEFORWARD:
					switch(getNextTile(Move.FORWARD)) {
					case BLOCK:
						break;
					case HOLE:
						positions.add(getNextPos(Move.FORWARD));
						if(pointOfDeath != -1) pointOfDeath = positions.size() - 1;
						break;
					default:	//EMPTY, START or FLAG
						positions.add(getNextPos(Move.FORWARD));
						break;
					}
					break;
				case TURNLEFT:
					robotRot.add(Turn.COUNTERCLOCKWISE);
					break;
				case TURNRIGHT:
					robotRot.add(Turn.CLOCKWISE);
					break;
				default:
					break;
				}
			}
			
			pathPane.repaint();
			
		//} catch (Exception e) {
		//	System.out.println("cannot calculate path");
		//}
	}
	
	public Tile getNextTile(Move step) {
		
		byte[] pos = new byte[2];
		
		pos[0] = positions.get(positions.size() - 1)[0];
		pos[1] = positions.get(positions.size() - 1)[1];
		
		switch(robotRot) {
		case NORTH:
			pos[1] -= step.step();
			break;
		
		case EAST:
			pos[0] += step.step();
			break;
		
		case SOUTH:
			pos[1] += step.step();
			break;
		
		case WEST:
			pos[0] -= step.step();
			break;
		}
		
		return tiles[Main.fileManager.posToTileIndex(pos)];
	}
	
	private byte[] getNextPos(Move step) {
		
		byte[] pos = new byte[2];
		
		pos[0] = positions.get(positions.size() - 1)[0];
		pos[1] = positions.get(positions.size() - 1)[1];
		
		switch(robotRot) {
		case NORTH:
			pos[1] -= step.step();
			break;
		
		case EAST:
			pos[0] += step.step();
			break;
		
		case SOUTH:
			pos[1] += step.step();
			break;
		
		case WEST:
			pos[0] -= step.step();
			break;
		}
		
		return pos;
	}
	
	
	public void updateToDependencies() {
		tiles = Main.tiles;
		robotRot = Main.initRot;
		initPos = Main.fileManager.tileIndexToPos(Main.gridPane.startTile);
		
		cards = new Card[Main.slotPane.slotList.size()];
		loops = new int[Main.slotPane.slotList.size()];
		
		for (int i = 0; i < Main.slotPane.slotList.size(); i++) {
			cards[i] = Main.slotPane.slotList.get(i).type;
			loops[i] = Main.slotPane.slotList.get(i).rLoops;
		}
	}
}
