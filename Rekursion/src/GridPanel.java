import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GridPanel extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener{

	private JFrame hostFrame;
	public StageFrame host;
	
	public int[] mousePos = new int[2];
	public int focusedTile;
			
	Tile[] tiles;
	
	private boolean isEditor = false;
	boolean mouseInFrame = false;
	int startTile = -1;
	int flagTile = -1;

	public GridPanel(JFrame hostFrameIn, StageFrame hostIn, Tile[] tilesIn) {
		hostFrame = hostFrameIn;
		host = hostIn;
		
		tiles = tilesIn;
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "trigger");
		getActionMap().put("trigger", new PopAction(host.menu));
	}
	
	public GridPanel(JFrame hostFrameIn, StageFrame hostIn) {
		isEditor = true;
		hostFrame = hostFrameIn;
		host = hostIn;
		
		tiles = new Tile[64];
		
		for (int i = 0; i < 64; i++) {
			tiles[i] = Tile.EMPTY;
		}
		
		addMouseListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "trigger");
		getActionMap().put("trigger", new PopAction(host.menu));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "turnRobotClockwise");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"), "turnRobotCounterclockwise");
		getActionMap().put("turnRobotClockwise", new TurnRobotAction(this, Turn.CLOCKWISE));
		getActionMap().put("turnRobotCounterclockwise", new TurnRobotAction(this, Turn.COUNTERCLOCKWISE));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("1"), "putEmpty");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("2"), "putBlock");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("3"), "putHole");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("4"), "putStart");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("5"), "putFlag");
		getActionMap().put("putEmpty", new PutTileAction(this, Tile.EMPTY));
		getActionMap().put("putBlock", new PutTileAction(this, Tile.BLOCK));
		getActionMap().put("putHole", new PutTileAction(this, Tile.HOLE));
		getActionMap().put("putStart", new PutTileAction(this, Tile.START));
		getActionMap().put("putFlag", new PutTileAction(this, Tile.FLAG));
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

		byte[] pos = new byte[2];
		int tileIndex;
		
		for (int j = 0; j < host.nTiles; j++) {
			for (int i = 0; i < host.nTiles; i++) {

				pos[0] = (byte) i;
				pos[1] = (byte) j;
				
				tileIndex = host.fileManager.posToTileIndex(pos);
				
				xPos = (i + 1) * host.gap + i * host.size;
				yPos = (j + 1) * host.gap + j * host.size;

				if(!isEditor) {
					switch (tiles[tileIndex]) {
	
					case EMPTY:
						g.fillRect(xPos, yPos, host.size, host.size);
						break;
	
					case BLOCK:
						g.drawImage(host.blockTile, xPos, yPos, host.size, host.size, null);
						break;
	
					case HOLE:
						g.drawImage(host.holeTile, xPos, yPos, host.size, host.size, null);
						break;
	
					case START:
						g.drawImage(host.startTile, xPos, yPos, host.size, host.size, null);
						break;
	
					case FLAG:
						g.drawImage(host.flagTile, xPos, yPos, host.size, host.size, null);
						break;
	
					}
				}
				
				else {
					
					switch (tiles[tileIndex]) {
					
					case EMPTY:
						g.fillRect(xPos, yPos, host.size, host.size);
						break;

					case BLOCK:
						g.drawImage(host.blockTile, xPos, yPos, host.size, host.size, null);
						break;

					case HOLE:
						g.drawImage(host.holeTile, xPos, yPos, host.size, host.size, null);
						break;

					case START:
						g.drawImage(host.startTile, xPos, yPos, host.size, host.size, null);
						break;
						
					case FLAG:
						g.drawImage(host.flagTile, xPos, yPos, host.size, host.size, null);
						break;
					}
				}
			}
		}
		
		host.menu.repaint();	//Draws the menu over the grid
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		switch (tiles[focusedTile]) {
		
		case EMPTY:
			tiles[focusedTile] = Tile.BLOCK;
			break;

		case BLOCK:
			tiles[focusedTile] = Tile.HOLE;
			break;

		case HOLE:
			if(startTile != -1) tiles[startTile] = Tile.EMPTY;
			startTile = focusedTile;
			
			host.robotPane.moveTo(focusedTile);
			host.robotPane.setRobotVisible(true);
			
			tiles[focusedTile] = Tile.START;
			break;

		case START:
			startTile = -1;
			host.robotPane.setRobotVisible(false);
			
			if(flagTile != -1) tiles[flagTile] = Tile.EMPTY;
			flagTile = focusedTile;
			
			tiles[focusedTile] = Tile.FLAG;
			break;
			
		case FLAG:
			flagTile = -1;
			
			tiles[focusedTile] = Tile.EMPTY;
			break;
		}
		
		this.repaint();
		host.robotPane.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseInFrame = true;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseInFrame = false;
	}
	

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

		
		tiles[focusedTile] = tiles[focusedTile].change(e.getWheelRotation());
		
		if(focusedTile == startTile) {
			startTile = -1;
			host.robotPane.setRobotVisible(false);
		}
		if(focusedTile == flagTile) flagTile = -1;
		
		if(tiles[focusedTile] == Tile.START && startTile != -1) tiles[startTile] = Tile.EMPTY;
		if(tiles[focusedTile] == Tile.START) {
			startTile = focusedTile;
			host.robotPane.moveTo(focusedTile);
			host.robotPane.setRobotVisible(true);
		}
		
		if(tiles[focusedTile] == Tile.FLAG && flagTile != -1) tiles[flagTile] = Tile.EMPTY;
		if(tiles[focusedTile] == Tile.FLAG) flagTile = focusedTile;
		
		repaint();
		host.robotPane.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		mousePos[0] = e.getX();
		mousePos[1] = e.getY();

		byte[] pos = new byte[2];
		
		pos[0] = (byte) ((mousePos[0] - 0.5 * host.gap) / (host.gap + host.size));
		pos[1] = (byte) ((mousePos[1] - 0.5 * host.gap) / (host.gap + host.size));
	
		focusedTile = host.fileManager.posToTileIndex(pos);
	}
}

