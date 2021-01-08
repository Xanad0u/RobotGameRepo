import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;


public class StageFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public StageFrame(int stageIn) throws IOException {
		Main.stage = stageIn;
		
		reImportStage(Main.stage);		//Loading stage information from storage
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	//Make closing window exit the program
		setSize(1028, 1228);	//Set the initial size of the window
		setLayout(null);		//Make frame layout null
		setIconImage(Main.icon);	//Set the icon image
		setExtendedState(JFrame.MAXIMIZED_BOTH);	//Extend window
		setTitle("Stage");	//Set title to Stage
		setVisible(true);		//make frame visible

		
		Main.menu = new MenuPanel(this, false)  {	//Create popup menu
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);	//Clear all drawn
				
				this.setBounds(getContentPane().getWidth() / 2 - Main.menuWidth / 2, getContentPane().getHeight() / 2 - Main.menuHeight / 2, Main.menuWidth, Main.menuHeight);	//Set popup menu bounds
			}
		};
		
		Main.menu.setVisible(false);		//make popup menu invisible
		Main.menu.setBounds((int) (getContentPane().getWidth() / 2 - Main.menuWidth / 2), (int) (getContentPane().getHeight() / 2 - Main.menuHeight / 2), Main.menuWidth, Main.menuHeight);	//Set popup menu bounds

		
		Main.robotPane = new RobotPanel(Main.initPos, Main.initRot); //Constructing robot pane
		
		
		Main.gridPane = new GridPanel(false);	//Constructing board pane
		
		Main.gridPane.setBounds(0, 0, 1, 1);		//Setting board size to more then zero, making it rescalable

		Main.menu.setBounds((int) (getContentPane().getWidth() / 2 - Main.menuWidth / 2), (int) (getContentPane().getHeight() / 2 - Main.menuHeight / 2), Main.menuWidth, Main.menuHeight);

		Main.robotPane.setBackground(new Color(0,0,0,0));	//Making robot pane background transparent
		Main.robotPane.setOpaque(false);						//Making robot pane transparent
		Main.robotPane.setVisible(true);				//Setting robot pane to visible
		Main.robotPane.setBounds(0, 0, 1, 1);		//Setting robot pane size to more then zero, making it rescalable
		
		Main.cardPane = new CardPanel(Main.cards, Main.cardAmount);
		
		Main.cardPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
		
		Main.slotPane = new SlotPanel(Main.slotAmount);
		
		Main.slotPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
		
		
		add(Main.menu);		//Add menu to frame
		add(Main.cardPane);
		add(Main.slotPane);
		add(Main.robotPane);	//Add robot pane to frame
		add(Main.gridPane);	//Add board pane to frame
	}

	
	private void reImportStage(int n) {				//Loading stage information from storage
		Main.tiles = Main.fileManager.getTiles(n);			//Loading tiles
		Main.slotAmount = Main.fileManager.getSlots(n);		//Loading slot amount
		Main.cardAmount = Main.fileManager.getCardAmount(n);	//Loading card amount
		Main.realCards = Main.fileManager.getRealCards(n);	//Loading real cards, not the loop index of R cards
		Main.cards = Main.fileManager.getCards(n);			//Loading all cards
		Main.initPos = Main.fileManager.getInitLoc(n);		//Loading the initial location of the robot
		Main.initRot = Main.fileManager.getInitRot(n);		//Loading the initial rotation of the robot
		
		for (int i = 0; i < Main.tiles.length; i++) {	//Reset tile selection status
			Main.tileSelectionStatus[i] = 0;
		}
	}
}
