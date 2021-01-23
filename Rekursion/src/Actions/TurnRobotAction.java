package Actions;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Enums.Turn;
import Globals.Main;

public class TurnRobotAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1157555767397710156L;
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
			
			Main.stageEditorFrame.calculatePath();
		}
	}
}