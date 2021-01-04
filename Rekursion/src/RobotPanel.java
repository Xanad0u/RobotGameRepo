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
	
	Timer timer;
	
	int pauseCounter = 0;
	
	int x = 0;
	int y = 0;
	
	int callMove = 0;
	int callTurn = 0;
	Turn callTurnFull = null;
	
	int moveStep = 0;
	
	Command[] executionOrder = null;
	int executionElement = 0;
	boolean executionReady = true;
	
	public RobotPanel(byte[] initLoc, Rotation initRot) {
		robot = new Robot(Main.substeps, Main.moveTime, Main.robotImg);
		robot.setLoc(initLoc);
		robot.setRot(initRot);
		
		timer = new Timer(Main.moveTime / Main.substeps, this);
		timer.start();
	}
	
	public RobotPanel() {
		robot = new Robot(Main.robotImg);
	}
	
	
	public void moveToWithRotation(int tile, Rotation rot) {
		robot.set(Main.fileManager.tileIndexToPos(tile), rot);
	}
	
	public void moveTo(int tile) {
		robot.setLoc(Main.fileManager.tileIndexToPos(tile));
	}
	
	public void turn(Turn turn) {
		robot.setRot(robot.getRot().add(turn));
		repaint();
	}
	
	
	public void toggleVisible() {
		if(robot.isVisible) setRobotVisible(false);
		else setRobotVisible(true);
		repaint();
	}
	
	public void setRobotVisible(boolean visible) {
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
		this.setBounds(Main.xNull + Main.gap, Main.yNull + Main.gap, Main.fullSize - Main.gap * 2, Main.fullSize - Main.gap * 2);
		
		robot.af.setToIdentity();
		
		
		robot.af.translate(robot.pos[0] * (Main.size + Main.gap) + robot.subPos[0], robot.pos[1] * (Main.size + Main.gap) + robot.subPos[1]);
		robot.af.scale(Main.size / (double) robot.img.getHeight(), Main.size / (double) robot.img.getHeight());
		robot.af.rotate(Math.toRadians(robot.subRot + (robot.rot.ordinal() - 1) * 90), robot.img.getWidth() / 2, robot.img.getHeight() / 2);
		
		
		robot.draw((Graphics2D) g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(callMove > 0) {

			robot.subPos[0] += (x * ((Main.size + Main.gap) / Main.substeps)) * moveStep;
			robot.subPos[1] += (y * ((Main.size + Main.gap) / Main.substeps)) * moveStep;
			

			callMove++;
			
			if(callMove > Main.substeps + 1) {
				callMove = 0;
				
				robot.subPos[0] = 0;
				robot.subPos[1] = 0;
				
				robot.pos[0] += x * moveStep;
				robot.pos[1] += y * moveStep;
				
				System.out.println("pos.x: " + robot.pos[0]);
				System.out.println("pos.y: " + robot.pos[1]);
				System.out.println();
				System.out.println();
				
				pauseCounter = Main.pauseTime;
				executionReady = true;
			}
		}
		
		if(Math.abs(callTurn) > 0) {
			robot.subRot += 90 / Main.substeps * Math.signum(callTurn);
			
			callTurn += Math.signum(callTurn);
			
			
			if(Math.abs(callTurn) > Main.substeps + 1) {
				robot.turn(callTurnFull);
				
				callTurn = 0;
				
				robot.subRot = 0;
				

				pauseCounter = Main.pauseTime;
				executionReady = true;
			}
		}
		
		if(executionOrder != null && executionReady && pauseCounter == 0) {
			executionReady = false;
			switch(executionOrder[executionElement]) {
			case MOVEFORWARD:
				if(Main.tiles[Main.fileManager.posToTile(robot.getMovePos(Move.FORWARD)) - 1] != Tile.BLOCK && robot.getMovePosNotOutOfGrid(Move.FORWARD)) 
					moveAnimated(Move.FORWARD);
				else {
					pauseCounter = Main.pauseTime;
					executionReady = true;
				}
				break;
				
			case MOVEBACKWARD:
				if(Main.tiles[Main.fileManager.posToTile(robot.getMovePos(Move.BACKWARD)) - 1] != Tile.BLOCK && robot.getMovePosNotOutOfGrid(Move.BACKWARD)) 
					moveAnimated(Move.BACKWARD);
				else {
					pauseCounter = Main.pauseTime;
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
