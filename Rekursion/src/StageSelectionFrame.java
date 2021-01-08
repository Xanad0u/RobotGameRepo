import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JButton;

import java.awt.FlowLayout;
import javax.imageio.ImageIO;
import javax.swing.SpringLayout;
import java.awt.Color;

import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

public class StageSelectionFrame extends JFrame{
	
	private final int buttonAmount = 12;
	private JPanel contentPane;
	public CallButton[] buttonArray = new CallButton[buttonAmount];
	
	public StageSelectionFrame() {
		File file = new File("./img/Icon.png");
		Image img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setIconImage(img);
		
		
		setTitle("Stage Selection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		

		
		SpringLayout sl_contentPane = new SpringLayout();

		contentPane.setLayout(sl_contentPane);
		
		JPanel levelPanel = new JPanel();
		levelPanel.setBackground(Color.WHITE);
		levelPanel.setAlignmentY(Component.TOP_ALIGNMENT);

		JPanel mainPanel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, mainPanel, -50, SpringLayout.SOUTH, contentPane);
		mainPanel.setBackground(Color.WHITE);
		sl_contentPane.putConstraint(SpringLayout.NORTH, mainPanel, 65, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, mainPanel, 500, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, mainPanel, -500, SpringLayout.EAST, contentPane);
		contentPane.add(mainPanel);
		SpringLayout sl_mainPanel = new SpringLayout();
		sl_mainPanel.putConstraint(SpringLayout.NORTH, levelPanel, 0, SpringLayout.NORTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, levelPanel, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, levelPanel, 0, SpringLayout.EAST, mainPanel);
		mainPanel.setLayout(sl_mainPanel);
		

		sl_contentPane.putConstraint(SpringLayout.NORTH, levelPanel, 75, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, levelPanel, 500, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, levelPanel, -100, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, levelPanel, -500, SpringLayout.EAST, contentPane);
		mainPanel.add(levelPanel);
		levelPanel.setLayout(new GridLayout(4, 3, 1, 1));
		
		
		for(int i = 0; i < buttonAmount; i++) {
			CallButton btnNewButton = new CallButton(Integer.toString(i + 1), this, i + 1);
			btnNewButton.setForeground(Color.BLACK);
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 32));
			btnNewButton.setBackground(Color.WHITE);
			levelPanel.add(btnNewButton);
			
			buttonArray[i] = btnNewButton;
		}
		
		JPanel controlPanel = new JPanel();
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, levelPanel, -30, SpringLayout.NORTH, controlPanel);
		sl_mainPanel.putConstraint(SpringLayout.NORTH, controlPanel, -50, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.WEST, controlPanel, 0, SpringLayout.WEST, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.SOUTH, controlPanel, 0, SpringLayout.SOUTH, mainPanel);
		sl_mainPanel.putConstraint(SpringLayout.EAST, controlPanel, 0, SpringLayout.EAST, mainPanel);
		controlPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		sl_contentPane.putConstraint(SpringLayout.WEST, controlPanel, 500, SpringLayout.WEST, contentPane);
		controlPanel.setBackground(Color.WHITE);
		sl_contentPane.putConstraint(SpringLayout.NORTH, controlPanel, 15, SpringLayout.SOUTH, levelPanel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, controlPanel, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, controlPanel, -500, SpringLayout.EAST, contentPane);
		mainPanel.add(controlPanel);
		FlowLayout fl_panel_1 = new FlowLayout(FlowLayout.CENTER, 10, 0);
		controlPanel.setLayout(fl_panel_1);
		
		JButton btnNewButton_9 = new JButton("<<");
		btnNewButton_9.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNewButton_9.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_9.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnNewButton_9);
		
		JButton btnNewButton_11 = new CallButton("return to menu", this, ButtonAction.MENUFRAME);
		btnNewButton_11.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNewButton_11.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_11.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnNewButton_11);
		
		JButton btnNewButton_10 = new JButton(">>");
		btnNewButton_10.setFont(new Font("Tahoma", Font.PLAIN, 32));
		btnNewButton_10.setHorizontalAlignment(SwingConstants.RIGHT);
		btnNewButton_10.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnNewButton_10.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnNewButton_10);
		
		/*
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_2, 300, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_2, 900, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_2, 600, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_2, -900, SpringLayout.EAST, contentPane);
		contentPane.add(panel_2);
		
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		levelPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "move up");
		levelPanel.getActionMap().put("move up", new PopAction(panel_2, buttonArray));
		panel_2.setVisible(false);
		
		contentPane.setComponentZOrder(mainPanel, 1);
		contentPane.setComponentZOrder(panel_2, 0);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_2.add(verticalStrut);
		
		JLabel lblPauseMenu = new JLabel("Pause menu");
		lblPauseMenu.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPauseMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(lblPauseMenu);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		panel_2.add(verticalStrut_1);
		
		JButton btnResume = new JButton("Resume");
		btnResume.setBackground(Color.WHITE);
		btnResume.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnResume.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnResume);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel_2.add(verticalStrut_2);
		
		JButton btnLevels = new JButton("Levels");
		btnLevels.setBackground(Color.WHITE);
		btnLevels.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLevels.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnLevels);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel_2.add(verticalStrut_3);
		
		JButton btnMenu = new JButton("Menu");
		btnMenu.setBackground(Color.WHITE);
		btnMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_2.add(btnMenu);
		*/
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
}

