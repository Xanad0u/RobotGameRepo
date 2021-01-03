import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {		//main caller
	
	public static int menuWidth = 200;		//Width of the popup menu
	public static int menuHeight = 300;		//Height of the popup menu
	
	public static int gap = 2;				//Size of the gap between tiles in pixels
	public static double ratio = 1.625;		//Optimal ratio of the playing field
	public static double cardRatio = 1.57;	//Ratio of the card images
	public static int nTiles = 8;			//Number of tiles in a row or column, hard coded as 8 !DO NOT CHANGE!
	
	public static int substeps = 20;		//Animation substeps of the robot
	public static int moveTime = 100;		//Time it takes the robot to do one action
	public static int pauseTime = 50;		//Pause time between two actions of the robot
	
	public static int fullSize;				//Holds the size of the board
	
	public static int xNull;				//Holds the x component of the board origin
	public static int yNull;				//Holds the y component of the board origin
	
	public static StageFileManager fileManager = new StageFileManager();	//Creates a new StageFileManager for the stage

	public static int size;
	public static BufferedImage blockTile;			//Holds the block tile image
	public static BufferedImage holeTile;			//Holds the hole tile image
	public static BufferedImage startTile;			//Holds the start tile image
	public static BufferedImage flagTile;			//Holds the flag tile image
	public static BufferedImage robotImg;			//Holds the robot image
	public static BufferedImage rTurnCard;			//Holds the right turn card image
	public static BufferedImage lTurnCard;			//Holds the left turn card image
	public static BufferedImage uTurnCard;			//Holds the u-turn card image
	public static BufferedImage forwardCard;		//Holds the forward card image
	public static BufferedImage fastForwardCard;	//Holds the fast forward card image
	public static BufferedImage backCard;			//Holds the back card image
	public static BufferedImage rCard;				//Holds the R card image
	public static BufferedImage cardSlot;			//Holds the card slot image
	
	public static StageFrame frame = null;						//Holds the frame
	public static RobotPanel robotPane = null;				//Holds the pane containing the robot
	public static GridPanel gridPane = null;				//Holds the pane containing the board
	public static MenuPanel menu = null;					//Holds the popup menu
	public static CardPanel cardPane = null;				//Holds the pane containing the usable cards
	public static SlotPanel slotPane = null;				//Holds the pane containing the slot and the cards placed in them
	
	public static void main(String[] args) throws IOException {
		
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
		
		
		MenuFrame menu = new MenuFrame();
	}
	
	static Command[] makeCommandsReal(Card[] cardsInSlots, int[] loops) {
		Command[] cmd;			//Stores the "real" commands, meaning 0 for R cards and split up double cards (fastforward and u-turn)
		int realCmd = 0;	//Stores the length of the split command string
		int[] adjLoops;		//Stores the R loops adjusted for split cards

		for(int i = 0; i < cardsInSlots.length; i++) {	//Calculates the length of the split command string
			if(cardsInSlots[i] == Card.FASTFORWARDCARD || cardsInSlots[i] == Card.UTURNCARD) realCmd += 2;
			else realCmd++;
		}


		cmd = new Command[realCmd];			//Initialize cmd
		adjLoops = new int[realCmd];	//Initialize adjLoops
		
		int shift = 0;		//Setting up a shift counter (inital -> 0)
		
		for(int i = 0; i < cardsInSlots.length; i++) {	//Converting command string with to without double cards, adjusting loops
			switch(cardsInSlots[i]) {
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
					adjLoops[i + shift] = loops[i];	//single card -> adjLoops (+1), adjLoops copies loops
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
		
		Command[] executionBuffer;	//Temporary storage holding the execution commands
		
		executionBuffer = new Command[outputList.size()];	//Initialize Command array of the length of the List

		for(int i = 0; i < outputList.size(); i++) {
			executionBuffer[i] = outputList.get(i);		//copy List to the executionBuffer Command array
		}
		return executionBuffer;
	}


	static ArrayList<Command> recursion(ArrayList<Command> commandList, int[] loops) {	//Method to make R cards real cards
		
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
}

