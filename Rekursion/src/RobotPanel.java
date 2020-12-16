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
	
	
	int ss;
	
	int x = 0;
	int y = 0;
	
	int callMove = 0;
	int callTurn = 0;
	
	public RobotPanel(int ssIn, int time, BufferedImage imgIn, JFrame frameIn, GridGraphics hostIn) {
		robot = new Robot(ssIn, time, imgIn);
		frame = frameIn;
		
		host = hostIn;
		
		ss = ssIn;
		
		timer = new Timer(time / ss, this);
		timer.start();
	}
	
	public void moveAnimated() {
		
		System.out.println("mA");
		
		//robot.af.setToTranslation(robot.xNull + robot.gap, robot.xNull + robot.gap);
		
		x = 0;
		y = 0;
		
		switch(robot.rot) {
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

			robot.subPos[0] += x * ((host.size + host.gap) / ss);
			robot.subPos[1] += y * ((host.size + host.gap) / ss);
			

			callMove++;
			
			if(callMove > ss + 1) {
				callMove = 0;
				
				robot.subPos[0] = 0;
				robot.subPos[1] = 0;
				
				robot.pos[0] += x;
				robot.pos[1] += y;
			}
		}
		
		if(Math.abs(callTurn) > 0) {
			robot.subRot += 90 / ss;
			
			callTurn += Math.signum(callTurn);
			
			if(Math.abs(callTurn) > ss + 1) {
				robot.rot -= Math.signum(callTurn);
				
				callTurn = 0;
				
				robot.subRot = 0;
				
				System.out.println("rot: " + robot.rot);
			}
		}
		
		repaint();
	}
	
	
}
