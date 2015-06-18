package gui;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import net.miginfocom.swing.MigLayout;

/**
 * Contains the console which will print all events done by the image processing, algorithm engine, and robot movements.
 * 
 * @author Kristin Hansen
 */

public class ConsolePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextArea console;
	private JScrollPane scroll;
	private TitledBorder title;
	
	public ConsolePanel() {
		setLayout(new MigLayout());
		setBackground(Color.decode("#333333"));
		setOpaque(false);
		
		console = new JTextArea(8,35);
		console.setBackground(Color.decode("#333333"));
		console.setBorder(BorderFactory.createEmptyBorder());
		console.setForeground(Color.decode("#FFFFFF"));
		console.setWrapStyleWord(false);
		console.setFocusable(false);
		console.setLineWrap(true);
		
		scroll = new JScrollPane(console);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		
		title = new TitledBorder("Console");
		title.setTitleFont(new Font("Arial", 1, 14));
		title.setTitleColor(Color.decode("#FFFFFF"));
		
		setBorder(title);
		
		add(scroll);
		
		addTxt("Init...");
	}
	
	public void addTxt(String txt) {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
		String time = sdf.format(cal.getTime());
		
		this.console.append("[" + time + "] " + txt + "\n");

		this.console.setCaretPosition(this.console.getText().length()-1);
	}
}