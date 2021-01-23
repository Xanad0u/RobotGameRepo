package Frames;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import Enums.Card;
import Enums.Command;
import Enums.Move;
import Enums.Rotation;
import Enums.Tile;
import Enums.Turn;
import Globals.Main;
import Panels.CardPanel;
import Panels.GridPanel;
import Panels.MenuPanel;
import Panels.PathPanel;
import Panels.SlotPanel;

public class StageEditorFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5998963084229496942L;

	public int editedStage;
	
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
	
	PathPanel pathPane;
	
	public StageEditorFrame() throws IOException {	//constructor used by StageEditorFrame
		
	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	//Make closing window exit the program
	setSize(1028, 1228);		//Set the initial size of the window
	setLayout(null);			//Make frame layout null
	setIconImage(Main.icon);	//Set the icon image
	setExtendedState(JFrame.MAXIMIZED_BOTH);	//Extend window
	setTitle("Stage");		//Set title to Stage
	setVisible(true);		//make frame visible

	Main.menu = new MenuPanel(this, true)  {	//Create popup menu
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1224462049577928671L;

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

	//pathPane = new PathPanel();
	
	//pathPane.setBackground(new Color(0,0,0,0));	//Making robot pane background transparent
	//pathPane.setOpaque(false);						//Making robot pane transparent
	//pathPane.setVisible(true);				//Setting robot pane to visible
	//pathPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable

	
	Main.cardPane = new CardPanel();
	
	Main.cardPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
	
	Main.slotPane = new SlotPanel();
	
	Main.slotPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
	
	
	add(Main.menu);		//Add menu to frame
	//add(pathPane);
	add(Main.cardPane);
	add(Main.slotPane);
	add(Main.gridPane);	//Add board pane to frame
	 
	}
		
		
	public void calculatePath() {
		if(Main.gridPane.startTile != -1) {
			updateToDependencies();
			
			pointOfDeath = -1;
			commands = new Command[Main.makeCommandsReal(cards, loops).length];
			commands = Main.makeCommandsReal(cards, loops);
			
			positions = new ArrayList<byte[]>();
			
			positions.add(initPos);
			
			for (Command command : commands) {
				switch(command) {
				case MOVEBACKWARD:
					switch(getNextTile(Move.BACKWARD)) {
					case BLOCK:
						break;
					case HOLE:
						positions.add(getNextPos(Move.BACKWARD));
						if(pointOfDeath == -1) pointOfDeath = positions.size() - 1;
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
						if(pointOfDeath == -1) pointOfDeath = positions.size() - 1;
						break;
					default:	//EMPTY, START or FLAG
						positions.add(getNextPos(Move.FORWARD));
						break;
					}
					break;
				case TURNLEFT:
					robotRot = robotRot.add(Turn.COUNTERCLOCKWISE);
					break;
				case TURNRIGHT:
					System.out.println(robotRot);
					robotRot = robotRot.add(Turn.CLOCKWISE);
					System.out.println(robotRot);
					break;
				default:
					break;
				}
			}
		}
		else {
			positions = null;

		}
		Main.gridPane.repaint();
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
		
		if(pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7) return Tile.BLOCK;
		else return tiles[Main.fileManager.posToTileIndex(pos)];
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
