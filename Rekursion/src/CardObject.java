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
	
	public CardObject(Card typeIn) {
		img = typeIn.getImg();
		type = typeIn;
		emptyImg = Main.cardSlot;
	}
	
	public CardObject(Card typeIn, int rLoopsIn) {
		img = Main.rCard;
		type = typeIn;
		emptyImg = Main.cardSlot;
		rLoops = rLoopsIn;
	}
	
	public CardObject() {
		type = Card.EMPTY;
		emptyImg = Main.cardSlot;
		state = State.EMPTY;
	}
	
	public Card getType() {
		return type;
	}
	
	public int getLoops() {
		if(type == Card.RCARD) return rLoops;
		else return 0;
	}
	
	public void draw(Graphics g, int x, int y) {
		
		switch(state) {
		case SET:
			g.drawImage(img, x, y, Main.size, (int) (Main.size * cardRatio), null);
			
			if(type == Card.RCARD) {
				int fontSize = Main.size / 4;
				
				g.setFont(new Font("Consolas", Font.PLAIN, fontSize));
				g.setColor(new Color(126, 192, 228, 255));
				
				char[] rLoopLabel = new char[1];
				rLoopLabel[0] = Integer.toString(rLoops).charAt(0);
				g.drawChars(rLoopLabel, 0, 1, x + (int) (Main.size / 1.7), y + fontSize + (int) (Main.size / 55));
			}
			break;
			
		case SELECTED:
			g.setColor(new Color(200, 50, 50));
			g.fillRoundRect(x - lineSize, y - lineSize, Main.size + 2 * lineSize, (int) (Main.size * cardRatio + 2 * lineSize), Main.size / 4, Main.size / 4);
			
			g.drawImage(img, x, y, Main.size, (int) (Main.size * cardRatio), null);
			
			if(type == Card.RCARD) {
				int fontSize = Main.size / 4;
				
				g.setFont(new Font("Consolas", Font.PLAIN, fontSize));
				g.setColor(new Color(126, 192, 228, 255));
				
				char[] rLoopLabel = new char[1];
				rLoopLabel[0] = Integer.toString(rLoops).charAt(0);
				g.drawChars(rLoopLabel, 0, 1, x + (int) (Main.size / 1.7), y + fontSize + (int) (Main.size / 55));
			}
			break;
			
		case EMPTY:
			g.drawImage(emptyImg, x, y, Main.size, (int) (Main.size * cardRatio), null);
			break;
		}
	}
}
