import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Robot {
	public byte[] pos = {0, 0};
	public byte rot = 0;
	
	public double[] subPos = {0, 0};
	public double subRot = 0;

	public AffineTransform af;
	
	public int substeps = 0;
	public long breakTime = 0;
	
	public BufferedImage img;
	
	StageFrame host;
	
	public Robot(int ss, long bt, BufferedImage imgIn) {
		substeps = ss;
		breakTime = bt;
		img = imgIn;
		
		af = new AffineTransform();
		
		af.setToTranslation(pos[0], pos[1]);
		af.setToRotation(Math.toRadians(rot));
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
	
	public byte[] getMovePos(byte step) {
		
		byte[] localPos = new byte[2];
		localPos[0] = pos[0];
		localPos[1] = pos[1];
		
		switch(rot) {
		case 0:
			localPos[0] += step;
			break;
		
		case 1:
			localPos[1] += step;
			break;
		
		case 2:
			localPos[0] -= step;
			break;
		
		case 3:
			localPos[1] -= step;
			break;
		}
		
		return localPos;
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
	
	public void draw(Graphics2D g) {	//Draw robot using set AffineTransform
		g.drawImage(img, af, null);
	}
}