import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;

public class PopMenu extends JInternalFrame {

	/**
	 * Launch the application.
	 * 
	 *
	 */
	Sound sound = new Sound();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopMenu frame = new PopMenu();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
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
		btnResume.addActionListener(sound);
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
