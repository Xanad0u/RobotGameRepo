import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CloseButton extends JButton implements ActionListener {
	private final JFrame frame;
	public CloseButton(String s, JFrame frame) {
		super(s);
		this.frame = frame;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.dispose();
		System.exit(0);
	}

}
