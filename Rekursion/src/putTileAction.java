import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class putTileAction extends AbstractAction {

	Tile putTile;
	GridPanel host;
	
	public putTileAction(GridPanel host, Tile tile) {
		putTile = tile;
		this.host = host;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(host.mouseInFrame) {
			
			if(host.focusedTile == host.startTile) host.startTile = -1;
			if(host.focusedTile == host.flagTile) host.flagTile = -1;
			
			if(putTile == Tile.START && host.startTile != -1) host.tiles[host.startTile] = Tile.EMPTY;
			if(putTile == Tile.START) host.startTile = host.focusedTile;
			if(putTile == Tile.FLAG && host.flagTile != -1) host.tiles[host.flagTile] = Tile.EMPTY;
			if(putTile == Tile.FLAG) host.flagTile = host.focusedTile;
			
			host.tiles[host.focusedTile] = putTile;
			host.repaint();
		}
	}
}