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

public class LevelFrame extends JFrame implements MouseListener{

	private static final long serialVersionUID = 1L;

	final int menuWidth = 200;
	final int menuHeight = 300;
	
	final int gap = 2;
	final double ratio = 1.625;
	final double cardRatio = 1.57;
	final int nTiles = 8;
	int level;

	public int size;
	public int fullSize;
	
	public int xNull;
	public int yNull;
	
	byte[] tiles;
	byte[] tileSelectionStatus = new byte[64];
	byte cardAmount;
	byte slotAmount;
	byte[] realCards;
	
	final int substeps = 20;
	final int moveTime = 100;
	final int pauseTime = 50;

	final StageFileManager fileManager = new StageFileManager();

	final BufferedImage blockTile;
	final BufferedImage holeTile;
	final BufferedImage startTile;
	final BufferedImage flagTile;

	final BufferedImage robotImg;

	final BufferedImage rTurnCard;
	final BufferedImage lTurnCard;
	final BufferedImage uTurnCard;
	final BufferedImage forwardCard;
	final BufferedImage fastForwardCard;
	final BufferedImage backCard;
	final BufferedImage RCard;

	final BufferedImage cardSlot;

	JFrame frame;
	RobotPanel robotPane;
	GridPanel gridPane;
	MenuPanel menu;

	Graphics gMain;
	
	byte[] testExecute = {1,1,1,3,1,1,2,4,1,3,1,1,3,1,1};

	
	public LevelFrame(int levelIn) throws IOException {
		level = levelIn;
		
		blockTile = ImageIO.read(new File("./img/Block.png"));
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
		RCard = ImageIO.read(new File("./img/Card_R.png"));

		cardSlot = ImageIO.read(new File("./img/Card_Empty.png"));

		
		reImportLevel(level);
		frame = buildFrame();
		
		initializeTileGridPanel();
		
		robotPane = new RobotPanel(substeps, moveTime, pauseTime, robotImg, frame, this);
		gridPane = new GridPanel(frame, this, tiles);
		
		gridPane.setBounds(0, 0, 1, 1);

		menu.setBounds((int) (frame.getContentPane().getWidth() / 2 - menuWidth / 2), (int) (frame.getContentPane().getHeight() / 2 - menuHeight / 2), menuWidth, menuHeight);

		robotPane.setBackground(new Color(0,0,0,0));
		robotPane.setOpaque(false);
		robotPane.setVisible(true);
		robotPane.setBounds(0, 0, 1, 1);
		
		
		frame.add(menu);
		frame.add(robotPane);
		frame.add(gridPane);
				
		robotPane.execute(testExecute);
		
	}

	private void reImportLevel(int n) {
		tiles = fileManager.getTiles(n);
		slotAmount = fileManager.getSlots(n);
		cardAmount = fileManager.getCardAmount(n);
		realCards = fileManager.getRealCards(n);

		for (int i = 0; i < tiles.length; i++) {
			tileSelectionStatus[i] = 0;
		}
	}

	
	private void initializeTileGridPanel() {
		
		/*
		gridPane = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				setBounds(0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight());
				
				gMain = g;
				
				int w = frame.getContentPane().getWidth();
				int h = frame.getContentPane().getHeight();

				

				int xPos;
				int yPos;

				int xCard;
				int yCard;

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

				fullSize = (nTiles + 1) * gap + nTiles * size;

				g.setColor(Color.BLACK);
				g.fillRect(xNull, yNull, fullSize, fullSize);

				g.setColor(Color.WHITE);

				for (int j = 0; j < nTiles; j++) {
					for (int i = 0; i < nTiles; i++) {

						xPos = ((i + 1) * gap + i * size) + xNull;
						yPos = ((j + 1) * gap + j * size) + yNull;

						switch (tiles[nTiles * nTiles - (nTiles * (j + 1) - i)]) {

						case 0:
							g.fillRect(xPos, yPos, size, size);
							break;

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
						
					}
				}
				

				yCard = (int) (((h - fullSize) / 2) - 2 * size);

				for (int i = 0; i < cardAmount; i++) {
					xCard = (int) ((fullSize - cardAmount * size) / (cardAmount + 1) * (i + 1) + size * i + xNull);

					switch (realCards[i]) {
					case 1:
						g.drawImage(backCard, xCard, yCard, size, (int) (size * cardRatio), null);
						break;

					case 2:
						g.drawImage(forwardCard, xCard, yCard, size, (int) (size * cardRatio), null);
						break;

					case 3:
						g.drawImage(fastForwardCard, xCard, yCard, size, (int) (size * cardRatio), null);
						break;

					case 4:
						g.drawImage(rTurnCard, xCard, yCard, size, (int) (size * cardRatio), null);
						break;

					case 5:
						g.drawImage(lTurnCard, xCard, yCard, size, (int) (size * cardRatio), null);
						break;

					case 6:
						g.drawImage(uTurnCard, xCard, yCard, size, (int) (size * cardRatio), null);
						break;

					case 7:
						g.drawImage(RCard, xCard, yCard, size, (int) (size * cardRatio), null);
						break;
					}
				}

				ySlot = (int) (((h + fullSize) / 2) + 0.5 * size);

				for (int i = 0; i < slotAmount; i++) {
					xCard = (int) ((fullSize - slotAmount * size) / (slotAmount + 1) * (i + 1) + size * i + xNull);

					g.drawImage(cardSlot, xCard, ySlot, size, (int) (size * cardRatio), null);
				}
				menu.repaint();
			}
		};
		*/
		
		frame.addMouseListener(this);
		
		menu = new MenuPanel(frame)  {
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				this.setBounds(frame.getContentPane().getWidth() / 2 - menuWidth / 2, frame.getContentPane().getHeight() / 2 - menuHeight / 2, menuWidth, menuHeight);
			}
		};
		
		menu.setVisible(false);
		menu.setBounds((int) (frame.getContentPane().getWidth() / 2 - menuWidth / 2), (int) (frame.getContentPane().getHeight() / 2 - menuHeight / 2), menuWidth, menuHeight);
		
		
	}
	
	private JFrame buildFrame() {
		File file = new File("./img/Icon.png");
		Image img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(1028, 1228);
		frame.setLayout(null);
		frame.setIconImage(img);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setTitle("Stage");

		frame.setVisible(true);

		return frame;
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
	public void mousePressed(MouseEvent e) {

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
