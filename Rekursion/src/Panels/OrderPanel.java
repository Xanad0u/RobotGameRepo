package Panels;

import java.awt.Graphics;

import javax.swing.JPanel;

import Globals.Main;

public class OrderPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5208338183755704167L;

	public int position = 0;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBounds(Main.xNull, Main.yNull  + Main.fullSize, Main.fullSize, (int) (Main.size / 2));
		
		g.drawImage(Main.robotImg, Main.size * position + Main.slotPane.slotGap * (position + 1) + (int) (Main.size / 4) - Main.lineSize, 0, (int) (Main.size / 2), (int) (Main.size / 2), null);
	}
}
