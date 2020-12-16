import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Window extends JFrame  {
    
    public Window() {
    	initComponents();	  
    }
	  
    private void initComponents() {
	    setTitle("Robot");  // Fenstertitel setzen
	    setSize(665,1080);
	    
	    
	    
	    
		                          // Fenstergröße einstellen
	                                                     // (notwendig, damit das Fenster geschlossen werden kann)
		
		
		File file = new File("./img/Robot.png");
		Image img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setIconImage(img);
		setExtendedState(JFrame.MAXIMIZED_BOTH);


		                           // Fenster (inkl. Inhalt) sichtbar machen
		
		
		
		
    }
	  
    class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("clicked");
        }           
    }
	  
	/**
	 * @param args
	 */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Window();
                
            }
        }); // Erzeugt einen neuen Thread, der eine Instanz von TestJFrame erzeugt
    }
    
}
