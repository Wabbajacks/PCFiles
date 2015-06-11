package imgProcessing;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	public ArrayList<Point2D> picAnal() {
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		
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
		
		int[] robotFront = {0,0,0};
		int[] robotBack = {0,0,0};
		ArrayList<Integer[]> bolde = new ArrayList<Integer[]>();
		ArrayList<Integer[]> edgeTop = new ArrayList<Integer[]>();
		ArrayList<Integer[]> edgeBottom = new ArrayList<Integer[]>();
		ArrayList<Integer[]> edgeLeft = new ArrayList<Integer[]>();
		ArrayList<Integer[]> edgeRight = new ArrayList<Integer[]>();

		int obstacleMinY = 480;
		int obstacleMaxY = 0;
		int obstacleMinX = 480;
		int obstacleMaxX = 0;
		for (int y = 0; y <rows; y=y+2) {
			for (int x = 0;x<cols;x=x+2){
				double color[] = {image.get(y,x)[0],image.get(y,x)[1],image.get(y,x)[2]};
				Integer[] tempArr = new Integer[2];
				tempArr[0] = x;
				tempArr[1] = y;
				if (color[0] > boldLowValue[0] && color[1] > boldLowValue[1] && color[2] > boldLowValue[2] && color[0] < boldHighValue[0] && color[1] < boldHighValue[1] && color[2] < boldHighValue[2]) {
					bolde.add(tempArr);
				} else if(color[0] > obstacleLowValue[0] && color[1] > obstacleLowValue[1] && color[2] > obstacleLowValue[2] && color[0] < obstacleHighValue[0] && color[1] < obstacleHighValue[1] && color[2] < obstacleHighValue[2]) {
					System.out.println(x+" "+y);
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
						edgeLeft.add(tempArr);
					} else if (x>540) {
						edgeRight.add(tempArr);
					} if (y<100) {
						edgeTop.add(tempArr);
					} else if (y>380) {
						edgeBottom.add(tempArr);
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
		
		//kvadrant bolde udregninger
//		int q0=0, q1=0, q2=0, q3=0;
//		for(int i=0;i<bolde.size();i++){
//			if (q0 == 0 && bolde.get(i)[0] <= minY[0] && bolde.get(i)[1] <= minX[1]) {
//				q0 = 1;
//			} else if (q1 == 0 && bolde.get(i)[0] <= minY[0] && bolde.get(i)[1] > minX[1]) {
//				q1 = 1;
//			} else if (q2 == 0 && bolde.get(i)[0] > minY[0] && bolde.get(i)[1] > minX[1]) {
//				q2 = 1;
//			} else if (q3 == 0 && bolde.get(i)[0] > minY[0] && bolde.get(i)[1] <= minX[1]) {
//				q3 = 1;
//			}
//		}
		
		//Calculating the coordinates for all balls
		BallGraph ballCalculator = new BallGraph(bolde);
		ArrayList<Point2D> ballSet = ballCalculator.filterBalls();
		for(int i=0;i<ballSet.size();i++){
			System.out.println(ballSet.get(i));
		}
		//obstacle outer coordinates
		Point2D obstacleTopLeft = new Point2D.Double(obstacleMinX,obstacleMinY);
		Point2D obstacleBottomRight = new Point2D.Double(obstacleMaxX,obstacleMaxY);
		
		//robot front and back coordinates
		Point2D robotF = new Point2D.Double(robotFront[0]/robotFront[2],robotFront[1]/robotFront[2]);
		Point2D robotB = new Point2D.Double(robotBack[0]/robotBack[2],robotBack[1]/robotBack[2]);
		
		//Calculation of area edges

		
		//prints for testing
		System.out.println("RobotFront, X: "+robotF.getX()+" Y:"+robotF.getY());
		System.out.println("RobotBack, X: "+robotB.getX()+" Y:"+robotB.getY());
		System.out.println("obstacle: top left:"+obstacleTopLeft+" bottom Right: "+obstacleBottomRight);
		return result;
	}
	
	private int calcEdge(ArrayList<Integer[]> edges) {
		HashMap<Integer, Integer> edgeCoords = new HashMap<Integer,Integer>();
		for(Integer[] coordinateSet : edges) {
			if(edgeCoords.containsKey(coordinateSet[1])) {
				edgeCoords.put(coordinateSet[1], (edgeCoords.get((Integer)coordinateSet[1])+1));
			} else {
				edgeCoords.put(coordinateSet[1],1);
			}
		}
//		for()
		return 0;
	}
}