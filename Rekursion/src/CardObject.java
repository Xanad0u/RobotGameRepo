import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class CardObject {

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
	
	public void draw(Graphics g, int x, int y, Integer linkedTo) {
		
		switch(state) {
		case SET:
			g.drawImage(img, x, y, Main.size, (int) (Main.size * Main.cardRatio), null);
			
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
			g.fillRoundRect(x - Main.lineSize, y - Main.lineSize, Main.size + 2 * Main.lineSize, (int) (Main.size * Main.cardRatio + 2 * Main.lineSize), Main.size / 4, Main.size / 4);
			
			g.drawImage(img, x, y, Main.size, (int) (Main.size * Main.cardRatio), null);
			
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
			g.drawImage(emptyImg, x, y, Main.size, (int) (Main.size * Main.cardRatio), null);
			break;
			
		case SELECTEDEMPTY:
			g.setColor(new Color(200, 50, 50));
			g.fillRoundRect(x - Main.lineSize, y - Main.lineSize, Main.size + 2 * Main.lineSize, (int) (Main.size * Main.cardRatio + 2 * Main.lineSize), Main.size / 4, Main.size / 4);
			
			g.drawImage(Main.cardSlot, x, y, Main.size, (int) (Main.size * Main.cardRatio), null);
			break;
			
		case LINKED:
			
			g.drawImage(img, x, y, Main.size, (int) (Main.size * Main.cardRatio), null);
			
			if(type == Card.RCARD) {
				int fontSize = Main.size / 4;
				
				g.setFont(new Font("Consolas", Font.PLAIN, fontSize));
				g.setColor(new Color(126, 192, 228, 255));
				
				char[] rLoopLabel = new char[1];
				rLoopLabel[0] = Integer.toString(rLoops).charAt(0);
				g.drawChars(rLoopLabel, 0, 1, x + (int) (Main.size / 1.7), y + fontSize + (int) (Main.size / 55));
			}
			int fontSize = Main.size / 2;
			
			g.setFont(new Font("Consolas", Font.PLAIN, fontSize));
			g.setColor(new Color(200, 50, 50));
			
			char[] linkLabel = new char[1];
			linkLabel[0] = Integer.toString(linkedTo).charAt(0);
			g.drawChars(linkLabel, 0, 1, x + (int) (Main.size / 20), y + fontSize + Main.size);
			break;
			
		default:
			break;
		}
	}
}
