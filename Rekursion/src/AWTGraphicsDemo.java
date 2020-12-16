import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AWTGraphicsDemo extends Frame {
       
	BufferedImage blockTile;
	AffineTransform af = new AffineTransform();
	
	public AWTGraphicsDemo(){
		super("Java AWT Examples");
      
		try {
			BufferedImage blockTile = ImageIO.read(new File("./img/Block.png"));
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
		prepareGUI();
	}

	public static void main(String[] args){
		AWTGraphicsDemo awtGraphicsDemo = new AWTGraphicsDemo();  
		awtGraphicsDemo.setVisible(true);
	}

	private void prepareGUI(){
		setSize(400,400);
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		}); 
	}    

	@Override
	public void paint(Graphics g) {
		
		af.translate(10, 10);
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(blockTile, af, null);

	}
}