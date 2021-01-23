package Actions;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Buttons.CallButton;
import Globals.Main;

public class PopAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7307527585317494126L;
	
	CallButton[] buttons = null;
	
	public PopAction(CallButton[] buttonsIn) {
		buttons = new CallButton[buttonsIn.length];
		buttons = buttonsIn;
		
		System.out.println(buttonsIn.length);
		System.out.println(buttons == buttonsIn);
	}
	
	public PopAction() {
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("PopAction performed");
		if(Main.menu.isVisible() == false) {
			System.out.println("Invisible");
			if(buttons != null) {
				for(int i = 0; i < buttons.length; i++) {
					buttons[i].setVisible(false);
				}
			}
			Main.menu.setVisible(true);
			Main.menu.repaint();
		} 
		else {
			System.out.println("Visible");
			if(buttons != null) {
				for(int i = 0; i < buttons.length; i++) {
					buttons[i].setVisible(true);
				}
			}
			Main.menu.setVisible(false);
		}
	}
}
