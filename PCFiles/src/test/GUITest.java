package test;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.seisw.util.geom.Point2D;

import gui.GUI;

public class GUITest {
	private GUI gui = null;
	
	public GUITest() {
		try {
			createAndShowGUI();
			
	    	gui = GUI.getInstance();
	    	
	    	Thread.sleep(1500);
	        
	    	gui.addTxt("Adding line with cooardinates: [10,10], [100,100]");
	        gui.drawLine(new Point(10,10), new Point(100,100));
	        
	        Thread.sleep(1500);
	        
	        gui.addTxt("Adding line with cooardinates: [100,100], [50,200]");
	        gui.drawLine(new Point(100,100), new Point(50,200));
	        
	        Thread.sleep(1500);
	        
	        gui.addTxt("Adding line with cooardinates: [50,200], [10,10]");
	        gui.drawLine(new Point(50,200), new Point(10,10));
	        
	        gui.updateImage();
	        gui.updateImage("images/scrCap2.jpg");
        
        
			Thread.sleep(1500);
			
			gui.clearLines();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
