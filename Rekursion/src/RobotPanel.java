import java.awt.Color;
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
	GridGraphics host;
	
	Timer timer;
	
	int moveTime;
	int pauseTime;
	int pauseCounter = 0;
	
	int ss;
	
	int x = 0;
	int y = 0;
	
	int callMove = 0;
	int callTurn = 0;
	
	int moveDir = 0;
	
	byte[] executionOrder = null;
	int executionElement = 0;
	boolean executionReady = true;
	
	public RobotPanel(int ssIn, int moveTimeIn, int pauseTimeIn, BufferedImage imgIn, JFrame frameIn, GridGraphics hostIn) {
		robot = new Robot(ssIn, moveTimeIn, imgIn);
		frame = frameIn;
		
		host = hostIn;
		
		ss = ssIn;
		moveTime = moveTimeIn;
		pauseTime = pauseTimeIn;
		
		timer = new Timer(moveTimeIn / ss, this);
		timer.start();
	}
	
	public void moveAnimated(int steps) {
		
		System.out.println("mA");
		
		//robot.af.setToTranslation(robot.xNull + robot.gap, robot.xNull + robot.gap);

		x = 0;
		y = 0;
		
		switch(robot.rot) {
		case 0:
			x = 1;
			break;
		case 1:
			y = 1;
			break;
		case 2:
			x = -1;
			break;
		case 3:
			y = -1;
			break;
		}
		
		moveDir = steps;
		callMove = 1;
	}
	
	public void turnAnimated(int dir) {
		callTurn = dir;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBounds(host.xNull + host.gap, host.yNull + host.gap, host.fullSize - host.gap * 2, host.fullSize - host.gap * 2);
		
		robot.af.setToIdentity();
		
		
		robot.af.translate(robot.pos[0] * (host.size + host.gap) + robot.subPos[0], robot.pos[1] * (host.size + host.gap) + robot.subPos[1]);
		robot.af.scale(host.size / (double) robot.img.getHeight(), host.size / (double) robot.img.getHeight());
		robot.af.rotate(Math.toRadians(robot.subRot + robot.rot * 90), robot.img.getWidth() / 2, robot.img.getHeight() / 2);
		
		
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
				
				pauseCounter = pauseTime;
				executionReady = true;
			}
		}
		
		if(Math.abs(callTurn) > 0) {
			robot.subRot += 90 / ss * Math.signum(callTurn);
			
			callTurn += Math.signum(callTurn);
			
			System.out.println(Math.abs(callTurn));
			System.out.println((int) Math.signum(callTurn));
			System.out.println(callTurn);
			
			if(Math.abs(callTurn) > ss + 1) {
				robot.turn((byte) Math.signum(callTurn));
				
				callTurn = 0;
				
				robot.subRot = 0;
				
				System.out.println("rot: " + robot.rot);
				
				pauseCounter = pauseTime;
				executionReady = true;
			}
		}
		
		if(executionOrder != null && executionReady && pauseCounter == 0) {
			executionReady = false;
			switch(executionOrder[executionElement]) {
			case 1:
				moveAnimated(1);
				break;
			case 2:
				moveAnimated(-1);
				break;
			case 3:
				turnAnimated(1);
				break;
			case 4:
				turnAnimated(-1);
				break;
			}
			executionElement++;
			
			if(executionElement == executionOrder.length) executionOrder = null;
		}
		
		if(pauseCounter > 0) pauseCounter--;
		
		repaint();
	}
	
	public void execute(byte[] cmds) {
		
		executionOrder = cmds;

	}
}
