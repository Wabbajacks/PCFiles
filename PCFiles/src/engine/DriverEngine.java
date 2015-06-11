package engine;

import java.awt.geom.Point2D;

import connection.PCConn;

/**
 * DriverEngine is the controller class for the PC part of the project
 * @author Anders
 *
 */
public class DriverEngine {
	private PCConn con;
	/* TODO:
	 * The DriverEngine runs the show
	 * 
	 * it sends the coordinates to AlgoEngine, which returns a set of instructions needed to be done.
	 * 
	 * It gives instructions to the robot through connection.PCConn
	 * 
	 * 
	 */
	public DriverEngine(){
		con = new PCConn();
		engine();
	}

	public static void main(String args[]){
		new DriverEngine();
	}
	
	private void engine(){
	    Point2D[] camCoordinates = new Point2D[4];
		String[] msg = null;
		
		
//		camCoordinates = new Point2D[4];
		camCoordinates[0] = new Point2D.Double(1, 2);
		camCoordinates[1] = new Point2D.Double(200, 300);
		camCoordinates[2] = new Point2D.Double(43, 87);
		camCoordinates[3] = new Point2D.Double(123, 453);
		
		con.sendMsg(msg);
		
	}
}
