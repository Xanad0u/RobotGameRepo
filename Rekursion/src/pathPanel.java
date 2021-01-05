import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class pathPanel extends JPanel {
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBounds(Main.xNull, Main.yNull, Main.fullSize, Main.fullSize);
		
		g.setColor(Color.GREEN);
		
		byte[] pos1;
		byte[] pos2;
		
		for (int i = 0; i < Main.stageEditorFrame.positions.size() - 1; i++) {
			
			if(i == Main.stageEditorFrame.pointOfDeath) g.setColor(Color.RED);
			
			pos1 = Main.stageEditorFrame.positions.get(i);
			pos2 = Main.stageEditorFrame.positions.get(i + 1);
			
			g.drawLine(scalePos(pos1[0]), scalePos(pos1[1]), scalePos(pos2[0]), scalePos(pos2[1]));
		}
	}
	
	private int scalePos(byte pos) {
		return (int) ((pos + 1) * Main.gap + (pos + 0.5) * Main.size);
	}
}

