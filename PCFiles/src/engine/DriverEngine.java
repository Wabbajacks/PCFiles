package engine;

import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JFrame;

import gui.GUI;
import imgProcessing.*;
import comm.PCConn;

/**
 * DriverEngine is the controller class for the PC part of the project
 * @author Anders
 *
 */
public class DriverEngine {
	private PCConn con;
	private AlgoEngine algo;
	private ImgCap cam;
	private GUI gui;
	
	private final String GUI_NAME = "Sheogorath GUI";
	
	/* TODO:
	 * The DriverEngine runs the show
	 * 
	 * recieves coordinates of all the balls and robot from imgProcessing.imgInfo
	 * 
	 * it sends the coordinates to AlgoEngine, which returns a set of instructions needed to be done.
	 * 
	 * It gives instructions to the robot through connection.PCConn
	 * 
	 * 
	 */
	public DriverEngine(){
		try {
			con = new PCConn("00165304789F");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		algo = new AlgoEngine();
		cam = new ImgCap();
		engine();
		
		// Start GUI
		createAndShowGUI();
		
		// Get instance of GUI
		gui = GUI.getInstance();
		
		// Set initial image in gui
		gui.updateImage();
	}
	
	private void createAndShowGUI() {
		// Create window
		JFrame f = new JFrame(GUI_NAME);
		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setBackground(Color.decode("#333333"));
		f.setResizable(false);

        // Create the content pane
        JComponent c = GUI.getInstance();
        
        c.setOpaque(false);
        f.setContentPane(c);

        // Draw the window
        f.pack();
        f.setVisible(true);
	}

	public static void main(String args[]){
		new DriverEngine();
	}

	private void engine(){
		/* The only thing the engine should be running in a loop */ 
		while(true){
			ImgInfo camInfo = cam.picAnal();
			algo.run(camInfo.getBalls(), camInfo.getRobot(), camInfo.getFrame());
			for (String s : algo.getInstruction()){
				System.out.print(s + " ");
			}
			System.out.println();
			
			for(String s : algo.getInstruction()) {
				try {
					con.sendCommand(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		/* TO here */

		/* Dummy data test */ 
//				Point2D[] ballCoordinates = new Point2D[4];
//		Point2D[] robotCoordinates = new Point2D[2];
//		Point2D[] wallCoordinates = new Point2D[4];
//
//		robotCoordinates[0] = new Point2D.Double(230, 120);
//		robotCoordinates[1] = new Point2D.Double(200, 122);
//
//		ballCoordinates[0] = new Point2D.Double(40, 30);
//		ballCoordinates[1] = new Point2D.Double(800, 12);
//		ballCoordinates[2] = new Point2D.Double(43, 87);
//		ballCoordinates[3] = new Point2D.Double(123, 453);
//
//		wallCoordinates [0] = new Point2D.Double(0, 1000);
//		wallCoordinates [1] = new Point2D.Double(1000, 1000);
//		wallCoordinates [2] = new Point2D.Double(0, 0);
//		wallCoordinates [3] = new Point2D.Double(1000, 0);
//
//		while(true){
//			algo.run(ballCoordinates, robotCoordinates, wallCoordinates);
//			System.out.println("commands");
//			for (String s : algo.getInstruction()){
//				System.out.print(s + " ");
//			}
//			System.out.println();
//			
//			try {
//				con.sendMsg(algo.getInstruction());
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} /* Dummy data end*/
	}
}
