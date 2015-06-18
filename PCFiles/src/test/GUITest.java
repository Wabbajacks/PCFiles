package test;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

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
	        
	        Random ran = new Random();
	        
	        for(int i = 0; i < 10; i++) {
	        	Thread.sleep(1000);
	        	int a = ran.nextInt(640);
	        	int b = ran.nextInt(640);
	        	int c = ran.nextInt(640);
	        	int d = ran.nextInt(640);
	        	
	        	gui.addTxt("Adding line with coordinates: [" + a + ", " + b + "], [" + c + ", " + d + "]");
	        	gui.drawLine(new Point(a, b), new Point(c,d));
	        }
	        
	        gui.addTxt("Updating image...");
	        gui.updateImage();
	        
			Thread.sleep(1500);
			
			gui.addTxt("Removing all lines.");
			gui.clearLines();
			
			Thread.sleep(900);
	    	
			gui.addTxt("Adding line with coordinates: [0, 0], [640,480]");
			gui.drawLine(new Point(0,0), new Point(640,480));
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
