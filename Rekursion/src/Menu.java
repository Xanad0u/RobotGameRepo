import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Menu extends JFrame implements MouseListener {

	public Menu() {
		JFrame menuFrame = new JFrame();
		
		File file = new File("./img/Icon.png");
		Image img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		menuFrame.addMouseListener(this);
		menuFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		menuFrame.setSize(1028, 1228);
		menuFrame.setIconImage(img);
		menuFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		menuFrame.setTitle("Robot Main Menu");

		JPanel Panel = new JPanel();
		
		//buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		//buttonPanel.setAlignmentY(CENTER_ALIGNMENT);
		
		Dimension dim = new Dimension();
		
		dim.setSize(300, 80);
		
		
		JButton button = new JButton();
		
		Panel.add(button);
		
		menuFrame.add(Panel);
		
		
		menuFrame.setVisible(true);
	}
	
	
	
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	
	}
}
