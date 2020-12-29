import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class PutTileAction extends AbstractAction {

	Tile putTile;
	GridPanel host;
	
	public PutTileAction(GridPanel host, Tile tile) {
		putTile = tile;
		this.host = host;
		host.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(host.mouseInFrame) {
			
			if(host.focusedTile == host.startTile) {
				host.startTile = -1;
				host.host.robotPane.setRobotVisible(false);
			}
			if(host.focusedTile == host.flagTile) host.flagTile = -1;
			
			if(putTile == Tile.START && host.startTile != -1) host.tiles[host.startTile] = Tile.EMPTY;
			if(putTile == Tile.START) {
				host.startTile = host.focusedTile;
				host.host.robotPane.moveTo(host.focusedTile);
				host.host.robotPane.setRobotVisible(true);
			}
			if(putTile == Tile.FLAG && host.flagTile != -1) host.tiles[host.flagTile] = Tile.EMPTY;
			if(putTile == Tile.FLAG) host.flagTile = host.focusedTile;
			
			host.tiles[host.focusedTile] = putTile;
			host.repaint();
			host.host.robotPane.repaint();
		}
	}
}