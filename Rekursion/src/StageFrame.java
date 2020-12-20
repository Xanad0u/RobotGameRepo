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
import java.util.ListIterator;

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
	
	byte[] tiles;								//Holds the tiles of the stage
	byte[] tileSelectionStatus = new byte[64];	//Holds the selection status of the tiles
	byte cardAmount;							//Holds the amount of cards in the stage
	byte slotAmount;							//Holds the amount of slots in the stage
	byte[] realCards;							//Holds the real cards in the stage, not the loop index of R cards
	byte[] cards;
	
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

	int[] executionBuffer;
	
	
	byte[] testExecute = {1, 1, 1, 3, 1, 1, 2, 4, 1, 3, 1, 1, 3, 1, 1};		//Temporary instruction for robot movement testing

	int[] testRCards = {7,2,2,1,2,4,3};
	
	int[] testInstructions = {4,2,5,7,4,1,7,4,5};
	
	int[] testLoops = {0,0,0,3,0,0,2,0,0};
	
	int[] testControl = {4,1,3,4,1,3,4,1,3,4,1,3,4,2,4,3,4,2,4,3,4,2,4,1,3,4,1,3,4,2,4,3,4,2,4,3,4,3,4,2,3,1,3,4,1,3,4,1,3,4,2,4,3,4,2,4,3,4,2,4,1,3,4,1,3,4,2,4,3,4,2,4,3,4,3,4,3};
			
	CardPanel testCardPanel;
	SlotPanel testSlotPanel;

	private byte[] initPos;

	private byte initRot;
	
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
		
		testCardPanel = new CardPanel(cards, cardAmount, this);
		
		testCardPanel.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
		
		testSlotPanel = new SlotPanel(slotAmount, testCardPanel, this);
		
		testSlotPanel.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
		
		
		frame.add(menu);		//Add menu to frame
		frame.add(testCardPanel);
		frame.add(testSlotPanel);
		frame.add(robotPane);	//Add robot pane to frame
		frame.add(gridPane);	//Add board pane to frame
		
		testCardPanel.requestFocus();
				
		//robotPane.execute(testExecute);		//Temporary robot movement testing
		
		//execute();
		
	}

	private void reImportStage(int n) {				//Loading stage information from storage
		tiles = fileManager.getTiles(n);			//Loading tiles
		slotAmount = fileManager.getSlots(n);		//Loading slot amount
		cardAmount = fileManager.getCardAmount(n);	//Loading card amount
		realCards = fileManager.getRealCards(n);	//Loading real cards, not the loop index of R cards
		cards = fileManager.getCards(n);
		initPos = fileManager.getInitLoc(n);
		initRot = fileManager.getInitRot(n);
		
		for (int i = 0; i < tiles.length; i++) {	//Reset tile selection status
			tileSelectionStatus[i] = 0;
		}
	}

	
	private void buildPopupMenu() {
		menu = new MenuPanel(frame)  {	//Create popup menu
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);	//Clear
				
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

	
	public void execute() {
		//int[] rLoops = new int[testSlotPanel.types.length];
		/*
		int[] rLoops = testRCards;
		int[] rLoopsR = new int[rLoops.length];
		
		for(int i = 0; i < rLoopsR.length; i++) {
			rLoopsR[i] = rLoops[i];
		}
		
		int maxLoop = 0;
		
		for(int i = 0; i < rLoops.length; i++) {
			if(rLoops[i] > maxLoop) maxLoop = rLoops[i];
		}
		
		System.out.println();
		System.out.println("maxLoop: " + maxLoop);
		System.out.println();
		
		int currentPaths = 1;
		int totalPaths = 1;
		int pathSplits;
		
		for(int i = 0; i < maxLoop; i++) {
			pathSplits = 0;
			
			System.out.print("preArray: { ");
			
			for(int j = 0; j < rLoops.length; j++) {
				if(rLoops[j] > 0) pathSplits++;
				
				System.out.print(rLoops[j] + " ");
			}
			
			System.out.println("}");
			
			System.out.println("pathSplits: " + pathSplits);
			
			currentPaths = currentPaths * pathSplits;
			
			totalPaths += currentPaths;
			
			System.out.println("paths (temp): " + currentPaths);
			
			System.out.print("postArray: { ");
			
			for(int j = 0; j < rLoops.length; j++) {
				if(rLoops[j] > 0) rLoops[j]--;
				System.out.print(rLoops[j] + " ");
			}
			System.out.println("}");
			System.out.println();
		}
		
		System.out.println("paths: " + totalPaths);
		*/
		
		int[] cmd;
		int realCmd = 0;
		

		for(int i = 0; i < testSlotPanel.types.length; i++) {
			if(testSlotPanel.types[i] == 3 || testSlotPanel.types[i] == 6) realCmd += 2;
			else realCmd++;
		}

		
		System.out.println("real cmd: " + realCmd);
		
		cmd = new int[realCmd];
		
		int shift = 0;
		
		for(int i = 0; i < testSlotPanel.types.length; i++) {
			switch(testSlotPanel.types[i]) {
				case 1:
					cmd[i + shift] = 2;
					break;
				case 2:
					cmd[i + shift] = 1;
					break;
				case 3:
					cmd[i + shift] = 1;
					shift++;
					cmd[i + shift] = 1;
					break;
				case 4:
					cmd[i + shift] = 3;
					break;
				case 5:
					cmd[i + shift] = 4;
					break;
				case 6:
					cmd[i + shift] = 3;
					shift++;
					cmd[i + shift] = 3;
					break;
				case 7:
					cmd[i + shift] = 0;
					break;
			}
		}
		
		//int[] fullCmd = new int[totalPaths * realCmd];

		ArrayList<Integer> commandList = new ArrayList<>();
		
		for(int i = 0; i < cmd.length; i++) {
			commandList.add(cmd[i]);
		}
		
		ListIterator<Integer> commandIterator2 = commandList.listIterator(0);
		
		System.out.println(commandIterator2.hasNext());
		
		do {
			System.out.println("in cmd: " + commandIterator2.next());
		}
		while(commandIterator2.hasNext());
		

		
		ArrayList<Integer> outputList = recursion(commandList, testSlotPanel.loops);
		
		executionBuffer = new int[outputList.size()];
		
		ListIterator<Integer> commandIterator = outputList.listIterator(0);
		
		System.out.println();
		System.out.println();
		
		System.out.println(commandIterator.hasNext());
		
		System.out.println();
		
		System.out.print("{ ");
		
		int k = 0;
		
		do {
			System.out.print(commandIterator.next() + " ");
			executionBuffer[k] = outputList.get(k);
			k++;
		}
		while(commandIterator.hasNext());
		
		System.out.println("}");
		
		System.out.print("{ ");
		
		for(int i = 0; i < testControl.length; i++) {
			System.out.print(testControl[i] + " ");
		}
		
		System.out.println("}");
		
		robotPane.execute(executionBuffer);
	}
	
	private ArrayList<Integer> recursion(ArrayList<Integer> commandsIn, int[] loops) {
		
		System.out.println();
		System.out.println();
		System.out.println("recursion called");
		
		
		ArrayList<Integer> commandsOut = new ArrayList<>();
		
		for(int i = 0; i < commandsIn.size(); i++) {
			System.out.println();
			if(commandsIn.get(i) != 0) {
				commandsOut.add(commandsIn.get(i));
				System.out.println("added " + commandsIn.get(i));
			}
			
			else {
				
				if(loops[i] > 0) {
					System.out.println("else if called");
					int[] loopsExe = new int[loops.length];
						for(int j = 0; j < loops.length; j++) {
							//System.out.println("loops pre: " + i + " ; " + loops[i]);
							if(loops[j] > 0) loopsExe[j] = loops[j] - 1;
							//System.out.println("loops post: " + i + " ; " + loops[i]);
						}
					
					commandsOut.addAll(recursion(commandsIn, loopsExe));
				}
				else System.out.println("else called");
			}
		}
		
		return commandsOut;
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
