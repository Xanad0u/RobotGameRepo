import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Cards extends JPanel {

	public Cards() {
		addMouseMotionListener(new mouseEvent());
        addMouseListener(new mouseEvent());
        
        System.out.println("Cards()");
	}
	
	
	
	private class mouseEvent extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
        	System.out.println("click");
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
        	System.out.println("drag");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        	System.out.println("release");
        }
        
        public void updateLocation(MouseEvent e) {

        }
    }
}
