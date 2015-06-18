package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class ConsolePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextArea console;
	private JScrollPane scroll;
	private TitledBorder title;
	
	public ConsolePanel() {
		setPreferredSize(new Dimension(450, 150));
		setLayout(new MigLayout());
		setBackground(Color.decode("#333333"));
		setOpaque(true);
		
		console = new JTextArea();
		console.setPreferredSize(new Dimension(450, 150));
		console.setBackground(Color.decode("#333333"));
		console.setBorder(BorderFactory.createEmptyBorder());
		console.setForeground(Color.decode("#FFFFFF"));
		console.setWrapStyleWord(false);
		console.setFocusable(false);
		console.setLineWrap(true);
		
		addTxt("Init...\n");
		
		scroll = new JScrollPane(console);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		
		title = new TitledBorder("Console");
		title.setTitleFont(new Font("Arial", 1, 14));
		title.setTitleColor(Color.decode("#FFFFFF"));
		
		setBorder(title);
		
		add(scroll);
	}
	
	public void addTxt(String txt) {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
		String time = sdf.format(cal.getTime());
		
		System.out.println("[" + time + "] " + txt + "\n");
		
		this.console.append("[" + time + "] " + txt + "\n");
	}
}