package engine;

import java.awt.geom.Point2D;

// Name to be changed
/**
 * AlgoEngine controls which instruction the robot should be given 
 * based on the coordinates sent by the cam Part
 * @author Anders
 *
 */
public class AlgoEngine {
	int targetBall;
	double distance;
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
		targetBall = 0;
		distance = 0;
	}
	
	/**
	 * getCoodinates
	 * Finds the nearest ball and set found ball to targetBall.
	 * 
	 * @param balls - Point2D array of all the balls locations on the field.
	 * @param robot - Point2D array of the robot's location, starting with the front of the robot.
	 */
	public void getCoordinates(Point2D[] balls, Point2D[] robot){
		setNearestBall(balls, robot);
		
	}
	
	/**
	 * setNearestBall
	 * 
	 * @param balls - Point2D array of all the balls locations on the field.
	 * @param robot - Point2D array of the robot's location, starting with the front of the robot.
	 */
	private void setNearestBall(Point2D[] balls, Point2D[] robot){
		targetBall = 0;
		distance = 1000;
		
		for(int i = 0 ; i < balls.length ; i++){
			double tempDist = robot[0].distance(balls[i]);
			
			if(distance > tempDist){
				targetBall = i;
				distance = tempDist;
			}
		}
	}
	
	/**
	 * getInstruction
	 * if the getCoordinates have been executed it will return the right info.
	 *  
	 * @return String array with Instructions to robot.
	 */
	public String[] getInstruction(){
		return null;
	}
	
}
