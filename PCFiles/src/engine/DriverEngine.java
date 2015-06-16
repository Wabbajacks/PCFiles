package engine;

import java.awt.geom.Point2D;

import imgProcessing.*;
import communication.PCConn;

/**
 * DriverEngine is the controller class for the PC part of the project
 * @author Anders
 *
 */
public class DriverEngine {
	private PCConn con;
	private AlgoEngine algo;
	private ImgCap cam;
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
		con = new PCConn();
		algo = new AlgoEngine();
		cam = new ImgCap();
		engine();
	}

	public static void main(String args[]){
		new DriverEngine();
	}

	private void engine(){
		/* The only thing the engine should be running in a loop */ 
		while(true){
			ImgInfo camInfo = cam.picAnal();
			algo.run(camInfo.getBalls(), camInfo.getRobot(), camInfo.getFrame());
			System.out.println("" + algo.getInstruction());
			con.sendMsg(algo.getInstruction());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/* TO here */

		/* Dummy data test */ 
		/*		Point2D[] ballCoordinates = new Point2D[4];
		Point2D[] robotCoordinates = new Point2D[2];
		Point2D[] wallCoordinates = new Point2D[4];

		robotCoordinates[0] = new Point2D.Double(230, 120);
		robotCoordinates[1] = new Point2D.Double(200, 122);

		ballCoordinates[0] = new Point2D.Double(40, 30);
		ballCoordinates[1] = new Point2D.Double(800, 12);
		ballCoordinates[2] = new Point2D.Double(43, 87);
		ballCoordinates[3] = new Point2D.Double(123, 453);

		wallCoordinates [0] = new Point2D.Double(0, 1000);
		wallCoordinates [1] = new Point2D.Double(1000, 1000);
		wallCoordinates [2] = new Point2D.Double(0, 0);
		wallCoordinates [3] = new Point2D.Double(1000, 0);

		while(true){
			algo.run(ballCoordinates, robotCoordinates, wallCoordinates);
			System.out.println("commands");
			for (String s : algo.getInstruction()){
				System.out.print(s + " ");
			}
			System.out.println();
			con.sendMsg(algo.getInstruction());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} */
	}
}
