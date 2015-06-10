package imgProcessing;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class ImgCap {
//	public static int boldLowValue, boldHighValue, groundLowValue, groundHighValue, obstacleLowValue, obstacleHighValue, robotBackLowValue, robotBackHighValue;
	
	public static void main( String[] args )
	{
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		int boldLowValue[] = {180, 190, 190};
		int boldHighValue[] = {252, 252, 252};
		int groundLowValue[] = {90, 90, 90};
		int groundHighValue[] = {90, 90, 90};
		int robotBackHighValue[] = {90, 90, 90};
		int robotBackLowValue[] = {90, 90, 90};
		int robotFrontHighValue[] = {65, 120, 50};
		int robotFrontLowValue[] = {50, 90, 30};
		int obstacleHighValue[] = {55, 65, 145};
		int obstacleLowValue[] = {25, 50, 125};
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
//		System.out.println(cols+" "+rows);
		ArrayList<Integer[]> bolde = new ArrayList<Integer[]>();
//		ArrayList<Integer[]> obstacle = new ArrayList<Integer[]>();

		int minY = 480;
		int maxY = 0;
		int minX = 480;
		int maxX = 0;
//		System.out.println(image.dump());
		for (int i = 0; i <rows; i=i+2) {
			for (int j = 0;j<cols;j=j+2){
				double color[] = {image.get(i,j)[0],image.get(i,j)[1],image.get(i,j)[2]};
				Integer[] tempArr = new Integer[2];
				tempArr[0] = j;
				tempArr[1] = i;
				if (color[0] > groundLowValue[0] && color[1] > groundLowValue[1] && color[2] > groundLowValue[2] && color[0] < groundHighValue[0] && color[1] < groundHighValue[1] && color[2] < groundHighValue[2]) {
					
				} else if (color[0] > boldLowValue[0] && color[1] > boldLowValue[1] && color[2] > boldLowValue[2] && color[0] < boldHighValue[0] && color[1] < boldHighValue[1] && color[2] < boldHighValue[2]) {
					bolde.add(tempArr);
				} else if(color[0] > obstacleLowValue[0] && color[1] > obstacleLowValue[1] && color[2] > obstacleLowValue[2] && color[0] < obstacleHighValue[0] && color[1] < obstacleHighValue[1] && color[2] < obstacleHighValue[2]) {
					if (j>100 && j<540 && i>100 && i<380){
						if (j< minX) {
							minX=j;
						} 
						else if (j > maxX) {
							maxX = j;
						} 
						else if (i<minY) {
							minY = i;
						} 
						else if (i>maxY) {
							maxY = i;
						}
					}
				} else if (color[0] > robotBackLowValue[0] && color[1] > robotBackLowValue[1] && color[2] > robotBackLowValue[2] && color[0] < robotBackHighValue[0] && color[1] < robotBackHighValue[1] && color[2] < robotBackHighValue[2]) {
				
				} else if (color[0] > robotFrontLowValue[0] && color[1] > robotFrontLowValue[1] && color[2] > robotFrontLowValue[2] && color[0] < robotFrontHighValue[0] && color[1] < robotFrontHighValue[1] && color[2] < robotFrontHighValue[2]) {
					robotFront[0] += j;
					robotFront[1] += i;
					robotFront[2]++;
				} 		
			}
		}
		System.out.println(minX+","+minY+" "+maxX+","+maxY);
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
		
//		for (int i = 0;i<bolde.size();i++) {
//			System.out.println(bolde.get(i)[0]+","+bolde.get(i)[1]);
//		}
		
//		System.out.print(q0+" "+q1+" "+q2+" "+q3);
		Graph hej = new Graph(bolde);
		ArrayList<String> returnvalue = hej.filterBalls();
		for(int i=0;i<returnvalue.size();i++){
			System.out.println(returnvalue.get(i));
		}
		System.out.println("RobotFront, X: "+robotFront[0]/robotFront[2]+" Y: "+robotFront[1]/robotFront[2]);
	}
	
	
}