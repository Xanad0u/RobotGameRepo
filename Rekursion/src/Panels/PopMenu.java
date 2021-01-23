package Panels;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.Box;

public class PopMenu extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1305429654084834251L;


	public PopMenu() {
		setBounds(100, 100, 171, 248);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_2);
		
		JLabel lblPauseMenu = new JLabel("Pause menu");
		lblPauseMenu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPauseMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblPauseMenu);
		
		Component verticalStrut = Box.createVerticalStrut(40);
		panel.add(verticalStrut);
		
		JButton btnResume = new JButton("Resume");
		btnResume.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnResume);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_1);
		
		JButton btnLevels = new JButton("Levels");
		btnLevels.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnLevels);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel.add(verticalStrut_3);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(btnMenu);
		
		
		setVisible(true);

	}
}
