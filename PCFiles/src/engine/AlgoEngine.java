package engine;

import java.awt.geom.Line2D;
//import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.Vector2D;

/**
 * AlgoEngine controls which instruction the robot should be given 
 * based on the coordinates sent by the cam Part
 * 
 * @author Anders
 *
 */
public class AlgoEngine {
	Point2D targetBall;
	Point2D robot_start;	
	int tolerance;
	double distance;
	String state;
	Vector2D robotV;
	Vector2D courseV;

	List<String> commands;
	/* TODO:
	 * Runs all the algorithms
	 *
	 * it should make a decision which ball it wish to go for and figure out which
	 * string of instructions has to be sent to fulfill the decision. 
	 * 
	 * it waits for a response from the robot, every time (?) it sends an instruction
	 * 
	 * A flowchart should be made for this class for the common understanding of its process
	*/

	/**
	 * AlgoEngine Constructor 
	 * initializes variables
	 */
	public AlgoEngine(){
		targetBall = null;
		tolerance = 15;
		distance = 0;
		state = "START";
//		robotV = null;
		courseV = null;
		
		robot_start = new Point2D(0, 0);
		
		commands = new ArrayList<String>();
	} 
	
	/**
	 * run
	 * Runned by the DriverEngine to start running a feedback from camera
	 * @param balls - Point2D[] of all the balls coordinates
	 * @param robot - Point2D[] of the robots coordinates
	 */
	public void run(Point2D[] balls, Point2D[] robot, Point2D[] wall){
		//REMOVE THIS SHIT!
		commands.clear();
	}
	
	public void run(Point2D[] balls, Point2D[] robot, Point2D[] wall, Point2D[] obst){
		// Obstacle can be made into a rectangle if needed
		commands.clear();
		switch(state){
		case "START":
			state = "CALCRUTE";
		case "CALCRUTE":
			setNearestBall(balls, robot, wall, obst);
			robotV = new Vector2D(robot[1], robot[0]);
			break;
		case "FETCHING":
			break;
		case "DELIVER":
			break;
		case "FINNISHED":
			break;
		default:
			System.out.println("What y'all doing here !?");
			break;
		}
		
	}
	
	/**
	 * setNearestBall
	 * which isn't within the reach of the boarders
	 * 
	 * @param balls - Point2D array of all the balls locations on the field.
	 * @param robot - Point2D array of the robot's location, starting with the front of the robot.
	 * @param wall - doesn't take balls to close to the wall. remove maybe?
	 * @param obst - same as wall. 
	 */
	private void setNearestBall(Point2D[] balls, Point2D[] robot, Point2D[] wall, Point2D[] obst){
		targetBall = null;
		distance = robot[0].distance(balls[0]);
		int NGD = 20 ;
		
		for(int i = 0 ; i < balls.length ; i++){
			double tempDist = robot[0].distance(balls[i]);

			if(distance >= tempDist){
				if(		Math.abs(balls[i].getX() - wall[0].getX()) > NGD &&
						Math.abs(balls[i].getY() - wall[0].getY()) > NGD &&
						Math.abs(balls[i].getX() - wall[1].getX()) > NGD &&
						Math.abs(balls[i].getY() - wall[1].getY()) > NGD &&
						Math.abs(balls[i].getX() - wall[2].getX()) > NGD &&
						Math.abs(balls[i].getY() - wall[2].getY()) > NGD &&
						Math.abs(balls[i].getX() - wall[3].getX()) > NGD &&
						Math.abs(balls[i].getY() - wall[3].getY()) > NGD 
						){	
				targetBall = balls[i];
				distance = tempDist;
				}
			}
		}
	}
	
	/**
	 * 
	 * @param l should always be the robots vector
	 * @param j Should always be the vector from the robot to the ball
	 * @return Degree from l to j counterclockwise
	 */
	public static int degree (Vector2D l, Vector2D j) {
		int degree = 0;
		
		degree = (int) Math.acos(((Vector2D.dot(l, j))/(Math.sqrt((l.x()*l.x())+(l.y()*l.y())+(Math.sqrt((j.x()*j.x()) + (j.y()*j.y())))))));
		
		return degree;
	}
	
	private boolean checkInterSection(Point2D[] obst, Line2D l) {
		Rectangle2D r = new Rectangle2D.Double(obst[0].getX(), obst[0].getY(), (obst[1].getX()-obst[0].getX()), (obst[0].getY()-obst[1].getY()));
		
		return l.intersects(r);
	}
	
	public Point2D courseStart(){
		return new Point2D(120, 120);
	}
	
	public Point2D courseEnd(){
		return new Point2D(200, 200);
	}

	/**
	 * getInstruction
	 * if the getCoordinates have been executed it will return the right info.
	 *  
	 * @return String array with Instructions to robot.
	 */
	public String[] getInstruction(){
		String[] arrayCommands = new String[commands.size()];
		commands.toArray(arrayCommands);
		return arrayCommands; 
	}
}
