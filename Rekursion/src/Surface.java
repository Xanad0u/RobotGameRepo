import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Surface extends JPanel {

	BufferedImage blockTile;
	BufferedImage holeTile;
	BufferedImage startTile;
	BufferedImage flagTile;
	BufferedImage robot;
	
	AffineTransform af = new AffineTransform();
	
	public Surface() {
		try {
			blockTile = ImageIO.read(new File("./img/Block.png"));
		} catch (IOException e) {
			System.out.println("error while loading block tile");
			e.printStackTrace();
		}
		
		try {
			holeTile = ImageIO.read(new File("./img/Hole.png"));
		} catch (IOException e) {
			System.out.println("error while loading hole tile");
			e.printStackTrace();
		}
		
		try {
			startTile = ImageIO.read(new File("./img/Start.png"));
		} catch (IOException e) {
			System.out.println("error while loading start tile");
			e.printStackTrace();
		}
		
		try {
			flagTile = ImageIO.read(new File("./img/Flag.png"));
		} catch (IOException e) {
			System.out.println("error while loading flag tile");
			e.printStackTrace();
		}
		
		try {
			robot = ImageIO.read(new File("./img/Robot.png"));
		} catch (IOException e) {
			System.out.println("error while loading robot sprite");
			e.printStackTrace();
		}
	}
	
    private void doDrawing(Graphics g) {

    	
    	
        Graphics2D g2d = (Graphics2D) g;
		//g2d.drawImage(robot, 50, 50, null);
		g2d.drawImage(robot, af, null);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}

