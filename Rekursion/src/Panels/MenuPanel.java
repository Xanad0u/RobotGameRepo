package Panels;
import javax.swing.JPanel;

import Buttons.CallButton;
import Enums.ButtonAction;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.Box;
import java.awt.Color;

public class MenuPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2773349137843460858L;

	public MenuPanel(JFrame motherFrame, boolean isEditor) {
		setBackground(Color.LIGHT_GRAY);
		
		JButton btnNewButton = new JButton("Resume");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(30);
		add(verticalStrut);
		
		JLabel lblNewLabel = new JLabel("Pause Menu");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblNewLabel);
		
		Component verticalStrut_2 = Box.createVerticalStrut(40);
		add(verticalStrut_2);
		add(btnNewButton);
		
		Component verticalStrut_1 = Box.createVerticalStrut(25);
		add(verticalStrut_1);
		
		if(!isEditor) {
			JButton btnNewButton_1 = new CallButton("Stages", motherFrame, ButtonAction.STAGESELECTIONFRAME);
			btnNewButton_1.setBackground(Color.WHITE);
			btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(btnNewButton_1);
		}
		else {
			JButton btnNewButton_1 = new CallButton("Save", motherFrame, ButtonAction.SAVE);
			btnNewButton_1.setBackground(Color.WHITE);
			btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
			add(btnNewButton_1);
		}
		
		Component verticalStrut_3 = Box.createVerticalStrut(25);
		add(verticalStrut_3);
		
		JButton btnNewButton_2 = new CallButton("Menu", motherFrame, ButtonAction.MENUFRAME);
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnNewButton_2);
		
		setFocusable(true);
		
		
	}
}
