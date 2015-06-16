package imgProcessing;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class ImgInfo {
	private Point2D[] balls;
	private Point2D[] obstacle;
	private Point2D[] frame;
	private Point2D[] robot;
	private Point2D[] goals;
	
	public ImgInfo(ArrayList<Point2D> balls, Point2D[] obstacle, Point2D[] frame, Point2D[] robot, Point2D[] goals) {
		this.obstacle = obstacle;
		this.frame = frame;
		this.robot = robot;
		this.goals = goals;
		this.balls = new Point2D[balls.size()];
		balls.toArray(this.balls);
	}

	public Point2D[] getBalls() {
		return balls;
	}

	public Point2D[] getObstacle() {
		return obstacle;
	}

	public Point2D[] getFrame() {
		return frame;
	}

	public Point2D[] getRobot() {
		return robot;
	}
	
	public Point2D[] getGoals() {
		return goals;
	}
	
}
