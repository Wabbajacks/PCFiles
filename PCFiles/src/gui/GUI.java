package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

/**
 * GUI for robot movements.
 * 
 * Draws all movements passed to the robot.
 * 
 * @author Kristin Hansen
 */

public class GUI extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Image img;
	private Point2D from;
	private Point2D to;
	
	private static GUI instance = null;
	
	private ImgPanel ip;
	private ConsolePanel cp;
	
	/**
	 * GUI class constructor.
	 */
	private GUI() {
		// Set layout specific settings for GUI frame
		setLayout(new MigLayout());
		setPreferredSize(new Dimension(693, 700));
		setOpaque(false);
		
		// Create main panels
		ip = new ImgPanel();
		cp = new ConsolePanel();
		
		add(ip, "wrap");
		add(cp);
	}
	
	/**
	 * Will return the current instance of the GUI.<br><br>
	 * 
	 * Instantiates a GUI if this hasn't been done yet.<br><br>
	 * 
	 * Note: the GUI class constructor is considered a singleton.
	 * @return The current instance of the GUI.
	 */
	public static GUI getInstance() {
		if(instance == null) instance = new GUI();
		
		return instance;
	}
	
	
	/**
	 * Instantiates the GUI and creates the necessary panes/panels.<br><br>
	 */
	public void createAndShowGUI() {
		// Create window
		JFrame f = new JFrame("Sheogorath GUI");
		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setBackground(Color.decode("#333333"));
		f.setResizable(false);

        // Create the content pane
        JComponent c = instance;
        
        c.setOpaque(false);
        f.setContentPane(c);

        // Draw the window
        f.pack();
        f.setVisible(true);
	}
	
	/**
	 * 
	 * @param img
	 */
	public void updateImage(Image img) {
		// TODO
	}

	/**
	 * Add text to the console window in the GUI.
	 * 
	 * @param txt A string that has to be printed in the console window.
	 */
	public void addTxt(String txt) {
		cp.addTxt(txt);
	}
}
