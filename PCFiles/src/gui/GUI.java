package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private ButtonPanel bp;
	
	/**
	 * GUI class constructor.
	 */
	private GUI() {
		// Set layout specific settings for GUI frame
		setLayout(new MigLayout());
		setOpaque(false);
		
		// Create main panels
		ip = new ImgPanel();
		cp = new ConsolePanel();
		bp = new ButtonPanel();
		
		bp.getClearLines().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearLines();
				addTxt("Removing all lines.");
			}
			
		});
		
		bp.getUpdateImage().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateImage();
				addTxt("Updating image.");
			}
			
		});
		
		add(ip, "wrap, span");
		add(cp);
		add(bp);
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
	
	/**
	 * Adds a line to the current image. It takes two parameters, two points.<br>
	 * 
	 * The two points are two coordinate sets a "from" and a "to" point, defining the boundaries of the line.
	 * 
	 * @param from The x,y-coordinate "from"
	 * @param to The x,y-coordinate "to"
	 */
	public void drawLine(Point from, Point to) {
		ip.drawLine(from, to);
	}
	
	/**
	 * Clears all drawn lines from the current "Latest image"-panel in the GUI.<br>
	 * Note: this cannot be reversed.
	 */
	public void clearLines() {
		ip.clearLines();
	}
}
