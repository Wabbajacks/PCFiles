package test;

import gui.GUI;

public class GUITest {
	private GUI gui;
	
	public GUITest() {
		gui = GUI.getInstance();
		
		gui.createAndShowGUI();
		
		gui.updateImage(null);
	}
	
	public static void main(String[] args) {
		new GUITest();
	}
}
