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
import java.awt.Container;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

public class StageSelectionFrame extends JFrame{
	
	private final double standartSizeSmall = 0.01;
	private final double standartSizeBig = 0.02;
	private final int buttonAmount = 12;
	private JPanel contentPane;
	public CallButton[] buttonArray = new CallButton[buttonAmount];
	private JButton btnNewButton_9;
	private JButton btnNewButton_10;
	private JButton btnNewButton_11;
	
	public int currentPage = 0;
	
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
		
		JPanel levelPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				for (int i = 0; i < buttonArray.length; i++) {
					buttonArray[i].setFont(new Font("Tahoma", Font.PLAIN, (int) (contentPane.getWidth() * standartSizeBig)));
					
					switch(Main.fileManager.getStageStatus(i + 1 + currentPage * buttonAmount)) {
					case COMPLETE:
						buttonArray[i].setForeground(Color.BLACK);
						buttonArray[i].setBackground(Color.GREEN);
						break;
					case NOTCOMPLETE:
						buttonArray[i].setForeground(Color.BLACK);
						buttonArray[i].setBackground(Color.WHITE);
						break;
					case NULL:
						buttonArray[i].setForeground(new Color(191, 191, 191));
						buttonArray[i].setBackground(new Color(212, 212, 212));
						break;
					
					}
				}
			}
		};
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
			buttonArray[i] = new CallButton(this, i + 1);
			buttonArray[i].setFont(new Font("Tahoma", Font.PLAIN, 1));
			switch(Main.fileManager.getStageStatus(i + 1)) {
			case COMPLETE:
				buttonArray[i].setForeground(Color.BLACK);
				buttonArray[i].setBackground(Color.GREEN);
				break;
			case NOTCOMPLETE:
				buttonArray[i].setForeground(Color.BLACK);
				buttonArray[i].setBackground(Color.WHITE);
				break;
			case NULL:
				buttonArray[i].setForeground(new Color(191, 191, 191));
				buttonArray[i].setBackground(new Color(212, 212, 212));
				break;
			
			}
			
			
			levelPanel.add(buttonArray[i]);
		}
		
		JPanel controlPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				btnNewButton_9.setFont(new Font("Tahoma", Font.PLAIN, (int) (contentPane.getWidth() * standartSizeSmall)));
				btnNewButton_10.setFont(new Font("Tahoma", Font.PLAIN, (int) (contentPane.getWidth() * standartSizeSmall)));
				btnNewButton_11.setFont(new Font("Tahoma", Font.PLAIN, (int) (contentPane.getWidth() * standartSizeSmall)));
			}
		};
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
		
		btnNewButton_9 = new CallButton("<<", this, ButtonAction.PREVIOUSPAGE);
		btnNewButton_9.setFont(new Font("Tahoma", Font.PLAIN, 1));
		btnNewButton_9.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_9.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnNewButton_9);
		
		btnNewButton_11 = new CallButton("return to menu", this, ButtonAction.MENUFRAME);
		btnNewButton_11.setFont(new Font("Tahoma", Font.PLAIN, 1));
		btnNewButton_11.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_11.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnNewButton_11);
		
		btnNewButton_10 = new CallButton(">>", this, ButtonAction.NEXTPAGE);
		btnNewButton_10.setFont(new Font("Tahoma", Font.PLAIN, 1));
		btnNewButton_10.setHorizontalAlignment(SwingConstants.RIGHT);
		btnNewButton_10.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnNewButton_10.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnNewButton_10);

		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		revalidate();
	}
	
	public void goToPage(int page) {
		for (int i = 0; i < buttonArray.length; i++) {
			buttonArray[i].changeCallStage(buttonArray[i].level + (page - currentPage) * buttonAmount);
		}
		repaint();
		currentPage = page;
	}
}

