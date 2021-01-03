import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class GridPanel extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener{

	public int[] mousePos = new int[2];
	public int focusedTile;
			
	public Tile[] tiles;
	public Rotation rot = Rotation.EAST;
	
	private boolean isEditor = false;
	boolean mouseInFrame = false;
	int startTile = -1;
	int flagTile = -1;
	public AffineTransform af = new AffineTransform();

	public GridPanel(Tile[] tilesIn) {
		
		tiles = tilesIn;
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "trigger");
		getActionMap().put("trigger", new PopAction());
	}
	
	public GridPanel() {
		isEditor = true;
		
		tiles = new Tile[64];
		
		for (int i = 0; i < 64; i++) {
			tiles[i] = Tile.EMPTY;
		}
		
		addMouseListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "trigger");
		getActionMap().put("trigger", new PopAction());
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "turnRobotClockwise");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F"), "turnRobotCounterclockwise");
		getActionMap().put("turnRobotClockwise", new TurnRobotAction(Turn.CLOCKWISE));
		getActionMap().put("turnRobotCounterclockwise", new TurnRobotAction(Turn.COUNTERCLOCKWISE));
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("1"), "putEmpty");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("2"), "putBlock");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("3"), "putHole");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("4"), "putStart");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("5"), "putFlag");
		getActionMap().put("putEmpty", new PutTileAction(Tile.EMPTY));
		getActionMap().put("putBlock", new PutTileAction(Tile.BLOCK));
		getActionMap().put("putHole", new PutTileAction(Tile.HOLE));
		getActionMap().put("putStart", new PutTileAction(Tile.START));
		getActionMap().put("putFlag", new PutTileAction(Tile.FLAG));
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		af.setToIdentity();
			
		af.translate(Main.fileManager.tileIndexToPos(startTile)[0] * (Main.size + Main.gap) + Main.gap, Main.fileManager.tileIndexToPos(startTile)[1] * (Main.size + Main.gap) + Main.gap);
		af.scale(Main.size / (double) Main.robotImg.getHeight(), Main.size / (double) Main.robotImg.getHeight());
		af.rotate(Math.toRadians(rot.ordinal() - 1) * 90, Main.robotImg.getWidth() / 2, Main.robotImg.getHeight() / 2);

		System.out.println(Main.frame.getContentPane().getWidth());
		
		int w = Main.frame.getContentPane().getWidth();
		int h = Main.frame.getContentPane().getHeight();

		int xPos;
		int yPos;

		if (h < w * Main.ratio) {
			Main.xNull = (int) ((w - (h / Main.ratio)) / 2);
			Main.yNull = (int) ((h - (h / Main.ratio)) / 2);
			Main.size = (int) (((h / Main.ratio) - ((Main.nTiles + 1) * Main.gap)) / Main.nTiles);
		}

		else {
			Main.xNull = 0;
			Main.yNull = (h - w) / 2;
			Main.size = (w - ((Main.nTiles + 1) * Main.gap)) / Main.nTiles;
		}

		Main.fullSize = (Main.nTiles + 1) * Main.gap + Main.nTiles * Main.size;

		this.setBounds(Main.xNull, Main.yNull, Main.fullSize, Main.fullSize);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.fullSize, Main.fullSize);

		g.setColor(Color.WHITE);

		byte[] pos = new byte[2];
		int tileIndex;
		
		for (int j = 0; j < Main.nTiles; j++) {
			for (int i = 0; i < Main.nTiles; i++) {

				pos[0] = (byte) i;
				pos[1] = (byte) j;
				
				tileIndex = Main.fileManager.posToTileIndex(pos);
				
				xPos = (i + 1) * Main.gap + i * Main.size;
				yPos = (j + 1) * Main.gap + j * Main.size;

				if(!isEditor) {
					switch (tiles[tileIndex]) {
	
					case EMPTY:
						g.fillRect(xPos, yPos, Main.size, Main.size);
						break;
	
					case BLOCK:
						g.drawImage(Main.blockTile, xPos, yPos, Main.size, Main.size, null);
						break;
	
					case HOLE:
						g.drawImage(Main.holeTile, xPos, yPos, Main.size, Main.size, null);
						break;
	
					case START:
						g.drawImage(Main.startTile, xPos, yPos, Main.size, Main.size, null);
						break;
	
					case FLAG:
						g.drawImage(Main.flagTile, xPos, yPos, Main.size, Main.size, null);
						break;
	
					}
				}
				
				else {
					
					switch (tiles[tileIndex]) {
					
					case EMPTY:
						g.fillRect(xPos, yPos, Main.size, Main.size);
						break;

					case BLOCK:
						g.drawImage(Main.blockTile, xPos, yPos, Main.size, Main.size, null);
						break;

					case HOLE:
						g.drawImage(Main.holeTile, xPos, yPos, Main.size, Main.size, null);
						break;

					case START:
						g.drawImage(Main.startTile, xPos, yPos, Main.size, Main.size, null);
						Graphics2D g2d = (Graphics2D) g;
						g2d.drawImage(Main.robotImg, af, null);
						break;
						
					case FLAG:
						g.drawImage(Main.flagTile, xPos, yPos, Main.size, Main.size, null);
						break;
					}
				}
			}
		}
		
		Main.menu.repaint();	//Draws the menu over the grid
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
			
			tiles[focusedTile] = Tile.START;
			break;

		case START:
			startTile = -1;
			
			if(flagTile != -1) tiles[flagTile] = Tile.EMPTY;
			flagTile = focusedTile;
			
			tiles[focusedTile] = Tile.FLAG;
			break;
			
		case FLAG:
			flagTile = -1;
			
			tiles[focusedTile] = Tile.EMPTY;
			break;
		}
		
		repaint();
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
		}
		if(focusedTile == flagTile) flagTile = -1;
		
		if(tiles[focusedTile] == Tile.START && startTile != -1) tiles[startTile] = Tile.EMPTY;
		if(tiles[focusedTile] == Tile.START) {
			startTile = focusedTile;
		}
		
		if(tiles[focusedTile] == Tile.FLAG && flagTile != -1) tiles[flagTile] = Tile.EMPTY;
		if(tiles[focusedTile] == Tile.FLAG) flagTile = focusedTile;
		
		repaint();
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
		
		pos[0] = (byte) ((mousePos[0] - 0.5 * Main.gap) / (Main.gap + Main.size));
		pos[1] = (byte) ((mousePos[1] - 0.5 * Main.gap) / (Main.gap + Main.size));
	
		focusedTile = Main.fileManager.posToTileIndex(pos);
	}
}

