package imgProcessing;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class ImgCap {
//	public static int boldLowValue, boldHighValue, groundLowValue, groundHighValue, obstacleLowValue, obstacleHighValue, robotBackLowValue, robotBackHighValue;
	
	public static void main( String[] args )
	{

		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		ImgCap obj = new ImgCap();
		obj.picAnal();
	}
	
	public ImgCap() {
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
	}
	
	public ImgInfo picAnal() {
		//Compairison values
		int boldLowValue[] = {180, 190, 190};
		int boldHighValue[] = {252, 252, 252};
		int robotBackHighValue[] = {50, 100, 240};
		int robotBackLowValue[] = {20, 80, 200};
		int robotFrontHighValue[] = {65, 120, 50};
		int robotFrontLowValue[] = {50, 90, 30};
		int obstacleHighValue[] = {55, 65, 145};
		int obstacleLowValue[] = {0, 0, 40};

		//The picture
		Mat image = new Mat();		
		//picture from file
		String filePath = "C:\\cdio\\12.bmp";
		image = Highgui.imread(filePath,1);

		//picture from cam
//		VideoCapture test = new VideoCapture(0);
//		test.read(image);
		
		
		int rows = image.height();
		int cols = image.width();

		//instantiating arrays for robot positions
		int[] robotFront = {0,0,0};
		int[] robotBack = {0,0,0};
		
		//instantiating reference ints for obstacle coordinates
		int obstacleMinY = 480;
		int obstacleMaxY = 0;
		int obstacleMinX = 640;
		int obstacleMaxX = 0;
		
		//instantiating arrayList for balls
		ArrayList<Integer[]> balls = new ArrayList<Integer[]>();
		
		//Hashmaps for frameEdge calculations
		int[] frameEdges = {0,0,0,0};
		HashMap<Integer,Integer> edgeTop = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> edgeBottom = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> edgeLeft = new HashMap<Integer,Integer>();
		HashMap<Integer,Integer> edgeRight = new HashMap<Integer,Integer>();

		//run through the picture
		for (int y = 0; y <rows; y=y+2) {
			for (int x = 0;x<cols;x=x+2){
				double color[] = {image.get(y,x)[0],image.get(y,x)[1],image.get(y,x)[2]};
				Integer[] tempArr = new Integer[2];
				tempArr[0] = x;
				tempArr[1] = y;
				if (color[0] > boldLowValue[0] && color[1] > boldLowValue[1] && color[2] > boldLowValue[2] && color[0] < boldHighValue[0] && color[1] < boldHighValue[1] && color[2] < boldHighValue[2]) {
					balls.add(tempArr);
				} else if(color[0] > obstacleLowValue[0] && color[1] > obstacleLowValue[1] && color[2] > obstacleLowValue[2] && color[0] < obstacleHighValue[0] && color[1] < obstacleHighValue[1] && color[2] < obstacleHighValue[2]) {					
					if (x>100 && x<540 && y>100 && y<380){
						if (x< obstacleMinX) {
							obstacleMinX=x;
						} 
						else if (x > obstacleMaxX) {
							obstacleMaxX = x;
						} 
						else if (y<obstacleMinY) {
							obstacleMinY = y;
							
						} 
						else if (y>obstacleMaxY) {
							obstacleMaxY = y;
						}
					} else if (x<100) {
						if(edgeLeft.containsKey(tempArr[0])) {
							edgeLeft.put(tempArr[0], (edgeLeft.get((Integer)tempArr[0])+1));
						} else {
							edgeLeft.put(tempArr[0],1);
						}
						
					} else if (x>540) {	
						if(edgeRight.containsKey(tempArr[0])) {
							edgeRight.put(tempArr[0], (edgeRight.get((Integer)tempArr[0])+1));
						} else {
							edgeRight.put(tempArr[0],1);
						}
					} if (y<100) {
						if(edgeTop.containsKey(tempArr[1])) {
							edgeTop.put(tempArr[1], (edgeTop.get((Integer)tempArr[1])+1));
						} else {
							edgeTop.put(tempArr[1],1);
						}
					} else if (y>380) {	
						if(edgeBottom.containsKey(tempArr[1])) {
							edgeBottom.put(tempArr[1], (edgeBottom.get((Integer)tempArr[1])+1));
						} else {
							edgeBottom.put(tempArr[1],1);
						}
					}
				} else if (color[0] > robotBackLowValue[0] && color[1] > robotBackLowValue[1] && color[2] > robotBackLowValue[2] && color[0] < robotBackHighValue[0] && color[1] < robotBackHighValue[1] && color[2] < robotBackHighValue[2]) {
					robotBack[0] +=x;
					robotBack[1] +=y;
					robotBack[2]++;
				} else if (color[0] > robotFrontLowValue[0] && color[1] > robotFrontLowValue[1] && color[2] > robotFrontLowValue[2] && color[0] < robotFrontHighValue[0] && color[1] < robotFrontHighValue[1] && color[2] < robotFrontHighValue[2]) {
					robotFront[0] += x;
					robotFront[1] += y;
					robotFront[2]++;
				} 		
			}
		}
		
		//Calculating the coordinates for all balls
		Graph ballGraph = new Graph(balls);
		ArrayList<Point2D> ballSet = ballGraph.filterBalls();

		//obstacle outer coordinates in P2D
		Point2D obstacleTopLeft = new Point2D.Double(obstacleMinX,obstacleMinY);
		Point2D obstacleBottomRight = new Point2D.Double(obstacleMaxX,obstacleMaxY);
		
		//robot front and back coordinates in P2D
		Point2D robotF = new Point2D.Double(robotFront[0]/robotFront[2],robotFront[1]/robotFront[2]);
		Point2D robotB = new Point2D.Double(robotBack[0]/robotBack[2],robotBack[1]/robotBack[2]);
		
		//Calculation of area cornors
		frameEdges[0] = topOrLeftEdge(edgeLeft);
		frameEdges[1] = topOrLeftEdge(edgeTop);
		frameEdges[2] = bottomOrRightEdge(edgeRight);
		frameEdges[3] = bottomOrRightEdge(edgeBottom);
		//converting to P2D
		Point2D edgeTopLeft = new Point2D.Double(frameEdges[0],frameEdges[1]);
		Point2D edgeTopRight = new Point2D.Double(frameEdges[2],frameEdges[1]);
		Point2D edgeBottomLeft = new Point2D.Double(frameEdges[0],frameEdges[3]);
		Point2D edgeBottomRight = new Point2D.Double(frameEdges[2],frameEdges[3]);
		
		//goal coordinates
		//leftGoal is always the small 8cm goal
		int k = 11;
		double leftGoalCenter = (edgeBottomLeft.getY()-edgeTopLeft.getY())/2+edgeTopLeft.getY();
		Point2D leftGoalTop = new Point2D.Double(edgeBottomLeft.getX(),leftGoalCenter-k);
		Point2D leftGoalBottom = new Point2D.Double(edgeBottomLeft.getX(),leftGoalCenter+k);
		
		//rightGoal is always the big 20cm goal
		k = 26;
		double rightGoalCenter = (edgeBottomRight.getY()-edgeTopRight.getY())/2+edgeTopRight.getY();
		Point2D rightGoalTop = new Point2D.Double(edgeBottomRight.getX(),rightGoalCenter-k);
		Point2D rightGoalBottom = new Point2D.Double(edgeBottomRight.getX(),rightGoalCenter+k);
		System.out.println("Left goal:");
		System.out.println(leftGoalTop);
		System.out.println(leftGoalBottom);
		
		System.out.println("Right goal:");
		System.out.println(rightGoalTop);
		System.out.println(rightGoalBottom);
		
		
		//prints for testing
		System.out.println("printing balls");
		for(int i=0;i<ballSet.size();i++){
			System.out.println(ballSet.get(i));
		}
		System.out.println("RobotFront, X: "+robotF.getX()+" Y:"+robotF.getY());
		System.out.println("RobotBack, X: "+robotB.getX()+" Y:"+robotB.getY());
		System.out.println("obstacle: top left:"+obstacleTopLeft+" bottom Right: "+obstacleBottomRight);
		System.out.println("top left: " + edgeTopLeft + " top right: " + edgeTopRight + "bottom left: " + edgeBottomLeft + "bottom right: " + edgeBottomRight);
		
		//return an info object
		return new ImgInfo(ballSet,new Point2D[]{obstacleTopLeft, obstacleBottomRight},new Point2D[]{edgeTopLeft, edgeTopRight, edgeBottomLeft, edgeBottomRight},new Point2D[]{robotF,robotB}, new Point2D[]{leftGoalTop,leftGoalBottom,rightGoalTop,rightGoalBottom});
	}
	
	//Follow methods determinate the x and y coordinates for the frame
	/****************************************************************/
	private int topOrLeftEdge(HashMap<Integer, Integer> edges) {
		int result = 0;
		for(Entry<Integer, Integer> entry: edges.entrySet()) {
			if(entry.getValue() > 100) {
				if(entry.getKey() > result) {
					result = entry.getKey();
				}
			}
		}
		return result;
	}
	
	private int bottomOrRightEdge(HashMap<Integer, Integer> edges) {
		int result = 640;
		for(Entry<Integer, Integer> entry: edges.entrySet()) {
			if(entry.getValue() > 100) {
				if(entry.getKey() < result) {
					result = entry.getKey();
				}
			}
		}
		return result;
	}
	/****************************************************************/
	
	/****************************************************************/
	//Following might be used later
	/****************************************************************/
	//kvadrant bolde udregninger
//	int q0=0, q1=0, q2=0, q3=0;
//	for(int i=0;i<bolde.size();i++){
//		if (q0 == 0 && bolde.get(i)[0] <= minY[0] && bolde.get(i)[1] <= minX[1]) {
//			q0 = 1;
//		} else if (q1 == 0 && bolde.get(i)[0] <= minY[0] && bolde.get(i)[1] > minX[1]) {
//			q1 = 1;
//		} else if (q2 == 0 && bolde.get(i)[0] > minY[0] && bolde.get(i)[1] > minX[1]) {
//			q2 = 1;
//		} else if (q3 == 0 && bolde.get(i)[0] > minY[0] && bolde.get(i)[1] <= minX[1]) {
//			q3 = 1;
//		}
//	}
//	private int locateGoal(HashMap<Integer, Integer> edges) {
//		int minVal = 640;
//		int coordMin = 0;
//		int maxVal = 0;
//		int coordMax = 0;
//		for(Entry<Integer, Integer> entry: edges.entrySet()) {
//			if(entry.getValue() < minVal && entry.getValue() > 60) {
//				minVal = entry.getValue();
//				coordMin = entry.getKey();
//			}
//			if(entry.getValue() > maxVal && entry.getValue() > 100) {
//				maxVal = entry.getValue();
//				coordMax = entry.getKey();
//			}
//		}
//		System.out.println(coordMin + " "+ minVal);
//		System.out.println(coordMax + " " + maxVal);
//		return  minVal;
//	}
}