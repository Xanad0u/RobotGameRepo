package BasicObjects;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import Enums.Move;
import Enums.Rotation;
import Enums.Turn;
import Frames.StageFrame;


public class Robot {
	public byte[] pos = {0, 0};
	public Rotation rot = Rotation.NORTH;
	public double scale = 1;
	
	public double[] subPos = {0, 0};
	public double subRot = 0;

	public AffineTransform af;
	
	public int substeps = 0;
	public long breakTime = 0;
	
	public BufferedImage img;
	
	StageFrame host;
	
	public boolean isVisible = true;
	
	public Robot(int ss, long bt, BufferedImage imgIn) {
		substeps = ss;
		breakTime = bt;
		img = imgIn;
		
		af = new AffineTransform();
	}
	
	public Robot(BufferedImage imgIn) {
		isVisible = false;
		img = imgIn;
		af = new AffineTransform();
	}

	public void set(byte[] p, Rotation r) {
		pos = p;
		rot = r;
	}
	
	public void setLoc(byte[] p) {
		pos = p;
	}
	
	public void setRot(Rotation initRot) {
		rot = initRot;
	}
	
	public void move(byte step) {
		
		switch(rot) {
		case NORTH:
			pos[1] -= step;
			break;
		
		case EAST:
			pos[0] += step;
			break;
		
		case SOUTH:
			pos[1] += step;
			break;
		
		case WEST:
			pos[0] -= step;
			break;
		}
	}
	
	public byte[] getMovePos(Move step) {
		
		byte[] localPos = new byte[2];
		localPos[0] = pos[0];
		localPos[1] = pos[1];
		
		switch(rot) {
		
		case NORTH:
			localPos[1] -= step.step();
			break;
		
		case EAST:
			localPos[0] += step.step();
			break;
		
		case SOUTH:
			localPos[1] += step.step();
			break;
		
		case WEST:
			localPos[0] -= step.step();
			break;
		}

		return localPos;
	}
	
	public boolean getMovePosNotOutOfGrid(Move step) {

		if(getMovePos(step)[0] < 0 || getMovePos(step)[0] > 7 || getMovePos(step)[1] < 0 || getMovePos(step)[1] > 7) return false;
		else return true;
	}


	
	public void turn(Turn turn) {
		rot = rot.add(turn);
	}
	
	public byte[] getLoc() {
		return pos;
	}
	
	public Rotation getRot() {
		return rot;
	}
	
	public void draw(Graphics2D g) {	//Draw robot using set AffineTransform
		if(isVisible) g.drawImage(img, af, null);
	}
}