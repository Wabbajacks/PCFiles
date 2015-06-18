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
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class ImgPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage bimg;
	private JLabel image;
	private TitledBorder title;
	
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
		
		image = new JLabel(new ImageIcon(bimg));
		image.setPreferredSize(dim);
		
		title = new TitledBorder("Latest image");
		title.setTitleFont(new Font("Arial", 1, 14));
		title.setTitleColor(Color.decode("#FFFFFF"));
		
		setBorder(title);
		
		add(image);
	}
}
