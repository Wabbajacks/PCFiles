package test;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;

import gui.GUI;

public class GUITest {
	private GUI gui = null;
	
	public GUITest() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
            	gui = GUI.getInstance();
                createAndShowGUI();
            }
		});
	}
	
	public static void main(String[] args) {
		new GUITest();
	}
	
	public void createAndShowGUI() {
		// Create window
		JFrame f = new JFrame("Sheogorath GUI");
		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setBackground(Color.decode("#333333"));
		f.setResizable(false);

        // Create the content pane
        JComponent c = GUI.getInstance();
        
        c.setOpaque(false);
        f.setContentPane(c);

        // Draw the window
        f.pack();
        f.setVisible(true);
	}
}
