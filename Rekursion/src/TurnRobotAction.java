import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class TurnRobotAction extends AbstractAction {

	Turn turn;
	GridPanel host;
	
	public TurnRobotAction(GridPanel host, Turn turn) {
		this.turn = turn;
		this.host = host;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(host.mouseInFrame) {
			
			host.host.robotPane.turn(turn);
			host.host.robotPane.repaint();
		}
	}
}