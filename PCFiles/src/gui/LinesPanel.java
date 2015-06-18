package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class LinesPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Point> points = new ArrayList<Point>();
	
	public void addPoints(Point p1, Point p2) {
		points.add(p1);
		points.add(p2);
	}
	
	public void drawLine(Point p1, Point p2) {
		points.add(p1);
		points.add(p2);
		
		this.repaint();
	}
	
	public void clearLines() {
		points.clear();
		
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		Random ran = new Random();
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		
		for(int i = 0; i < points.size(); i++) {
			int red = ran.nextInt(256);
			int green = ran.nextInt(256);
			int blue = ran.nextInt(256);

			Color col = new Color(red,green,blue);
			
			g2d.setColor(col);
			
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
			
			i++;
		}
	}
}