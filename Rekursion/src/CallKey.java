import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPopupMenu;

public class CallKey implements KeyListener {
	private String label;
	private int keycode;
	public CallKey(String labelIn, int keycodeIn) {
		label = labelIn;
		keycode = keycodeIn;
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(keycode) {
		case 27:
			
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
