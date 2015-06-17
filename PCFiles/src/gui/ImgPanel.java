package gui;

import gui.utils.CreateTextimage;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ImgPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage bimg;
	private JLabel image;
	private JLabel title;
	
	public ImgPanel() {
		setPreferredSize(new Dimension(587, 240));
		setLayout(new MigLayout());
		
		try {
			bimg = ImageIO.read(new File("images/scrCap.jpg"));
		} catch(IOException e) {
			bimg = CreateTextimage.createImage("No screen capture available.");
		}
		
		image = new JLabel(new ImageIcon(bimg));
		title = new JLabel("Latest image");
		
		add(title, "wrap");
		add(image);
	}
}
