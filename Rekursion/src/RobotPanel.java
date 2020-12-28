import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RobotPanel extends JPanel implements ActionListener {

	Robot robot;
	JFrame frame;
	RobotPanel panel = this;
	StageFrame host;
	
	Timer timer;
	
	int moveTime;
	int pauseTime;
	int pauseCounter = 0;
	
	int ss;
	
	int x = 0;
	int y = 0;
	
	int callMove = 0;
	int callTurn = 0;
	Turn callTurnFull = null;
	
	int moveDir = 0;
	
	int[] executionOrder = null;
	int executionElement = 0;
	boolean executionReady = true;
	
	public RobotPanel(int ssIn, int moveTimeIn, int pauseTimeIn, BufferedImage imgIn, JFrame frameIn, StageFrame hostIn, byte[] initLoc, Rotation initRot) {
		robot = new Robot(ssIn, moveTimeIn, imgIn);
		robot.setLoc(initLoc);
		robot.setRot(initRot);
		frame = frameIn;
		
		host = hostIn;
		
		ss = ssIn;
		moveTime = moveTimeIn;
		pauseTime = pauseTimeIn;
		
		timer = new Timer(moveTimeIn / ss, this);
		timer.start();
	}
	
	public void moveAnimated(int steps) {

		x = 0;
		y = 0;
		
		switch(robot.rot) {
		case NORTH:
			y = -1;
			break;
		case EAST:
			x = 1;
			break;
		case SOUTH:
			y = 1;
			break;
		case WEST:
			x = -1;
			break;
		}
		
		moveDir = steps;
		callMove = 1;
	}
	
	public void turnAnimated(Turn dir) {
		callTurn = dir.dir();
		callTurnFull = dir;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBounds(host.xNull + host.gap, host.yNull + host.gap, host.fullSize - host.gap * 2, host.fullSize - host.gap * 2);
		
		robot.af.setToIdentity();
		
		
		robot.af.translate(robot.pos[0] * (host.size + host.gap) + robot.subPos[0], robot.pos[1] * (host.size + host.gap) + robot.subPos[1]);
		robot.af.scale(host.size / (double) robot.img.getHeight(), host.size / (double) robot.img.getHeight());
		robot.af.rotate(Math.toRadians(robot.subRot + (robot.rot.ordinal() - 1) * 90), robot.img.getWidth() / 2, robot.img.getHeight() / 2);
		
		
		robot.draw((Graphics2D) g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(callMove > 0) {

			robot.subPos[0] += (x * ((host.size + host.gap) / ss)) * moveDir;
			robot.subPos[1] += (y * ((host.size + host.gap) / ss)) * moveDir;
			

			callMove++;
			
			if(callMove > ss + 1) {
				callMove = 0;
				
				robot.subPos[0] = 0;
				robot.subPos[1] = 0;
				
				robot.pos[0] += x * moveDir;
				robot.pos[1] += y * moveDir;
				
				System.out.println("pos.x: " + robot.pos[0]);
				System.out.println("pos.y: " + robot.pos[1]);
				System.out.println();
				System.out.println();
				
				pauseCounter = pauseTime;
				executionReady = true;
			}
		}
		
		if(Math.abs(callTurn) > 0) {
			robot.subRot += 90 / ss * Math.signum(callTurn);
			
			callTurn += Math.signum(callTurn);
			
			
			if(Math.abs(callTurn) > ss + 1) {
				robot.turn(callTurnFull);
				
				callTurn = 0;
				
				robot.subRot = 0;
				

				pauseCounter = pauseTime;
				executionReady = true;
			}
		}
		
		if(executionOrder != null && executionReady && pauseCounter == 0) {
			executionReady = false;
			switch(executionOrder[executionElement]) {
			case 1:
				System.out.println("moveTile: " + host.fileManager.posToTile(robot.getMovePos((byte) 1)));
				System.out.println("moveTileType: " + host.tiles[host.fileManager.posToTile(robot.getMovePos((byte) 1)) - 1]);
				System.out.println();
				if(host.tiles[host.fileManager.posToTile(robot.getMovePos((byte) 1)) - 1] != 1) 
					moveAnimated(1);
				else {
					pauseCounter = pauseTime;
					executionReady = true;
				}
				break;
			case 2:
				if(host.tiles[host.fileManager.posToTile(robot.getMovePos((byte) - 1)) - 1] != 1) 
					moveAnimated(-1);
				break;
			case 3:
				turnAnimated(Turn.CLOCKWISE);
				break;
			case 4:
				turnAnimated(Turn.COUNTERCLOCKWISE);
				break;
			}
			executionElement++;
			
			if(executionElement == executionOrder.length) executionOrder = null;
		}
		
		if(pauseCounter > 0) pauseCounter--;
		
		repaint();
	}
	
	public void execute(int[] executionBuffer) {
		
		executionOrder = executionBuffer;
	}
}
