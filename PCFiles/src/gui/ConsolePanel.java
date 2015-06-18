package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

public class ConsolePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextArea console;
	private JScrollPane scroll;
	private JLabel title;
	
	public ConsolePanel() {
		setPreferredSize(new Dimension(587, 150));
		setLayout(new MigLayout());
		setBackground(Color.decode("#333333"));
		
		console = new JTextArea("Init...");
		console.setPreferredSize(new Dimension(320, 110));
		
		scroll = new JScrollPane(console);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		title = new JLabel("Console");
		title.setForeground(Color.decode("#FFFFFF"));
		
		add(title, "wrap");
		add(scroll);
	}
}
