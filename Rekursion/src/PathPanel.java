import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PathPanel extends JPanel {
	
	int[] pos1 = new int[2];
	int[] pos2 = new int[2];

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		System.out.println("executed repaint");

		

		g.setColor(Color.GREEN);
		
		
		if(Main.stageEditorFrame.positions != null) {
			for (int i = 0; i < Main.stageEditorFrame.positions.size() - 1; i++) {
				
				if(i == Main.stageEditorFrame.pointOfDeath) g.setColor(Color.RED);
				
				pos1[0] = scalePos(Main.stageEditorFrame.positions.get(i)[0]);
				pos1[1] = scalePos(Main.stageEditorFrame.positions.get(i)[1]);
				pos2[0] = scalePos(Main.stageEditorFrame.positions.get(i + 1)[0]);
				pos2[1] = scalePos(Main.stageEditorFrame.positions.get(i + 1)[1]);
				
				g.drawLine(pos1[0], pos1[1], pos2[0], pos2[1]);
				System.out.println("drew Line from (" + pos1[0] + ", " + pos1[1] + ") to (" + pos2[0] + ", " + pos2[1] + ")");
			}
		}
	}
	
	private int scalePos(byte pos) {
		return (int) ((pos + 1) * Main.gap + (pos + 0.5) * Main.size);
	}
	
	public void manualRepaint() {
		System.out.println("received path repaint call");
		
		if(Main.xNull > 0 && Main.yNull > 0) setBounds(Main.xNull, Main.yNull, Main.fullSize, Main.fullSize);
		else setBounds(0, 0, 1, 1);
		
		repaint();
	}
}

