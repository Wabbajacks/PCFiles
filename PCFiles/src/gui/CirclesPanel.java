package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

/**
 * Draws a circle at a given coordinate.<br><br>
 * Use it to determine which ball is currently being pursued by the robot.
 * 
 * @author Kristin Hansen
 */
public class CirclesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Point active;
	
	private final int BALL_RADIUS = 7;
	
	public void setActive(Point active) {
		this.active = active;
	}
	
	public void drawActive(Point active) {
		this.active = active;
		
		this.repaint();
	}
	
	public void clearActive() {
		this.active = null;
		
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(active != null) {
			try {
				super.paintComponent(g);
				
				int diameter = BALL_RADIUS * 2;
				
				Graphics2D g2d = (Graphics2D) g;
				
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(Color.YELLOW);
				
				g.fillOval((active.x - BALL_RADIUS), (active.y - BALL_RADIUS), diameter, diameter);
			} catch(NullPointerException e) {
				// Nope....
			}
		}
	}
}
