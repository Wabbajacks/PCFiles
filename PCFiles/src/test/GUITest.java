package test;

import gui.GUI;

public class GUITest {
	private GUI gui = null;
	
	public GUITest() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
            	gui = GUI.getInstance();
            	
                gui.createAndShowGUI();
                
                gui.addTxt("Test1");
                
                gui.addTxt("Test2");
            }
		});
	}
	
	public static void main(String[] args) {
		new GUITest();
	}
}
