import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GridPanel extends JPanel {

	private JFrame hostFrame;
	private StageFrame host;
	
	private byte[] tiles;
	
	private CardObject testCard;

	public GridPanel(JFrame hostFrameIn, StageFrame hostIn, byte[] tilesIn) {
		hostFrame = hostFrameIn;
		host = hostIn;
		
		tiles = tilesIn;
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "trigger");
		getActionMap().put("trigger", new PopAction(host.menu));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int w = hostFrame.getContentPane().getWidth();
		int h = hostFrame.getContentPane().getHeight();

		int xPos;
		int yPos;

		if (h < w * host.ratio) {
			host.xNull = (int) ((w - (h / host.ratio)) / 2);
			host.yNull = (int) ((h - (h / host.ratio)) / 2);
			host.size = (int) (((h / host.ratio) - ((host.nTiles + 1) * host.gap)) / host.nTiles);
		}

		else {
			host.xNull = 0;
			host.yNull = (h - w) / 2;
			host.size = (w - ((host.nTiles + 1) * host.gap)) / host.nTiles;
		}

		host.fullSize = (host.nTiles + 1) * host.gap + host.nTiles * host.size;

		this.setBounds(host.xNull, host.yNull, host.fullSize, host.fullSize);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, host.fullSize, host.fullSize);

		g.setColor(Color.WHITE);

		for (int j = 0; j < host.nTiles; j++) {
			for (int i = 0; i < host.nTiles; i++) {

				xPos = (i + 1) * host.gap + i * host.size;
				yPos = (j + 1) * host.gap + j * host.size;

				switch (tiles[host.nTiles * host.nTiles - (host.nTiles * (j + 1) - i)]) {

				case 0:
					g.fillRect(xPos, yPos, host.size, host.size);
					break;

				case 1:
					g.drawImage(host.blockTile, xPos, yPos, host.size, host.size, null);
					break;

				case 2:
					g.drawImage(host.holeTile, xPos, yPos, host.size, host.size, null);
					break;

				case 3:
					g.drawImage(host.startTile, xPos, yPos, host.size, host.size, null);
					break;

				case 4:
					g.drawImage(host.flagTile, xPos, yPos, host.size, host.size, null);
					break;

				}
				/*
				
				if (tileSelectionStatus[i + 8 * j] != 0) {
					switch (tileSelectionStatus[i + 8 * j]) {

					case 1:
						g.drawImage(blockTile, xPos, yPos, size, size, null);
						break;

					case 2:
						g.drawImage(holeTile, xPos, yPos, size, size, null);
						break;

					case 3:
						g.drawImage(startTile, xPos, yPos, size, size, null);
						break;

					case 4:
						g.drawImage(flagTile, xPos, yPos, size, size, null);
						break;
					}
				}
				*/
				
			}
		}
		
		host.menu.repaint();	//Draws the menu over the grid
	}

}
