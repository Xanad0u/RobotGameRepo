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
	
	int moveStep = 0;
	
	Command[] executionOrder = null;
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
	
	public RobotPanel(BufferedImage imgIn, JFrame frameIn, StageFrame hostIn) {
		robot = new Robot(imgIn);
		frame = frameIn;
		
		host = hostIn;
	}
	
	public void moveToWithRotation(int tile, Rotation rot) {
		robot.set(host.fileManager.tileToPos(tile), rot);
		repaint();
	}
	
	public void toggleVisible() {
		if(robot.isVisible) robot.isVisible = false;
		else robot.isVisible = true;
		repaint();
	}
	
	public void setVisible(boolean visible) {
		robot.isVisible = visible;
		repaint();
	}

	public void moveAnimated(Move move) {

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
		
		moveStep = move.step();
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
		
		
		if(robot.isVisible) robot.draw((Graphics2D) g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(callMove > 0) {

			robot.subPos[0] += (x * ((host.size + host.gap) / ss)) * moveStep;
			robot.subPos[1] += (y * ((host.size + host.gap) / ss)) * moveStep;
			

			callMove++;
			
			if(callMove > ss + 1) {
				callMove = 0;
				
				robot.subPos[0] = 0;
				robot.subPos[1] = 0;
				
				robot.pos[0] += x * moveStep;
				robot.pos[1] += y * moveStep;
				
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
			case MOVEFORWARD:
				if(host.tiles[host.fileManager.posToTile(robot.getMovePos(Move.FORWARD)) - 1] != Tile.BLOCK && robot.getMovePosNotOutOfGrid(Move.FORWARD)) 
					moveAnimated(Move.FORWARD);
				else {
					pauseCounter = pauseTime;
					executionReady = true;
				}
				break;
				
			case MOVEBACKWARD:
				if(host.tiles[host.fileManager.posToTile(robot.getMovePos(Move.BACKWARD)) - 1] != Tile.BLOCK && robot.getMovePosNotOutOfGrid(Move.BACKWARD)) 
					moveAnimated(Move.BACKWARD);
				else {
					pauseCounter = pauseTime;
					executionReady = true;
				}
				break;
				
			case TURNRIGHT:
				turnAnimated(Turn.CLOCKWISE);
				break;
				
			case TURNLEFT:
				turnAnimated(Turn.COUNTERCLOCKWISE);
				break;
				
			default:	//should not be called
				System.out.println("ERROR - Could not execute command");
				break;
			}
			executionElement++;
			
			if(executionElement == executionOrder.length) executionOrder = null;
		}
		
		if(pauseCounter > 0) pauseCounter--;
		
		repaint();
	}
	
	public void execute(Command[] executionBuffer) {
		
		executionOrder = executionBuffer;
	}
}
