package gui;

import java.awt.Dimension;
import java.awt.Point;

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
	 * Updates the image shown in the "Latest image"-panel.<br>
	 * If no string parameter with a path to file is given, the default image path will be used.
	 */
	public void updateImage() {
		ip.updateImage();
	}
	
	/**
	 * Updates the image shown in the "Latest image"-panel.
	 * 
	 * @param path Path to the new image.
	 */
	public void updateImage(String path) {
		ip.updateImage(path);
	}

	/**
	 * Add text to the console window in the GUI.
	 * 
	 * @param txt A string that has to be printed in the console window.
	 */
	public void addTxt(String txt) {
		cp.addTxt(txt);
	}
	
	public void drawLine(Point p1, Point p2) {
		ip.drawLine(p1, p2);
	}
	
	public void clearLines() {
		ip.clearLines();
	}
}
