package imgProcessing;
import java.util.ArrayList;

import math.geom2d.Point2D;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.*;
//import org.opencv


public class ImgCap {
	private VideoCapture webCam;
	public static void main( String[] args )
	{

		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		ImgCap obj = new ImgCap();
		obj.picAnal();
	}
	
	public ImgCap() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		webCam = new VideoCapture();
		webCam.open(0); //0 for jbn, 1 for bækhøj
	}
	
	public ImgInfo picAnal() {
		//Timer start
		long startTime = System.nanoTime();
		
		/*Picture start*/
		Mat image = new Mat();	
		//picture from file//
//		String filePath = "C:\\cdio\\41.bmp";
//		image = Highgui.imread(filePath,1);
		/*picture from cam*/
		if (webCam.isOpened()) {
			webCam.read(image);
		} else {
			webCam = new VideoCapture();
			webCam.open(1); //0 for jbn, 1 for bækhøj
			webCam.read(image);
		}
		/*Picture end*/
		//img2 for testing purposes
		Mat image2 = image.clone();
		
		Mat circles = new Mat();
		Mat grayImg = new Mat(image.rows(),image.cols(),image.type());
		Scalar scalarColorB = new Scalar(0,0,0); // black scalar
		Scalar scalarColorT = new Scalar(0,255,0); //T scalar, initial color = green
		int rows = image.height();
		int cols = image.width();
		int obstacleMinY = rows;
		int obstacleMaxY = 0;
		int obstacleMinX = cols;
		int obstacleMaxX = 0;
		//instantiating arrays for robot positions
		int[] robotFront = {0,0,0};
		int[] robotBack = {0,0,0};
		
//		run through the picture
		for (int y = 0; y <rows; y=y+2) {
			for (int x = 0;x<cols;x=x+2){
				double color[] = {image.get(y,x)[0],image.get(y,x)[1],image.get(y,x)[2]};
				Integer[] tempArr = new Integer[2];
				tempArr[0] = x;
				tempArr[1] = y;
					if (color[0]+100< color[2] && color[2]-50 > color[1]) {	
					if (x>100 && x<540 && y>100 && y<380){
						System.out.println("x: "+x+" y: "+y);
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
					}
				} else	if (color[2]+100 < color[0] && color[1]+100 > color[0]){	
					robotBack[0] +=x;
					robotBack[1] +=y;
					robotBack[2]++;
				} else if (color[0]+100 <color[1] && color[2]+50 > color[1]) {
					robotFront[0] += x;
					robotFront[1] += y;
					robotFront[2]++;
				} 		
			}
		}
		
		//documentation for opencv methods and paramenters - http://docs.opencv.org/modules/imgproc/doc/feature_detection.html
		Imgproc.cvtColor(image, grayImg, Imgproc.COLOR_BGRA2GRAY); 
        //line finding
        Mat lines = new Mat();
		Imgproc.GaussianBlur(grayImg, grayImg, new Size(3,3),0,0);
        Imgproc.Canny(grayImg, lines, 5, 60);
        Mat linesV = new Mat();
        Imgproc.HoughLinesP(lines, linesV, 1, Math.PI/180, 50, 200, 100 );
        Point2D[] p2DCorners = {new Point2D(0,0), new Point2D(640,0), new Point2D(0,480), new Point2D(640,480)};        
        for( int i = 0; i < linesV.cols(); i++ )
        {
        	//Line coordinate sets (frame)
            double[] vec = linesV.get(0, i);
            double x1 = vec[0], 
                   y1 = vec[1],
                   x2 = vec[2],
                   y2 = vec[3];
            if(((x1 < 75 || x1 > 565) && (y1 < 75 || y1 > 405)) || ((x2 < 75 || x2 > 565) && (y2 < 75 || y2 > 405))) {
                Point start = new Point(x1, y1);
                Point end = new Point(x2, y2);
    		    Core.line( image2, start, end, new Scalar(0,0,0),2); //drawing found lines
    		    for(int j = i+1; j < linesV.cols(); j++ ) {
    	            double[] jvec = linesV.get(0, j);
    	            double x3 = jvec[0], 
    	                   y3 = jvec[1],
    	                   x4 = jvec[2],
    	                   y4 = jvec[3];
    	    		double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
    	    		if (d != 0) {
    		    		double xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
    		    		double yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
    		    		if (xi > 0 && xi<640 && yi > 0 && yi< 480) {
    		    			if (xi < 75 && yi < 75) {
    		    				if (xi >=  p2DCorners[0].getX() && yi >= p2DCorners[0].getY()) {
    		    					p2DCorners[0] = new Point2D(xi, yi);
    				    		}
    		    			} else if (xi > 565 && yi < 75) {
    	    					if (xi <=  p2DCorners[1].getX() && yi >= p2DCorners[1].getY()) {
    	    						p2DCorners[1] = new Point2D (xi, yi);
    				    		}
    			    		} else if (xi < 75 && yi > 405) {
    			    			if (xi >=  p2DCorners[2].getX() && yi <= p2DCorners[2].getY()) {
    			    				p2DCorners[2] = new Point2D(xi, yi);
    				    		}
    			    		} else if (xi > 565 && yi > 405) {
    			    			if (xi <=  p2DCorners[3].getX() && yi <= p2DCorners[3].getY()) {
    			    				p2DCorners[3] = new Point2D(xi, yi);
    				    		}
    			    		}
    		    		}
    	    		}
    		    }
            }
        }
		//circles finding (balls)
		ArrayList<Point2D> ballSet = new ArrayList<Point2D>();
		Imgproc.HoughCircles(grayImg, circles, Imgproc.CV_HOUGH_GRADIENT, 1, 15, 10, 20, 3, 10);
		int radius;
        Point pt;
        for (int x = 0; x < circles.cols(); x++) {
        	double vCircle[] = circles.get(0,x);
        	if (vCircle == null) {
        		System.out.println("no circles found");
	            break;
	        }
        	// coordinates of the balls
        	ballSet.add(new Point2D(vCircle[0],vCircle[1]));
        	
    		pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
	        radius = (int)Math.round(vCircle[2]);
	
	        // draw the found circle
	        Core.circle(image, pt, radius, scalarColorB, 2);
	        Core.circle(image, pt, 1, scalarColorT, 0);
        }
		//obstacle in P2D
		Point2D obstacleTopLeft = new Point2D(obstacleMinX,obstacleMinY);
		Point2D obstacleBottomRight = new Point2D(obstacleMaxX,obstacleMaxY);
		Core.line(image, new Point(obstacleMinX,obstacleMinY), new Point(obstacleMinX,obstacleMaxY), scalarColorT,2);
		Core.line(image, new Point(obstacleMinX,obstacleMinY), new Point(obstacleMaxX,obstacleMinY), scalarColorT,2);
		Core.line(image, new Point(obstacleMaxX,obstacleMaxY), new Point(obstacleMinX,obstacleMaxY), scalarColorT,2);
		Core.line(image, new Point(obstacleMaxX,obstacleMaxY), new Point(obstacleMaxX,obstacleMinY), scalarColorT,2);
		
		//robot front and back coordinates in P2D and drawing on the picture in red
		scalarColorT = new Scalar(0,0,255);
		Point2D robotF = new Point2D(robotFront[0]/robotFront[2],robotFront[1]/robotFront[2]);
		Point2D robotB = new Point2D(robotBack[0]/robotBack[2],robotBack[1]/robotBack[2]);
		Core.circle(image, new Point(robotF.getX(),robotF.getY()), 3,  scalarColorT,2);
		Core.circle(image, new Point(robotB.getX(),robotB.getY()), 3,  scalarColorT,2);
		
		//Goals in P2D
		double tempd = (p2DCorners[0].distance(p2DCorners[2])/2)+p2DCorners[0].getY();
		Point2D topLeftGoal = new Point2D(p2DCorners[0].getX(),tempd-12);
		Point2D bottomLeftGoal = new Point2D(p2DCorners[0].getX(),tempd+12);
		tempd = (p2DCorners[1].distance(p2DCorners[3])/2)+p2DCorners[1].getY();
		Point2D topRightGoal = new Point2D(p2DCorners[1].getX(),tempd-25);
		Point2D bottomRightGoal = new Point2D(p2DCorners[3].getX(),tempd+25);
        
		//drawing frame
		Core.line(image, new Point(p2DCorners[0].getX(),p2DCorners[0].getY()), new Point(p2DCorners[1].getX(),p2DCorners[1].getY()), scalarColorB,2);
		Core.line(image, new Point(p2DCorners[0].getX(),p2DCorners[0].getY()), new Point(p2DCorners[2].getX(),p2DCorners[2].getY()), scalarColorB,2);
		Core.line(image, new Point(p2DCorners[3].getX(),p2DCorners[3].getY()), new Point(p2DCorners[1].getX(),p2DCorners[1].getY()), scalarColorB,2);
		Core.line(image, new Point(p2DCorners[3].getX(),p2DCorners[3].getY()), new Point(p2DCorners[2].getX(),p2DCorners[2].getY()), scalarColorB,2);
        
		Highgui.imwrite("images\\scrCap.jpg",image);
		Highgui.imwrite("images\\scrCap2.jpg",image2);
        long endTime = System.nanoTime();
        
		//test prints
		for (Point2D p : p2DCorners) {
        	System.out.println("Corners: "+p);
        }
		System.out.println(obstacleMaxX+" "+obstacleMinX+ " "+obstacleMaxY+ " "+obstacleMinY);
		System.out.println("robotFront:"+robotF+" robotBack:" +robotB);
		System.out.println((endTime-startTime)/1000000);
		Point2D[] goal = {topLeftGoal,bottomLeftGoal,topRightGoal,bottomRightGoal};
		for (Point2D p : goal) {
			System.out.println("goal: "+p);
		}
		
        //TODO releaseCam skal først kaldes ved program slut. 
//		releaseCam();
		//return an info object
		return new ImgInfo(ballSet,new Point2D[]{obstacleTopLeft, obstacleBottomRight},p2DCorners,new Point2D[]{robotF,robotB}, goal);
	} 
	
	public void releaseCam() {
		webCam.release();
	}
}