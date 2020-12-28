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
		host.tiles[host.focusedTile] = putTile;
		host.repaint();
	}

}
