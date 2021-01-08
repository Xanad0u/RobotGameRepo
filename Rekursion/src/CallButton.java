import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CallButton extends JButton implements ActionListener {
	private ButtonAction call;
	private final JFrame frame;
	int level;

	public CallButton(String s, JFrame frame, ButtonAction call) {
		super(s);
		this.frame = frame;
		this.call = call;
		level = 0;
		addActionListener(this);
	}

	public CallButton(String s, JFrame frame, int levelIn) {
		super(s);
		this.frame = frame;
		call = ButtonAction.STAGEFRAME;
		level = levelIn;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (call) {
		case STAGEEDITORFRAME:
			try {
				Main.stageEditorFrame = new StageEditorFrame();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
			
		case STAGESELECTIONFRAME:
			StageSelectionFrame SSF = new StageSelectionFrame();			
			break;

		case MENUFRAME:
			MenuFrame MF = new MenuFrame();
			break;

		case STAGEFRAME:
			if (level != 0) {
				try {
					Main.stageFrame = new StageFrame(level);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			break;
			
		case SAVE:
			Main.fileManager.saveStage();
			MenuFrame MF2 = new MenuFrame();
			break;
			
		default:
			break;
		}
		frame.dispose();
	}

}