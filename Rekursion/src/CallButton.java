import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CallButton extends JButton implements ActionListener {
	private String call;
	private final JFrame frame;
	int level;

	public CallButton(String s, JFrame frame, String callIn) {
		super(s);
		this.frame = frame;
		call = callIn;
		level = 0;
		addActionListener(this);
	}

	public CallButton(String s, JFrame frame, int levelIn) {
		super(s);
		this.frame = frame;
		call = "PF";
		level = levelIn;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (call) {
		case "SSF":
			StageSelectionFrame SSF = new StageSelectionFrame();			
			break;

		case "MF":
			MenuFrame MF = new MenuFrame();
			break;

		case "PF":
			if (level != 0) {
				try {
					LevelFrame PF = new LevelFrame(level);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			break;
		}
		frame.dispose();
	}

}