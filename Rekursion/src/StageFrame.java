import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StageFrame extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;

	final int menuWidth = 200;		//Width of the popup menu
	final int menuHeight = 300;		//Height of the popup menu
	
	final int gap = 2;				//Size of the gap between tiles in pixels
	final double ratio = 1.625;		//Optimal ratio of the playing field
	final double cardRatio = 1.57;	//Ratio of the card images
	final int nTiles = 8;			//Number of tiles in a row or column, hard coded as 8 !DO NOT CHANGE!
	int stage;						//Holds the stage index

	public int size;				//Holds the tile size
	public int fullSize;			//Holds the size of the board
	
	public int xNull;				//Holds the x component of the board origin
	public int yNull;				//Holds the y component of the board origin
	
	Tile[] tiles;								//Holds the tiles of the stage
	byte[] tileSelectionStatus = new byte[64];	//Holds the selection status of the tiles
	byte cardAmount;							//Holds the amount of cards in the stage
	byte slotAmount;							//Holds the amount of slots in the stage
	Card[] realCards;							//Holds the real cards in the stage, not the loop index of R cards
	Card[] cards;
	
	final int substeps = 20;		//Animation substeps of the robot
	final int moveTime = 100;		//Time it takes the robot to do one action
	final int pauseTime = 50;		//Pause time between two actions of the robot

	final StageFileManager fileManager = new StageFileManager();	//Creates a new StageFileManager for the stage

	final BufferedImage blockTile;	//Holds the block tile image
	final BufferedImage holeTile;	//Holds the hole tile image
	final BufferedImage startTile;	//Holds the start tile image
	final BufferedImage flagTile;	//Holds the flag tile image

	final BufferedImage robotImg;	//Holds the robot image

	final BufferedImage rTurnCard;			//Holds the right turn card image
	final BufferedImage lTurnCard;			//Holds the left turn card image
	final BufferedImage uTurnCard;			//Holds the u-turn card image
	final BufferedImage forwardCard;		//Holds the forward card image
	final BufferedImage fastForwardCard;	//Holds the fast forward card image
	final BufferedImage backCard;			//Holds the back card image
	final BufferedImage rCard;				//Holds the R card image

	final BufferedImage cardSlot;			//Holds the card slot image

	JFrame frame;			//Holds the frame
	RobotPanel robotPane;	//Holds the pane containing the robot
	GridPanel gridPane;		//Holds the pane containing the board
	MenuPanel menu;			//Holds the popup menu

	Command[] executionBuffer;	//Temporary storage holding the execution commands
		
	CardPanel cardPane;		//Holds the pane containing the usable cards
	SlotPanel slotPane;		//Holds the pane containing the slot and the cards placed in them

	private byte[] initPos;	//Holds the initial position of the robot
	//private byte initRot;	//Holds the initial rotation of the robot
	
	private Rotation initRot;
	
	public StageFrame(int stageIn) throws IOException {
		stage = stageIn;
		
		blockTile = ImageIO.read(new File("./img/Block.png"));	//Loading the images from storage
		holeTile = ImageIO.read(new File("./img/Hole.png"));
		startTile = ImageIO.read(new File("./img/Start.png"));
		flagTile = ImageIO.read(new File("./img/Flag.png"));

		robotImg = ImageIO.read(new File("./img/Robot.png"));

		rTurnCard = ImageIO.read(new File("./img/Card_TurnR.png"));
		lTurnCard = ImageIO.read(new File("./img/Card_TurnL.png"));
		uTurnCard = ImageIO.read(new File("./img/Card_UTurn.png"));
		forwardCard = ImageIO.read(new File("./img/Card_Forward.png"));
		fastForwardCard = ImageIO.read(new File("./img/Card_FastForward.png"));
		backCard = ImageIO.read(new File("./img/Card_Back.png"));
		rCard = ImageIO.read(new File("./img/Card_R.png"));

		cardSlot = ImageIO.read(new File("./img/Card_Empty.png"));

		
		reImportStage(stage);		//Loading stage information from storage
		frame = buildFrame();		//Constructing frame
		frame.addMouseListener(this);	//Add mouse listening
		
		buildPopupMenu();	//Constructing popup Menu
		
		robotPane = new RobotPanel(substeps, moveTime, pauseTime, robotImg, frame, this, initPos, initRot); //Constructing robot pane
		
		
		gridPane = new GridPanel(frame, this, tiles);	//Constructing board pane
		
		gridPane.setBounds(0, 0, 1, 1);		//Setting board size to more then zero, making it rescalable

		menu.setBounds((int) (frame.getContentPane().getWidth() / 2 - menuWidth / 2), (int) (frame.getContentPane().getHeight() / 2 - menuHeight / 2), menuWidth, menuHeight);

		robotPane.setBackground(new Color(0,0,0,0));	//Making robot pane background transparent
		robotPane.setOpaque(false);						//Making robot pane transparent
		robotPane.setVisible(true);				//Setting robot pane to visible
		robotPane.setBounds(0, 0, 1, 1);		//Setting robot pane size to more then zero, making it rescalable
		
		cardPane = new CardPanel(cards, cardAmount, this);
		
		cardPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
		
		slotPane = new SlotPanel(slotAmount, cardPane, this);
		
		slotPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
		
		
		frame.add(menu);		//Add menu to frame
		frame.add(cardPane);
		frame.add(slotPane);
		frame.add(robotPane);	//Add robot pane to frame
		frame.add(gridPane);	//Add board pane to frame
	}

	private void reImportStage(int n) {				//Loading stage information from storage
		tiles = fileManager.getTiles(n);			//Loading tiles
		slotAmount = fileManager.getSlots(n);		//Loading slot amount
		cardAmount = fileManager.getCardAmount(n);	//Loading card amount
		realCards = fileManager.getRealCards(n);	//Loading real cards, not the loop index of R cards
		cards = fileManager.getCards(n);			//Loading all cards
		initPos = fileManager.getInitLoc(n);		//Loading the initial location of the robot
		initRot = fileManager.getInitRot(n);		//Loading the initial rotation of the robot
		
		for (int i = 0; i < tiles.length; i++) {	//Reset tile selection status
			tileSelectionStatus[i] = 0;
		}
	}

	
	private void buildPopupMenu() {
		menu = new MenuPanel(frame)  {	//Create popup menu
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);	//Clear all drawn
				
				this.setBounds(frame.getContentPane().getWidth() / 2 - menuWidth / 2, frame.getContentPane().getHeight() / 2 - menuHeight / 2, menuWidth, menuHeight);	//Set popup menu bounds
			}
		};
		
		menu.setVisible(false);		//make popup menu invisible
		menu.setBounds((int) (frame.getContentPane().getWidth() / 2 - menuWidth / 2), (int) (frame.getContentPane().getHeight() / 2 - menuHeight / 2), menuWidth, menuHeight);	//Set popup menu bounds
	}
	
	private JFrame buildFrame() {
		File file = new File("./img/Icon.png");	//Set icon file path
		
		Image img = null;	//Create local image container
		try {
			img = ImageIO.read(file);	//Load icon from storage
		} catch (IOException e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame();	//Create frame
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);	//Make closing window exit the program
		
		frame.setSize(1028, 1228);	//Set the initial size of the window
		frame.setLayout(null);		//Make frame layout null
		frame.setIconImage(img);	//Set the icon image
		
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);	//Extend window
		
		frame.setTitle("Stage");	//Set title to Stage

		frame.setVisible(true);		//make frame visible

		return frame;	//Output frame
	}

	
	public void execute() {		//Method to read, convert, pass and execute the cards placed in the slots

		Command[] cmd;			//Stores the "real" commands, meaning 0 for R cards and split up double cards (fastforward and u-turn)
		int realCmd = 0;	//Stores the length of the split command string
		int[] adjLoops;		//Stores the R loops adjusted for split cards

		for(int i = 0; i < slotPane.types.length; i++) {	//Calculates the length of the split command string
			if(slotPane.types[i] == Card.FASTFORWARDCARD || slotPane.types[i] == Card.UTURNCARD) realCmd += 2;
			else realCmd++;
		}


		cmd = new Command[realCmd];			//Initialize cmd
		adjLoops = new int[realCmd];	//Initialize adjLoops
		
		int shift = 0;		//Setting up a shift counter (inital -> 0)
		
		for(int i = 0; i < slotPane.types.length; i++) {	//Converting command string with to without double cards, adjusting loops
			switch(slotPane.types[i]) {
				case BACKCARD:
					cmd[i + shift] = Command.MOVEBACKWARD;		//backward
					adjLoops[i + shift] = 0;					//single card -> ajdLoops (+1)
					break;
				case FORWARDCARD:	
					cmd[i + shift] = Command.MOVEFORWARD;		//forward
					adjLoops[i + shift] = 0;					//single card -> ajdLoops (+1)
					break;
				case FASTFORWARDCARD:
					cmd[i + shift] = Command.MOVEFORWARD;		//fastforward
					adjLoops[i + shift] = 0;					//double card -> ajdLoops (+2)
					shift++;									//double card -> shift++;
					cmd[i + shift] = Command.MOVEFORWARD;
					adjLoops[i + shift] = 0;
					break;
				case RTURNCARD:
					cmd[i + shift] = Command.TURNRIGHT;			//right turn
					adjLoops[i + shift] = 0;					//single card -> ajdLoops (+1)
					break;
				case LTURNCARD:
					cmd[i + shift] = Command.TURNLEFT;			//left turn
					adjLoops[i + shift] = 0;					//single card -> ajdLoops (+1)
					break;
				case UTURNCARD:
					cmd[i + shift] = Command.TURNRIGHT;			//u turn
					adjLoops[i + shift] = 0;					//double card -> ajdLoops (+2)
					shift++;									//double card -> shift++;
					cmd[i + shift] = Command.TURNRIGHT;
					adjLoops[i + shift] = 0;
					break;
				case RCARD:
					cmd[i + shift] = Command.INSERTRECUSION;	//R card
					adjLoops[i + shift] = slotPane.loops[i];	//single card -> adjLoops (+1), adjLoops copies loops
					break;
					
				default:	//should not be called
					System.out.println("ERROR - Could not convert card to command");
					break;
					
					//TODO Visualize current executing card
					//TODO Debugging command output
			}
		}

		ArrayList<Command> commandList = new ArrayList<>();		//Initialize List to hold commands
		
		for(int i = 0; i < cmd.length; i++) {	//Set List to be equal to the cmd array
			commandList.add(cmd[i]);
		}
		
		ArrayList<Command> outputList = recursion(commandList, adjLoops);	//Run recursion on the List, making R cards real cards
		
		executionBuffer = new Command[outputList.size()];	//Initialize Command array of the length of the List

		for(int i = 0; i < outputList.size(); i++) {
			executionBuffer[i] = outputList.get(i);		//copy List to the executionBuffer Command array
		}


		robotPane.execute(executionBuffer);	//run execution in the robot instance
	}
	
	private ArrayList<Command> recursion(ArrayList<Command> commandList, int[] loops) {	//Method to make R cards real cards
		
		ArrayList<Command> commandsOut = new ArrayList<>();	//Create new local List containing the commands to be returned
		
		for(int i = 0; i < commandList.size(); i++) {	//Loop over the input List
			if(commandList.get(i) != Command.INSERTRECUSION) {
				commandsOut.add(commandList.get(i));		//Add the command of the input List to the output List, if it isn't a R command
			}
			else {
				if(loops[i] > 0) {											//If the command is a R command test if there are iterations left
					int[] loopsExe = new int[loops.length];					//Create new array to decrease the iterations left
						for(int j = 0; j < loops.length; j++) {				//Loops over the array
							if(loops[j] > 0) loopsExe[j] = loops[j] - 1;	//If the iterations left is greater then zero decrease it by one
						}
					
					commandsOut.addAll(recursion(commandList, loopsExe));	//Run recursion with the input List and the decreased loop array
				}
			}
		}
		return commandsOut;		//return the local output List
	}
	
	
	/*
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse " + e.getClickCount() + " times clicked at " + e.getPoint());

		int w = frame.getContentPane().getWidth();
		int h = frame.getContentPane().getHeight();

		int xNull;
		int yNull;
		int size;
		int fullSize;

		int xPos = e.getX();
		int yPos = e.getY();

		int xTile;
		int yTile;

		int ySlot;

		if (h < w * ratio) {
			xNull = (int) ((w - (h / ratio)) / 2);
			yNull = (int) ((h - (h / ratio)) / 2);
			size = (int) (((h / ratio) - ((nTiles + 1) * gap)) / nTiles);
		}

		else {
			xNull = 0;
			yNull = (h - w) / 2;
			size = (w - ((nTiles + 1) * gap)) / nTiles;
		}

		xTile = (int) Math.floor((xPos - (xNull + 0.5 * gap)) / (gap + size));
		yTile = (int) Math.floor((yPos - (yNull + 0.5 * gap)) / (gap + size));

		if (tileSelectionStatus[xTile + 8 * yTile] == 4) {
			tileSelectionStatus[xTile + 8 * yTile] = 0;
		} else {
			tileSelectionStatus[xTile + 8 * yTile] += 1;
		}

		gridPane.repaint();
	}
	*/

	@Override
	public void mousePressed(MouseEvent e) {	//Temporary robot movement testing
		/*
		switch(e.getButton()) {
		case 1:
			robotPane.turnAnimated(1);
			break;
		case 2:
			robotPane.moveAnimated(1);
			break;
		case 3:
			robotPane.turnAnimated(-1);
			break;
		}
		*/
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
