package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

/**
 * Contains buttons used by GUI. Add events to the buttons with the get-methods for each button
 * <ul>
 * <li>{@link #getClearLinesButton()}</li>
 * <li>{@link #getClearActiveButton()}</li>
 * <li>{@link #getUpdateImageButton()}</li>
 * </ul>
 * 
 * @author Kristin Hansen
 */
public class ButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton clearLinesButton;
	private JButton clearActiveButton;
	private JButton updateImageButton;

	public ButtonPanel() {
		TitledBorder title = new TitledBorder("Commands");
		title.setTitleFont(new Font("Arial", 1, 14));
		title.setTitleColor(Color.decode("#FFFFFF"));
		
		setLayout(new MigLayout());
		setPreferredSize(new Dimension(234,166));
		setBorder(title);
		setBackground(Color.decode("#333333"));
		
		clearLinesButton = new JButton("Clear lines");
		clearActiveButton = new JButton("Clear active ball");
		updateImageButton = new JButton("Update image");
		
		add(clearLinesButton, "wrap");
		add(clearActiveButton, "wrap");
		add(updateImageButton);
	}

	public JButton getClearLinesButton() {
		return clearLinesButton;
	}

	public JButton getUpdateImageButton() {
		return updateImageButton;
	}
	
	public JButton getClearActiveButton() {
		return clearActiveButton;
	}
}
