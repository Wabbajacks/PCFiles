package engine;

import java.awt.geom.Point2D;

import communication.PCConn;

/**
 * DriverEngine is the controller class for the PC part of the project
 * @author Anders
 *
 */
public class DriverEngine {
	private PCConn con;
	private AlgoEngine algo;
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
//		con = new PCConn();
		algo = new AlgoEngine();
		engine();
	}

	public static void main(String args[]){
		new DriverEngine();
	}
	
	private void engine(){
	    Point2D[] ballCoordinates = new Point2D[2];
	    Point2D[] robotCoordinates = new Point2D[2];
//		String[] msg = null;
		
//		camCoordinates = new Point2D[4];
	    
	    robotCoordinates[0] = new Point2D.Double(230, 120);
	    robotCoordinates[1] = new Point2D.Double(200, 122);
	    
		ballCoordinates[0] = new Point2D.Double(1, 2);
		ballCoordinates[1] = new Point2D.Double(2000, 3000);
//		ballCoordinates[2] = new Point2D.Double(43, 87);
//		ballCoordinates[3] = new Point2D.Double(123, 453);
		
		while(true){
			algo.run(ballCoordinates, robotCoordinates);
			System.out.println("commands");
			for (String s : algo.getInstruction()){
				System.out.print(s + " ");
			}
			System.out.println();
//			con.sendMsg(algo.getInstruction());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
}
