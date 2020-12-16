import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class KeyEventClass extends JFrame implements KeyListener {
    
    public KeyEventClass(){
        this.setLayout(new BorderLayout());
        JTextField field = new JTextField();
        field.addKeyListener(this);
        this.add(field, BorderLayout.CENTER);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void keyTyped(KeyEvent e) {
        System.out.println("KeyTyped: ");
        if(e.getKeyChar() == KeyEvent.CHAR_UNDEFINED){
            System.out.println("Kein Unicode-Character gedr\u00FCckt!");
        }else{
            System.out.println(e.getKeyChar() + " gedr\u00FCckt!");
        }
        System.out.println("---");
    }
    public void keyPressed(KeyEvent e) {
        System.out.println("Taste: " + e.getKeyChar() + ", Code: " + e.getKeyCode());
        System.out.println("Tastenposition: " + e.getKeyLocation());
        System.out.println("---");
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("KeyReleased: ");
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            System.out.println("Programmabbruch!");
            System.exit(0);
        }    
        System.out.println("Taste: " + e.getKeyChar() + ", Code: " + e.getKeyCode());
        System.out.println("---");
    }
    
    public static void main(String[] args) {
        new KeyEventClass();
    }
}