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
	
	private final String DEFAULT_IMAGE_PATH = "images/scrCap.jpg";
	
	public ImgPanel() {
		Dimension dim = new Dimension(680, 530);
		
		setMinimumSize(dim);
		setPreferredSize(dim);
		setMaximumSize(dim);
		
		setLayout(new MigLayout());
		setBackground(Color.decode("#333333"));
		
		try {
			bimg = ImageIO.read(new File(DEFAULT_IMAGE_PATH));
		} catch(IOException e) {
			bimg = CreateTextimage.createImage("No screen capture available.");
		}
		
		lpane = new JLayeredPane();
		lpane.setPreferredSize(dim);
		
		image = new JLabel(new ImageIcon(bimg));
		image.setPreferredSize(dim);
		image.setBounds(0, 0, bimg.getWidth(), bimg.getHeight());
		
		lines = new LinesPanel();
		lines.setPreferredSize(image.getPreferredSize());
		lines.setBounds(image.getBounds());
		lines.setOpaque(false);
		
		title = new TitledBorder("Latest image");
		title.setTitleFont(new Font("Arial", 1, 14));
		title.setTitleColor(Color.decode("#FFFFFF"));
		
		lpane.add(image, new Integer(1), 0);
		lpane.add(lines, new Integer(2), 0);
		
		setBorder(title);
		
		add(lpane);
	}
	
	/**
	 * Will update the image used by the GUI of the track.<br>
	 * 
	 * @param path Path to the new image.
	 */
	public void updateImage(String path) {
		try {
			bimg = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		image.setIcon(new ImageIcon(bimg));
	}
	
	/**
	 * Will update the image used by the GUI of the track.<br><br>
	 * 
	 * If no parameter is given the default path {@link #DEFAULT_IMAGE_PATH} will be used.
	 */
	public void updateImage() {
		try {
			bimg = ImageIO.read(new File(DEFAULT_IMAGE_PATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		image.setIcon(new ImageIcon(bimg));
	}
	
	/**
	 * 
	 * @param p1
	 * @param p2
	 */
	public void drawLine(Point p1, Point p2) {
		lines.drawLine(p1, p2);
	}
}
