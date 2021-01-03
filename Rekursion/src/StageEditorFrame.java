import java.awt.GraphicsConfiguration;
import java.io.IOException;

public class StageEditorFrame{

	public Tile[] tiles;
	public Card[] cards;
	public int[] loops;
	private Command[] commands;
	public int slotAmount;
	public Rotation robotRot;
	public int startTile = -1;
	public int flagTile = -1;
	
	
	public StageEditorFrame() throws IOException {	//constructor used by StageEditorFrame
	

	/*
	//reImportStage(stage);		//Loading stage information from storage
	frame = buildFrame();		//Constructing frame
	
	buildPopupMenu();	//Constructing popup Menu
	
	gridPane = new GridPanel(frame, this);	//Constructing board pane
	
	gridPane.setBounds(0, 0, 1, 1);		//Setting board size to more then zero, making it rescalable

	menu.setBounds((int) (frame.getContentPane().getWidth() / 2 - menuWidth / 2), (int) (frame.getContentPane().getHeight() / 2 - menuHeight / 2), menuWidth, menuHeight);

	
	cardPane = new CardPanel(this);
	
	cardPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
	
	slotPane = new SlotPanel(this);
	
	slotPane.setBounds(0, 0, 1, 1);		//Setting testCardPanel size to more then zero, making it rescalable
	
	
	frame.add(menu);		//Add menu to frame
	frame.add(cardPane);
	frame.add(slotPane);
	frame.add(gridPane);	//Add board pane to frame
	*/
	
	}
		
		
	public void calculatePath() {
		commands = Main.makeCommandsReal(cards, loops);
	}
	
	public void updateToDependencies() {
		tiles = Main.gridPane.tiles;
		
	}
}
