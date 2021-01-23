package Actions;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Enums.Tile;
import Globals.Main;

public class PutTileAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2803912574908409499L;
	
	Tile putTile;
	
	public PutTileAction(Tile tile) {
		putTile = tile;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(Main.gridPane.mouseInFrame) {
			
			if(Main.gridPane.focusedTile == Main.gridPane.startTile) {
				Main.gridPane.startTile = -1;
			}
			if(Main.gridPane.focusedTile == Main.gridPane.flagTile) Main.gridPane.flagTile = -1;
			
			if(putTile == Tile.START && Main.gridPane.startTile != -1) Main.tiles[Main.gridPane.startTile] = Tile.EMPTY;
			if(putTile == Tile.START) {
				Main.gridPane.startTile = Main.gridPane.focusedTile;
			}
			if(putTile == Tile.FLAG && Main.gridPane.flagTile != -1) Main.tiles[Main.gridPane.flagTile] = Tile.EMPTY;
			if(putTile == Tile.FLAG) Main.gridPane.flagTile = Main.gridPane.focusedTile;
			
			Main.tiles[Main.gridPane.focusedTile] = putTile;
			
			Main.stageEditorFrame.calculatePath();
			Main.gridPane.repaint();
		}
	}
}