package gui;

import gui.utils.CreateTextimage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	
	public ImgPanel() {
		Dimension dim = new Dimension(680, 530);
		
		setMinimumSize(dim);
		setPreferredSize(dim);
		setMaximumSize(dim);
		
		setLayout(new MigLayout());
		setBackground(Color.decode("#333333"));
		
		try {
			bimg = ImageIO.read(new File("images/scrCap.jpg"));
		} catch(IOException e) {
			bimg = CreateTextimage.createImage("No screen capture available.");
		}
		
		lpane = new JLayeredPane();
		lpane.setPreferredSize(dim);
		
		image = new JLabel(new ImageIcon(bimg));
		image.setPreferredSize(dim);
		image.setBounds(0, 0, bimg.getWidth(), bimg.getHeight());
		
		
		title = new TitledBorder("Latest image");
		title.setTitleFont(new Font("Arial", 1, 14));
		title.setTitleColor(Color.decode("#FFFFFF"));
		
		lpane.add(image, new Integer(1), 0);
		
		setBorder(title);
		
		add(lpane);
	}
	
	/**
	 * Will add a line to the image.
	 */
	public void addLine() {
		// TODO
	}
	
	/**
	 * Will update the image used by the GUI of the track.<br>
	 * 
	 * @param path 
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
	
	public void updateImage() {
		try {
			bimg = ImageIO.read(new File("images/scrCap.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		image.setIcon(new ImageIcon(bimg));
	}
}
