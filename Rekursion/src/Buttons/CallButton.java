package Buttons;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import Enums.ButtonAction;
import Frames.MenuFrame;
import Frames.StageEditorFrame;
import Frames.StageFrame;
import Frames.StageSelectionFrame;
import Globals.Main;

public class CallButton extends JButton implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3693516271197253612L;
	
	private ButtonAction call;
	private final JFrame frame;
	public int level;

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
				e2.printStackTrace();
			}
			break;
			
		case STAGESELECTIONFRAME:
			Main.StageSelectionFrame = new StageSelectionFrame();			
			break;

		case MENUFRAME:
			new MenuFrame();
			break;

		case STAGEFRAME:
			if (level != 0) {
				try {
					Main.stageFrame = new StageFrame(level);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			break;
			
		case SAVE:
			Main.fileManager.saveStage();
			new MenuFrame();
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