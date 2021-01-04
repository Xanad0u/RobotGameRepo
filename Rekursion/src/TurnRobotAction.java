import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class TurnRobotAction extends AbstractAction {

	Turn turn;
	
	public TurnRobotAction(Turn turn) {
		this.turn = turn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Turn action called");
		
		if(Main.gridPane.mouseInFrame) {
			
			System.out.println("condition passed");
			System.out.println(Main.initRot);
			
			Main.initRot = Main.initRot.add(turn);
			System.out.println(Main.initRot);
			Main.gridPane.repaint();
		}
	}
}