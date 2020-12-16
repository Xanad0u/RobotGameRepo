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
	
	int call = 0;
	
	public RobotPanel(int ssIn, int bt, BufferedImage imgIn, JFrame frameIn, GridGraphics hostIn) {
		robot = new Robot(ssIn, bt, imgIn);
		frame = frameIn;
		
		host = hostIn;
		
		ss = ssIn;
		
		timer = new Timer(bt, this);
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
		
		call = 1;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBounds(host.xNull, host.yNull, host.fullSize - host.gap * 2, host.fullSize - host.gap * 2);
		
		robot.xOrigin = host.xNull + host.gap;
		robot.yOrigin = 50;
		
		System.out.println(host.xNull + host.gap);
		System.out.println(host.yNull + host.gap);
		
		robot.draw((Graphics2D) g);
		
		robot.af.setToScale(host.size / (double) robot.img.getHeight(), host.size / (double) robot.img.getHeight());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//System.out.println("tick");
		//System.out.println("call = " + call);
		
		//System.out.println(call > 0 && call < (ss + 1));
		
		if(call > 0 && call < (ss + 1)) {
			//System.out.println("in call");
			robot.af.translate(x * (host.size / ss), y * (host.size / ss));
			
			call++;
		}
		
		repaint();
	}
	
	
}
