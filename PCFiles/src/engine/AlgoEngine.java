package engine;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

// Name to be changed
/**
 * AlgoEngine controls which instruction the robot should be given 
 * based on the coordinates sent by the cam Part
 * 
 * Program StartAlgoEngine: (if there is still balls within the "middle" of the field
 * Point2D ballCoordinateArray		(b_x, b_y)
 * Point2D robotCoordinateArray		(r_x, r_y)
 * 
 * String robotCommandArray
 * 
 * integer targetBall				(Array number indicator)
 * integer robotDirection			(0:-y		1:-x 		2:+y 		3:+x	when going forward)
 * double distance					(distance from robot to ball)
 * 
 * 
 * 
 * targetBall = findNearestBall(ballCoordinateArray, robotCoordinateArray)
 * robot Direction = findRobotDirection(robotCoordinateArray)
 * 
 * 	if(r_x != b_x)
 * 		if(r_x < b_x)
 * 			change robotDirection to 3
 * 			store changeDirectionCommand to robotCommandArray 
 * 		else
 * 			change robotDirection to 1
 * 			store chanceDirectionCommand to robotCommandArray
 * 		end if
 * 
 * 		calcDriveTime()
 * 		store forward(time) to robotCommandArray
 * 	end if
 * 
 * 	if(r_y != b_y)
 * 		if(r_y < b_y)
 * 			change robotDirection to 2
 * 			store chanceDirectionCommand to robotCommandArray
 * 		else
 * 			chance robotDirection to 0 
 * 			store chanceDirectionCommand to robotCommandArray
 * 		end if
 * 
 * 		calcDriveTime()
 * 		store forward(time) to robotCommandArray
 * 	end if
 * 
 *  return robotCommandArray
 *  
 * @author Anders
 *
 */
public class AlgoEngine {
	int targetBall;
	int robotDirection;
	double distance;
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
		targetBall = 0;
		distance = 0;
		robotDirection = 0;
		commands = new ArrayList<String>();
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
	 * robotDirection
	 * determines which direction the robot is looking
	 * 
	 * @param robot - Point2D Array containing the robots coordinates
	 * @return the integer value of the direction 	(0:-y		1:-x 		2:+y 		3:+x	when going forward)
	 */
	private int robotDirection(Point2D[] robot){
		/*	check which front back direction has the biggest difference to determine which axis the robot is on	 */
		if(Math.abs(robot[0].getX() - robot[1].getX()) > Math.abs(robot[0].getY() - robot[1].getY()) ){
			/* frontX smaller than backX then direction is 'LEFT' else ... */
			if(robot[0].getX() < robot[1].getX())	return 1;
			/* ... direction is 'RIGHT' */
			else return 3;
		} else {
			/* frontY smaller than backY then direction is 'DOWN' else ... */
			if(robot[0].getY() < robot[1].getY())	return 0;
			/* ... direction is 'UP' */
			else return 2;
		}
	}
	
	/**
	 * getInstruction
	 * if the getCoordinates have been executed it will return the right info.
	 *  
	 * @return String array with Instructions to robot.
	 */
	public String[] getInstruction(){
		
		return (String[]) commands.toArray();
	}
	
}
