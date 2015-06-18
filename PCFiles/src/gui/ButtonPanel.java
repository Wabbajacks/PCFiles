package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class ButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton clearLines;
	private JButton updateImage;

	public ButtonPanel() {
		TitledBorder title = new TitledBorder("Commands");
		title.setTitleFont(new Font("Arial", 1, 14));
		title.setTitleColor(Color.decode("#FFFFFF"));
		
		setLayout(new MigLayout());
		setPreferredSize(new Dimension(234,166));
		setBorder(title);
		setBackground(Color.decode("#333333"));
		
		clearLines = new JButton("Clear lines");
		updateImage = new JButton("Update image");
		
		add(clearLines, "wrap");
		add(updateImage);
	}

	public JButton getClearLines() {
		return clearLines;
	}

	public JButton getUpdateImage() {
		return updateImage;
	}
}
