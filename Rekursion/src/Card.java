import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Card {
	
	final double cardRatio = 1.57;
	
	BufferedImage img;
	int type;
	int rLoops;
	
	public Card(BufferedImage imgIn, int typeIn) {
		img = imgIn;
		type = typeIn;
	}
	
	public Card(BufferedImage imgIn, int typeIn, int rLoopsIn) {
		img = imgIn;
		type = typeIn;
		rLoops = rLoopsIn;
	}
	
	public void draw(Graphics g, int x, int y, int size) {
		g.drawImage(img, x, y, size, (int) (size * cardRatio), null);
		
		if(type == 7) {
			int fontSize = size / 4;
			
			g.setFont(new Font("Consolas", Font.PLAIN, fontSize));
			g.setColor(new Color(126, 192, 228, 255));
			
			char[] rLoopLabel = new char[1];
			rLoopLabel[0] = Integer.toString(rLoops).charAt(0);
			g.drawChars(rLoopLabel, 0, 1, x + (int) (size / 1.7), y + fontSize + (int) (size / 55));

		}
	}
}
