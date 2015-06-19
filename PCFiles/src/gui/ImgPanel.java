package gui;

import gui.utils.CreateTextimage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

/**
 * Panel that contains the current/latest image processed by the image processing.
 * 
 * @author Kristin Hansen
 */
public class ImgPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage bimg;
	private JLabel image;
	private TitledBorder title;
	private JLayeredPane lpane;
	private LinesPanel lines;
	private CirclesPanel circle;
	
	private final String DEFAULT_IMAGE_PATH = "images/scrCap.jpg";
	
	public ImgPanel() {
		try {
			bimg = ImageIO.read(new File(DEFAULT_IMAGE_PATH));
		} catch(IOException e) {
			bimg = CreateTextimage.createImage("No screen capture available.");
		}
		
		Dimension dim = new Dimension(bimg.getWidth(), bimg.getHeight());
		
		setLayout(new MigLayout());
		setBackground(Color.decode("#333333"));
		
		lpane = new JLayeredPane();
		lpane.setMinimumSize(dim);
		lpane.setPreferredSize(dim);
		lpane.setMaximumSize(dim);
		
		image = new JLabel(new ImageIcon(bimg));
		image.setMinimumSize(dim);
		image.setPreferredSize(dim);
		image.setMaximumSize(dim);
		image.setBounds(0, 0, bimg.getWidth(), bimg.getHeight());
		
		lines = new LinesPanel();
		lines.setMinimumSize(image.getMinimumSize());
		lines.setPreferredSize(image.getPreferredSize());
		lines.setMaximumSize(image.getMaximumSize());
		lines.setBounds(image.getBounds());
		lines.setOpaque(false);
		
		circle = new CirclesPanel();
		circle.setMinimumSize(image.getMinimumSize());
		circle.setPreferredSize(image.getPreferredSize());
		circle.setMaximumSize(image.getMaximumSize());
		circle.setBounds(image.getBounds());
		circle.setOpaque(false);
		
		title = new TitledBorder("Latest image");
		title.setTitleFont(new Font("Arial", 1, 14));
		title.setTitleColor(Color.decode("#FFFFFF"));
		
		lpane.add(image, 0, 0);
		lpane.add(circle, 1, 0);
		lpane.add(lines, 2, 0);
		
		setBorder(title);
		
		add(lpane);
	}
	
	public void updateImage(String path) {
		try {
			bimg = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		image.setIcon(new ImageIcon(bimg));
	}
	
	public void updateImage() {
		try {
			bimg = ImageIO.read(new File(DEFAULT_IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		image.setIcon(new ImageIcon(bimg));
	}
	
	public void drawLine(Point p1, Point p2) {
		lines.drawLine(p1, p2);
	}

	public void clearLines() {
		lines.clearLines();
	}
	
	public void drawActive(Point active) {
		circle.drawActive(active);
	}

	public void clearActive() {
		circle.clearActive();
	}
}
