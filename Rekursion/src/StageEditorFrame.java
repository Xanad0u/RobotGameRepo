import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class StageEditorFrame extends JFrame {

	public Tile[] tiles;
	public Card[] cards;
	public int[] loops;
	private Command[] commands;
	public int slotAmount;
	public Rotation robotRot;
	public int startTile = -1;
	public int flagTile = -1;
	
	
	public StageEditorFrame() throws IOException {	//constructor used by StageEditorFrame
	

	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	//Make closing window exit the program
	setSize(1028, 1228);	//Set the initial size of the window
	setLayout(null);		//Make frame layout null
	setIconImage(Main.icon);	//Set the icon image
	setExtendedState(JFrame.MAXIMIZED_BOTH);	//Extend window
	setTitle("Stage");	//Set title to Stage
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

	Main.menu.setBounds((int) (getContentPane().getWidth() / 2 - Main.menuWidth / 2), (int) (getContentPane().getHeight() / 2 - Main.menuHeight / 2), Main.menuWidth, Main.menuHeight);

	
	Main.cardPane = new CardPanel();
	
	Main.cardPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
	
	Main.slotPane = new SlotPanel();
	
	Main.slotPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
	
	
	add(Main.menu);		//Add menu to frame
	add(Main.cardPane);
	add(Main.slotPane);
	add(Main.gridPane);	//Add board pane to frame
	
	
	}
		
		
	public void calculatePath() {
		commands = Main.makeCommandsReal(cards, loops);
	}
	
	public void updateToDependencies() {
		tiles = Main.tiles;
		
	}
}
