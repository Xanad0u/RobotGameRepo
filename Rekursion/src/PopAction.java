import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;

public class PopAction extends AbstractAction {

	JPanel pMenu;
	CallButton[] buttons = null;
	
	public PopAction(JPanel pMenuIn, CallButton[] buttonsIn) {
		pMenu = pMenuIn;
		buttons = new CallButton[buttonsIn.length];
		buttons = buttonsIn;
		
		System.out.println(buttonsIn.length);
		System.out.println(buttons == buttonsIn);
	}
	
	public PopAction(JPanel pMenuIn) {
		pMenu = pMenuIn;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("PopAction performed");
		if(pMenu.isVisible() == false) {
			System.out.println("Invisible");
			if(buttons != null) {
				for(int i = 0; i < buttons.length; i++) {
					buttons[i].setVisible(false);
				}
			}
			pMenu.setVisible(true);
			pMenu.repaint();
		} 
		else {
			System.out.println("Visible");
			if(buttons != null) {
				for(int i = 0; i < buttons.length; i++) {
					buttons[i].setVisible(true);
				}
			}
			pMenu.setVisible(false);
		}
		
	}
	
}
