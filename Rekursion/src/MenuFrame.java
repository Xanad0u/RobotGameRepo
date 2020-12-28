import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.SwingConstants;
import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class MenuFrame extends JFrame {

	private JPanel contentPane;

	public MenuFrame() {
		File file = new File("./img/Icon.png");
		Image img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setIconImage(img);
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		Component verticalStrut_3 = Box.createVerticalStrut(150);
		contentPane.add(verticalStrut_3);
		
		JLabel lblTitle = new JLabel("Robot");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Consolas", Font.PLAIN, 99));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblTitle);
		
		Component verticalStrut_2 = Box.createVerticalStrut(200);
		contentPane.add(verticalStrut_2);
		
		JButton btnStageSelection = new CallButton("Stage Selection", this, "SSF");
		btnStageSelection.setToolTipText("Select a stage to play");
		btnStageSelection.setBackground(Color.WHITE);
		btnStageSelection.setVerticalAlignment(SwingConstants.BOTTOM);
		btnStageSelection.setFont(new Font("Consolas", Font.PLAIN, 32));
		btnStageSelection.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(btnStageSelection);
		
		Component verticalStrut = Box.createVerticalStrut(60);
		contentPane.add(verticalStrut);
		
		JButton btnLevelEditor = new CallButton("Level Editor", this, 5);
		btnLevelEditor.setToolTipText("Edit and create levels");
		btnLevelEditor.setBackground(Color.WHITE);
		btnLevelEditor.setVerticalAlignment(SwingConstants.BOTTOM);
		btnLevelEditor.setFont(new Font("Consolas", Font.PLAIN, 32));
		btnLevelEditor.setAlignmentX(0.5f);
		contentPane.add(btnLevelEditor);
		
		Component verticalStrut_1 = Box.createVerticalStrut(100);
		contentPane.add(verticalStrut_1);
		
		JButton btnExit = new CloseButton("Exit\r\n", this);
		btnExit.setToolTipText("Just stay");
		btnExit.setBackground(Color.WHITE);
		btnExit.setVerticalAlignment(SwingConstants.BOTTOM);
		btnExit.setFont(new Font("Consolas", Font.PLAIN, 32));
		btnExit.setAlignmentX(0.5f);
		contentPane.add(btnExit);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}

}
