package gui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Mat;

import net.miginfocom.swing.MigLayout;

public class ImgPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	private JLabel title;
	
	public ImgPanel() {
		setPreferredSize(new Dimension(580, 200));
		setLayout(new MigLayout());
		
		img = new Mat();
		title = new JLabel("Latest image");
		
		add(title, "wrap");
	}
}
