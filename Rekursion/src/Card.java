import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Card {
	
	private final double cardRatio = 1.57;
	private final int lineSize = 4;
	
	protected BufferedImage img;
	private BufferedImage emptyImg;
	private int type;
	protected int rLoops;
	
	public int state = 0;
	
	public Card(BufferedImage imgIn, BufferedImage emptyImgIn, int typeIn) {
		img = imgIn;
		type = typeIn;
		emptyImg = emptyImgIn;
	}
	
	public Card(BufferedImage imgIn, BufferedImage emptyImgIn, int typeIn, int rLoopsIn) {
		img = imgIn;
		type = typeIn;
		emptyImg = emptyImgIn;
		rLoops = rLoopsIn;
	}
	
	public Card(BufferedImage emptyImgIn) {
		type = 0;
		emptyImg = emptyImgIn;
	}
	
	public int getType() {
		return type;
	}
	
	public int getLoops() {
		if(type == 7) return rLoops;
		else return 0;
	}
	
	public void draw(Graphics g, int x, int y, int size) {
		
		switch(state) {
		case 0:
			g.drawImage(img, x, y, size, (int) (size * cardRatio), null);
			
			if(type == 7) {
				int fontSize = size / 4;
				
				g.setFont(new Font("Consolas", Font.PLAIN, fontSize));
				g.setColor(new Color(126, 192, 228, 255));
				
				char[] rLoopLabel = new char[1];
				rLoopLabel[0] = Integer.toString(rLoops).charAt(0);
				g.drawChars(rLoopLabel, 0, 1, x + (int) (size / 1.7), y + fontSize + (int) (size / 55));
			}
			break;
			
		case 1:
			g.setColor(new Color(200, 50, 50));
			g.fillRoundRect(x - lineSize, y - lineSize, size + 2 * lineSize, (int) (size * cardRatio + 2 * lineSize), 25, 25);
			
			g.drawImage(img, x, y, size, (int) (size * cardRatio), null);
			
			if(type == 7) {
				int fontSize = size / 4;
				
				g.setFont(new Font("Consolas", Font.PLAIN, fontSize));
				g.setColor(new Color(126, 192, 228, 255));
				
				char[] rLoopLabel = new char[1];
				rLoopLabel[0] = Integer.toString(rLoops).charAt(0);
				g.drawChars(rLoopLabel, 0, 1, x + (int) (size / 1.7), y + fontSize + (int) (size / 55));
			}
			break;
			
		case 2:
			g.drawImage(emptyImg, x, y, size, (int) (size * cardRatio), null);
			break;
		}
	}
}
