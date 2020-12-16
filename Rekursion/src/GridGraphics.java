import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

public class GridGraphics extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;

	final int menuWidth = 200;
	final int menuHeight = 300;
	
	final int gap = 2;
	final double ratio = 1.625;
	final double cardRatio = 1.57;
	final int nTiles = 8;
	int level;

	int size;
	int fullSize;
	
	int xNull;
	int yNull;
	
	byte[] tiles;
	byte[] tileSelectionStatus = new byte[64];
	byte cardAmount;
	byte slotAmount;
	byte[] realCards;

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
	JPanel gridPane;
	JPanel robotPane;
	MenuPanel menu;
	
	Robot robot;

	Graphics gMain;

	public GridGraphics(int levelIn) throws IOException {
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

		robot = new Robot(10, 10, robotImg);
		
		reImportLevel(level);
		frame = buildFrame();

		initializeTileGridPanel();
		initializeRobotPanel();

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

		gridPane = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
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
		
		gridPane.addMouseListener(this);
		gridPane.setBackground(Color.WHITE);
		
		menu = new MenuPanel(frame)  {
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				this.setBounds(frame.getContentPane().getWidth() / 2 - menuWidth / 2, frame.getContentPane().getHeight() / 2 - menuHeight / 2, menuWidth, menuHeight);
			}
		};
		
		frame.add(menu);
		frame.add(gridPane);
		menu.setVisible(false);
		
		//frame.setComponentZOrder(pane, 1);
		//frame.setComponentZOrder(menu, 0);
		
		menu.setBounds((int) (frame.getContentPane().getWidth() / 2 - menuWidth / 2), (int) (frame.getContentPane().getHeight() / 2 - menuHeight / 2), menuWidth, menuHeight);
		gridPane.revalidate();
		
		gridPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "trigger");
		gridPane.getActionMap().put("trigger", new PopAction(menu));
		
	}
	
	private void initializeRobotPanel() {
		robotPane = new JPanel() {

			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				
			}
		};
	}

	/*
	 * public static void drawRobot(Graphics g, Image robotImg, int size, int w, int
	 * h, float x, float y) {
	 * 
	 * AffineTransform at = new AffineTransform();
	 * 
	 * // 4. translate it to the center of the component
	 * at.translate(robotImg.getWidth(null) / 2, robotImg.getHeight(null) / 2);
	 * 
	 * at.setToTranslation((x * size + ( x - 1 ) * l + w), ((8 - (y + 1)) * size - (
	 * 2 + (y + 1) ) * l + h));
	 * 
	 * // 3. do the actual rotation at.rotate(Math.PI/4);
	 * 
	 * at.setToScale(1 / size, 1 / size);
	 * 
	 * // 2. just a scale because this image is big //at.scale( 1 /size, 1 / size);
	 * 
	 * // 1. translate the object so that you rotate it around the // center (easier
	 * :)) at.translate(-robotImg.getWidth(null) / 2, -robotImg.getHeight(null) /
	 * 2);
	 * 
	 * Graphics2D g2d = (Graphics2D) g; g2d.drawImage(robotImg, at, null);
	 * 
	 * g.drawImage(robotImg,(int) (x * size + ( x - 1 ) * l + w),(int) ((8 - (y +
	 * 1)) * size - ( 2 + (y + 1) ) * l + h), size, size, null); }
	 */

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
		frame.setIconImage(img);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setTitle("Stage");

		frame.setVisible(true);

		
		
		return frame;
	}

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

		/*
		System.out.println("xPos:" + xPos);
		System.out.println("xNull:" + xNull);
		System.out.println("size:" + size);
		System.out.println("gap:" + gap);
		System.out.println("shift amount:" + (xNull + 0.5 * gap));
		System.out.println("shiftet xPos:" + (xPos - (xNull + 0.5 * gap)));
		System.out.println("detect size:" + (gap + size));
		System.out.println("tile converted:" + (xPos - (xNull + 0.5 * gap)) / (gap + size));
		System.out.println("floored:" + Math.floor((xPos - (xNull + 0.5 * gap)) / (gap + size)));
		System.out.println("casted:" + (int) Math.floor((xPos - (xNull + 0.5 * gap)) / (gap + size)));
		System.out.println("xTile:" + xTile);
		System.out.println();
		System.out.println("yPos:" + yPos);
		System.out.println("yNull:" + yNull);
		System.out.println("size:" + size);
		System.out.println("gap:" + gap);
		System.out.println("shift amount:" + (yNull + 0.5 * gap));
		System.out.println("shiftet xPos:" + (yPos - (yNull + 0.5 * gap)));
		System.out.println("detect size:" + (gap + size));
		System.out.println("tile converted:" + (yPos - (yNull + 0.5 * gap)) / (gap + size));
		System.out.println("floored:" + Math.floor((yPos - (yNull + 0.5 * gap)) / (gap + size)));
		System.out.println("casted:" + (int) Math.floor((yPos - (yNull + 0.5 * gap)) / (gap + size)));
		System.out.println("yTile:" + yTile);
		*/

		gridPane.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse " + e.getClickCount() + " times pressed at " + e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("Mouse " + e.getClickCount() + " times released at " + e.getPoint());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("Mouse entered at " + e.getPoint());
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse exited at " + e.getPoint());
	}

}
