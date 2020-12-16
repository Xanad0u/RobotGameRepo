import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class Robot {
	private byte[] pos = {0, 0};
	private byte rot = 0;
	
	private int gap;
	
	private int size;
	private int fullSize;
	
	private int xNull;
	private int yNull;
	
	public AffineTransform af;
	
	private int substeps = 0;
	private long breakTime = 0;
	public BufferedImage img;
	private Graphics2D g2D;
	
	GridGraphics host;
	
	public Robot(int ss, long bt, BufferedImage imgIn, Graphics2D g, GridGraphics hostIn) {
		substeps = ss;
		breakTime = bt;
		img = imgIn;
		
		g2D = g;
		
		host = hostIn;
		
		af = new AffineTransform();
		
		af.setToTranslation(pos[0], pos[1]);
		af.setToRotation(Math.toRadians(rot));
	}
	
	public Robot(int ss, long bt, BufferedImage imgIn, GridGraphics hostIn) {
		substeps = ss;
		breakTime = bt;
		img = imgIn;
		
		host = hostIn;

		af = new AffineTransform();
		
		af.setToTranslation(pos[0], pos[1]);
		af.setToRotation(Math.toRadians(rot));
	}


	public void setGraphics(Graphics2D g) {
		g2D = g;
	}
	
	public void set(byte[] p, byte r) {
		pos = p;
		rot = r;
	}
	
	public void setLoc(byte[] p) {
		pos = p;
	}
	
	public void setRot(byte r) {
		rot = r;
	}
	
	public void move(byte step) {
		
		switch(rot) {
		case 0:
			pos[0] += step;
		break;
		
		case 1:
			pos[1] += step;
		break;
		
		case 2:
			pos[0] -= step;
		break;
		
		case 3:
			pos[1] -= step;
		break;
		}
	}
	
	public void turn(byte turn) {
		byte r = rot;
		r += turn;
		
		if(r < 0) r += 4;
		if(r > 3) r -= 4;
		
		rot = r;
	}
	
	public byte[] getLoc() {
		return pos;
	}
	
	public byte getRot() {
		return rot;
	}
	
	public void moveAnimated(JPanel pane) {
		
		System.out.println("mA");
		
		af.setToTranslation(xNull + gap + size / 2, xNull + gap + size / 2);
		
		float x = 0;
		float y = 0;
		
		switch(rot) {
		case 0:
			x = 1;
			break;
		case 1:
			y = -1;
			break;
		case 2:
			x = -1;
			break;
		case 3:
			y = -1;
			break;
		}
		
		for(int i = 0; i < substeps; i++) {
			System.out.println("loop " + i);
			
			af.translate(x * (size / substeps), y * (size / substeps));
			
			pane.repaint();

			try {
				Thread.sleep(breakTime);
				System.out.println("sleep " + i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(img, af, null);
		System.out.println("drew robot");
	}
	
	public void update() {
		gap = host.gap;
		fullSize = host.fullSize;
		size = host.size;
		xNull = host.xNull;
		yNull = host.yNull;
	}
}