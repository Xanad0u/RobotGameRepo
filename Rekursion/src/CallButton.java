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

	public CallButton(JFrame frame, int levelIn) {
		super(String.valueOf(levelIn));
		this.frame = frame;
		call = ButtonAction.STAGEFRAME;
		level = levelIn;
		addActionListener(this);
	}
	
	public void changeCallStage(int levelIn) {
		setText(String.valueOf(levelIn));
		call = ButtonAction.STAGEFRAME;
		level = levelIn;
		addActionListener(this);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean dispose = true;
		
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
			Main.StageSelectionFrame = new StageSelectionFrame();			
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
			
		case NEXTPAGE:
			Main.StageSelectionFrame.goToPage(Main.StageSelectionFrame.currentPage + 1);
			dispose = false;
			break;
			
		case PREVIOUSPAGE:
			if(Main.StageSelectionFrame.currentPage != 0) Main.StageSelectionFrame.goToPage(Main.StageSelectionFrame.currentPage - 1);
			dispose = false;
			break;
			
		default:
			break;
			
		
		}
		if(dispose) frame.dispose();
	}

}