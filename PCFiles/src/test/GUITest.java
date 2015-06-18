package test;

import gui.GUI;

public class GUITest {
	private GUI gui;
	
	public GUITest() {
		gui = GUI.getInstance();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.createAndShowGUI();
            }
        });
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gui.addTxt("Test");
		
		gui.addTxt("Test2");
	}
	
	public static void main(String[] args) {
		new GUITest();
	}
}
