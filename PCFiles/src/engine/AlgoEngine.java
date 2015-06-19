package engine;

//import java.awt.geom.Line2D;
//import java.awt.geom.Point2D;
import imgProcessing.ImgCap;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import math.geom2d.line.*;
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
	final int GD;
	Point2D targetBall;
	Point2D robot_start;	
	int tolerance;
	double distance;
	String state;
	Vector2D robotV;
	Vector2D courseV;
	Point2D deliverP2D;
	int ballsCollected;

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
		ballsCollected = 0;
		GD = 50;
		
		robot_start = new Point2D(0, 0);
		
		commands = new ArrayList<String>();
	} 
	
	/**
	 * run
	 * Runned by the DriverEngine to start running a feedback from camera
	 * @param balls - Point2D[] of all the balls coordinates
	 * @param robot - Point2D[] of the robots coordinates
	 */
	
	public void run(Point2D[] balls, Point2D[] robot, Point2D[] wall, Point2D[] obst, Point2D[] goals){
		// Obstacle can be made into a rectangle if needed
		commands.clear();
		switch(state){
		/* START */
		case "START":
			state = "CALCRUTE";
		case "CALCRUTE":
			setNearestBall(balls, robot, wall, obst);
			
			state = "FETCHING";
		case "FETCHING":
			/* FETCHING
			 * yes
			 * */
			setNearestBall(balls, robot, wall, obst);
			robotV = new Vector2D(robot[1], robot[0]);
			courseV = new Vector2D(robot[0], targetBall);
			if(checkInterSection(obst, robot[0], targetBall)){
				if (Math.abs(robot[0].getX() - targetBall.getX()) > Math.abs(robot[0].getY() - targetBall.getY())){
					int i = 50;
					if (obst[0].getY() < robot[0].getY()) i = -50;
					courseV = new Vector2D(robot[0], new Point2D(robot[0].getX(), obst[0].getY()+i));
				} else {
					int i = 50;
					if (obst[0].getX() < robot[0].getX()) i = -50;
					courseV = new Vector2D(robot[0], new Point2D(obst[0].getX()+i, robot[0].getY()));
				}
				state = "REDIRECTED";
				break;
			}
			double degree = degree(robotV, courseV);
			
			if(turnRight(degree)==true) {
				// Turn right
				degree = 360-degree;
				commands.add("C_TR " + degree);
			}
			else{
				// Turn left
				commands.add("C_TL " + degree);
			}
			if(degree < tolerance ){
				commands.add("FW");
			} else commands.add("BR");
			
			//commands.add("C_FW");
			
			ballsCollected++;
			
			
			break;
		case "REDIRECTED":
			
			/* REDIRECTED
			 * When the Robot is trying to move trough the obstacle in the middle it should be put in this state,
			 * moving at a redirected course, moving around the obstacle in the middle,
			 * so when it reaches its goal it should not increment the BallCatchCounter and should then go
			 * to FETCHING state again.
			 */
			
			
			//state = "FETCHING";
			
			break;
			
		case "DELIVER":
			switch(state){
			
				case "DELIVERROUTE":
					deliverP2D = new Point2D(goals[0].getX()+GD, goals[0].getY());
					courseV = new Vector2D(deliverP2D.getX(), goals[0].getY());
					robotV = new Vector2D(robot[1], robot[0]);
					
					if(checkInterSection(obst, robot[0], deliverP2D)){
						courseV = new Vector2D(deliverP2D.getX(), robot[1].getX());
						robotV = new Vector2D(robot[1],robot[0]);
						
						degree = degree(robotV, courseV);
						
						if(turnRight(degree)==true) {
							// Turn right
							degree = 360-degree;
							commands.add("C_TR " + degree);
						}
						else{
							// Turn left
							commands.add("C_TL " + degree);
						}
						
						commands.add("C_FW");
						
						break;
					}
					
					if(robot[0].getX() == goals[0].getX()+GD && robot[0].getY() == goals[0].getY()){
						courseV = new Vector2D(goals[0].getX(), goals[0].getY());
						robotV = new Vector2D(robot[1], robot[0]);
						degree = degree(robotV, courseV);
						
						if(turnRight(degree)==true) {
							// Turn right
							degree = 360-degree;
							commands.add("C_TR " + degree);
						}
						else{
							// Turn left
							commands.add("C_TL " + degree);
						}
						
						state = "DELIVERBALLS";
	
					}
					break;
					
				case "DELIVERBALLS": 
					// Make 2 engine go the opposite direction to spit out all the balls
					// start engine 2 again when done
					break;
				default:
					System.out.println("Something went wrong in deliver");
					break;
			}
			/* DELIVER should have 2 states, and should be in this state when the BallCatchCounter is at its limit:
			 * State 1: driver over to the front of the goal coordinate (like +-50 to x-axis)
			 * State 2: only change the direction so the vectors is parallel (where courseV is directly to the goal,
			 * 	and then VOMIT ALL OVER THAT GOAL!! */
			//Left goal is little goal
			break;
		case "FINNISHED":
			/* FINNISHED
			 * When there is no more balls let on the fields it should go to this state.
			 * 
			 * Remember to release cam when finished.
			 * */
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
	private double degree (Vector2D l, Vector2D j) {
		double degree = 0;
		
		double dotProdukt = Vector2D.dot(l, j);
		
		double kvadratrodL = Math.sqrt((l.getX() * l.getX()) + (l.getY() * l.getY()));
		
		double kvadratrodJ = Math.sqrt((j.getX() * j.getX()) + (j.getY() * j.getY()));

		degree = Math.acos(dotProdukt/(kvadratrodL*kvadratrodJ));
		degree = degree*180 / Math.PI;
		return degree;
	}
	
	private boolean turnRight(double degree){
		boolean turnR = false;
		
		if(degree>180){
			turnR=true;
		}
		
		return turnR;
	}
	
	
	private boolean checkInterSection(Point2D[] obst, Point2D startL, Point2D endL) {
		Rectangle2D r = new Rectangle2D.Double(obst[0].getX(), obst[0].getY(), (obst[1].getX()-obst[0].getX()), (obst[0].getY()-obst[1].getY()));
		java.awt.geom.Line2D l = new java.awt.geom.Line2D.Double(
				new java.awt.geom.Point2D.Double(startL.getX(), startL.getY()), 
				new java.awt.geom.Point2D.Double(endL.getX(), endL.getY()));
		
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
