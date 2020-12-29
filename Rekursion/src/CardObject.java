import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class CardObject {
	
	private final double cardRatio = 1.57;
	private final int lineSize = 4;
	
	protected BufferedImage img;
	private BufferedImage emptyImg;
	Card type;
	int rLoops;
	
	public State state = State.SET;
	
	public CardObject(BufferedImage imgIn, BufferedImage emptyImgIn, Card typeIn) {
		img = imgIn;
		type = typeIn;
		emptyImg = emptyImgIn;
	}
	
	public CardObject(BufferedImage imgIn, BufferedImage emptyImgIn, Card typeIn, int rLoopsIn) {
		img = imgIn;
		type = typeIn;
		emptyImg = emptyImgIn;
		rLoops = rLoopsIn;
	}
	
	public CardObject(BufferedImage emptyImgIn) {
		type = Card.EMPTY;
		emptyImg = emptyImgIn;
		state = State.EMPTY;
	}
	
	public Card getType() {
		return type;
	}
	
	public int getLoops() {
		if(type == Card.RCARD) return rLoops;
		else return 0;
	}
	
	public void draw(Graphics g, int x, int y, int size) {
		
		switch(state) {
		case SET:
			g.drawImage(img, x, y, size, (int) (size * cardRatio), null);
			
			if(type == Card.RCARD) {
				int fontSize = size / 4;
				
				g.setFont(new Font("Consolas", Font.PLAIN, fontSize));
				g.setColor(new Color(126, 192, 228, 255));
				
				char[] rLoopLabel = new char[1];
				rLoopLabel[0] = Integer.toString(rLoops).charAt(0);
				g.drawChars(rLoopLabel, 0, 1, x + (int) (size / 1.7), y + fontSize + (int) (size / 55));
			}
			break;
			
		case SELECTED:
			g.setColor(new Color(200, 50, 50));
			g.fillRoundRect(x - lineSize, y - lineSize, size + 2 * lineSize, (int) (size * cardRatio + 2 * lineSize), size / 4, size / 4);
			
			g.drawImage(img, x, y, size, (int) (size * cardRatio), null);
			
			if(type == Card.RCARD) {
				int fontSize = size / 4;
				
				g.setFont(new Font("Consolas", Font.PLAIN, fontSize));
				g.setColor(new Color(126, 192, 228, 255));
				
				char[] rLoopLabel = new char[1];
				rLoopLabel[0] = Integer.toString(rLoops).charAt(0);
				g.drawChars(rLoopLabel, 0, 1, x + (int) (size / 1.7), y + fontSize + (int) (size / 55));
			}
			break;
			
		case EMPTY:
			g.drawImage(emptyImg, x, y, size, (int) (size * cardRatio), null);
			break;
		}
	}
}
