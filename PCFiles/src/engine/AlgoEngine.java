package engine;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * AlgoEngine controls which instruction the robot should be given 
 * based on the coordinates sent by the cam Part
 * 
 * @author Anders
 *
 */
public class AlgoEngine {
	int targetBall;
	int robotDirection;
	int tolerance;
	boolean sweepMode;
	
	int startPointer;
	Point2D robot_start;
	double move_constant;
	
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

	 * Program AlgoEngine_Firststep: (if there is still balls within the "middle"-section of the field
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
	 * robotDirection = findRobotDirection(robotCoordinateArray)
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
	 */

	/**
	 * AlgoEngine Constructor 
	 * initializes variables
	 */
	public AlgoEngine(){
		targetBall = 0;
		distance = 0;
		robotDirection = 0;
		tolerance = 25;
		sweepMode = false;
		
		startPointer = 0;
		robot_start = new Point2D.Double(0, 0);
		move_constant = 0.0;
		
		commands = new ArrayList<String>();
	} 
	
	/**
	 * run
	 * Runned by the DriverEngine to start running a feedback from camera
	 * @param balls - Point2D[] of all the balls coordinates
	 * @param robot - Point2D[] of the robots coordinates
	 */
	public String[] run(Point2D[] balls, Point2D[] robot, Point2D[] wall){
		commands.clear();
		
		if (startPointer < 2)	setupStep(robot);
		if (startPointer > 1)	firstSteps(balls, robot, wall);
		
		return getInstruction();
	}
	
	/**
	 * setupStep
	 * First time makes robot move for t time.
	 * Second time calculates move constant for robot.
	 * @param robot - robot Point2D coordinates
	 */
	private void setupStep(Point2D[] robot){
		int t = 2000;
		if (startPointer == 0){
			commands.add("C_FW;;" + (t));
			robot_start = robot[0];
			System.out.println("First setupState");
		} else {
			double d = robot[0].distance(robot_start);
			d = 67.2;
			move_constant = (t/d);
			System.out.println("d: " + d);
			System.out.println("t: " + t);
			System.out.println("move_constant: " + move_constant);
			System.out.println("Second setupState");
		}
		startPointer++;
	}

	/**
	 * firstSteps
	 * Finds the nearest ball and set found ball to targetBall.
	 * 
	 * @param balls - Point2D array of all the balls locations on the field.
	 * @param robot - Point2D array of the robot's location, starting with the front of the robot.
	 */
	private void firstSteps(Point2D[] balls, Point2D[] robot, Point2D[] wall){
		setNearestBall(balls, robot, wall);
		robotDirection = robotDirection(robot);
		if (targetBall == -1){ 
			sweepMode = true;
			System.out.println("SweepMode on");
		}

		//System.out.println("robotdirection: " + robotDirection);
		
		//System.out.println(targetBall);
		if(!sweepMode){
			/* if robot_x doesn't fit ball_x do following ...*/
			if ( !((robot[0].getX() - tolerance) <= balls[targetBall].getX() && balls[targetBall].getX() <= (robot[0].getX() + tolerance)) ){
				if( robot[0].getX() < balls[targetBall].getX()){
					/* ... chance robotDirection to 3 */
					for (String s : changeDirection(3, robotDirection)){
						commands.add(s);
					}
					robotDirection = 3;
				} else {
					/* ... chance robotDirection to 1 */
					for (String s : changeDirection(1, robotDirection)){
						commands.add(s);
					}
					robotDirection = 1;
				}
				/* and add how far to drive forward*/
				commands.add("C_FW;;" + calcDriveTime(Math.abs(robot[0].getX() - balls[targetBall].getX())));
			}
			/* if robot_y doesn't fit ball_y do following ...*/
			if ( !((robot[0].getY() - tolerance) <= balls[targetBall].getY() && balls[targetBall].getY() <= (robot[0].getY() + tolerance)) ){
				if( robot[0].getY() < balls[targetBall].getX()){
					/* ... change robotDirection to 2*/
					for (String s : changeDirection(2, robotDirection)){
						commands.add(s);
					}
					robotDirection = 2;
				} else {
					/* ... change robotDirection to 0*/
					for (String s : changeDirection(0, robotDirection)){
						commands.add(s);
					}
					robotDirection = 0;
				}
				
				/* and add how far to drive forward*/
				commands.add("C_FW;;" + calcDriveTime(Math.abs(robot[0].getY() - balls[targetBall].getY())));
			}
		}
	}

	/**
	 * setNearestBall
	 * which isn't within the reach of the boarders
	 * 
	 * @param balls - Point2D array of all the balls locations on the field.
	 * @param robot - Point2D array of the robot's location, starting with the front of the robot.
	 */
	private void setNearestBall(Point2D[] balls, Point2D[] robot, Point2D[] wall){
		targetBall = -1;
		distance = robot[0].distance(balls[1]);
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
				targetBall = i;
				distance = tempDist;
				}
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
	 * changeDirection of the robot
	 * @param wantedDirection - The direction the robot should be set to
	 * @param robotDirection - The direction the robot is currently facing
	 * @return String Array of Commands
	 */
	private String[] changeDirection(int wantedDirection, int robotDirection){
		int switchChoose = (int)wantedDirection - robotDirection;
		List<String> tempList = new ArrayList<String>();

		switch (switchChoose){
		case -3: /* RIGHT */
			tempList.add("C_HR;;0");
			break;
		case -2: /* 180 */
			tempList.add("C_HR;;0");
			tempList.add("C_HR;;0");
			break;
		case -1: /* LEFT */
			tempList.add("C_HL;;0");
			break;
		case 0: /* NONE */
			break;
		case 1: /* RIGHT */
			tempList.add("C_HR;;0");
			break;
		case 2: /* 180 */
			tempList.add("C_HR;;0");
			tempList.add("C_HR;;0");
			break;
		case 3: /* LEFT */
			tempList.add("C_HL;;0");
			break;
		default:
			System.out.println("default");
			break;
		}
		String[] returnList = new String[tempList.size()];
		tempList.toArray(returnList);
		return returnList;
	}

	private int calcDriveTime(double distance){
		return (int) (distance*move_constant);
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
