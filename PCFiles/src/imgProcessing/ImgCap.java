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
	
	@SuppressWarnings({ "deprecation", "static-access" })
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
		
		//Instantiating matrix
		Mat shapes = new Mat();
		Mat grayImg = new Mat(image.rows(),image.cols(),image.type());
		Scalar scalarColorB = new Scalar(0,0,0); // black scalar
		Scalar scalarColorT = new Scalar(0,255,0); //T scalar, initial color = green
		
		//Instantiating rows and cols
		int rows = image.height();
		int cols = image.width();
		
		//Instantiating Min values and Max values for obstacle
		int obstacleMinY = rows;
		int obstacleMaxY = 0;
		int obstacleMinX = cols;
		int obstacleMaxX = 0;
		
		//instantiating arrays for robot color positions
		int[] robotFront = {0,0,0};
		int[] robotBack = {0,0,0};	
		
		//Instantiating Point and Point2D to set new coordinates later
        Point pt = new Point();
        Point2D pt2D = new Point2D();
        
		//instantiating shape variables
        //lines
        double d;
        double xi;
		double yi;
		double x1, y1, x2, y2, x3, y3, x4, y4;
		double[] vec = new double[4];
		double[] jvec = new double[4];
		double[] vCircle = new double[3];
		//circles
        int radius;
		ArrayList<Point2D> ballSet = new ArrayList<Point2D>();
        
		//Instantiating frame variables
        Point2D[] goal = new Point2D[2];
        Point2D[] p2DCorners = {new Point2D(0,0), new Point2D(cols,0), new Point2D(0,rows), new Point2D(cols,rows)};        

        /***************************************************************/
        /******************Run through the picture**********************/
		for (int y = 0; y <rows; y=y+3) {
			for (int x = 0;x<cols;x=x+4){
				double color[] = {image.get(y,x)[0],image.get(y,x)[1],image.get(y,x)[2]};
					if (color[0]+100< color[2] && color[2]-50 > color[1]) {	
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
		/***************************************************************/
		
		// Documentation for opencv methods and paramenters - http://docs.opencv.org/modules/imgproc/doc/feature_detection.html
		// Picture processing
		Imgproc.cvtColor(image, grayImg, Imgproc.COLOR_BGRA2GRAY); 
		Imgproc.GaussianBlur(grayImg, grayImg, new Size(3,3),0,0);
        Imgproc.Canny(grayImg, shapes, 5, 60);

        /***************************************************************/
		/*************************Line finding**************************/
        Imgproc.HoughLinesP(shapes, shapes, 1, Math.PI/180, 50, 200, 100 );
        for( int i = 0; i < shapes.cols(); i++ )
        {
        	//Line coordinate sets (frame)
        	vec = shapes.get(0, i);
        	x1 = vec[0]; 
        	y1 = vec[1];
			x2 = vec[2];
			y2 = vec[3];
            if(((x1 < 75 || x1 > 565) && (y1 < 75 || y1 > 405)) || ((x2 < 75 || x2 > 565) && (y2 < 75 || y2 > 405))) {
    		    for(int j = i+1; j < shapes.cols(); j++ ) {
    	            jvec = shapes.get(0, j);
    	            x3 = jvec[0]; 
	            	y3 = jvec[1];
	            	x4 = jvec[2];
	            	y4 = jvec[3];
    	    		d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
    	    		if (d != 0) {
    		    		xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
    		    		yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;
    		    		if (xi > 0 && xi<640 && yi > 0 && yi< 480) {
    		    			if (xi < 75 && yi < 75) {
    		    				if (xi >=  p2DCorners[0].getX() && yi >= p2DCorners[0].getY()) {
    		    					p2DCorners[0] = pt2D.create(xi, yi);
    				    		}
    		    			} else if (xi > 565 && yi < 75) {
    	    					if (xi <=  p2DCorners[1].getX() && yi >= p2DCorners[1].getY()) {
    	    						p2DCorners[1] = pt2D.create(xi, yi);
    				    		}
    			    		} else if (xi < 75 && yi > 405) {
    			    			if (xi >=  p2DCorners[2].getX() && yi <= p2DCorners[2].getY()) {
    			    				p2DCorners[2] = pt2D.create(xi, yi);
    				    		}
    			    		} else if (xi > 565 && yi > 405) {
    			    			if (xi <=  p2DCorners[3].getX() && yi <= p2DCorners[3].getY()) {
    			    				p2DCorners[3] = pt2D.create(xi, yi);
    				    		}
    			    		}
    		    		}
    	    		}
    		    }
            }
        }
        /***************************************************************/
        
        //reset shapes
        shapes = new Mat();

        /***************************************************************/
        /***************circles finding (balls)*************************/
		Imgproc.HoughCircles(grayImg, shapes, Imgproc.CV_HOUGH_GRADIENT, 1, 15, 10, 20, 3, 10);
        for (int x = 0; x < shapes.cols(); x++) {
        	vCircle = shapes.get(0,x);
        	if (vCircle == null) {
        		//TODO return so that we know that there are no more balls
        		System.err.println("no circles found");
	            break;
	        }
        	// coordinates of the balls
        	ballSet.add(pt2D.create(vCircle[0], vCircle[1]));
    		pt.set(vCircle);
	        radius = (int)Math.round(vCircle[2]);
	
	        // draw the found circle
	        Core.circle(image, pt, radius, scalarColorB, 2);
	        Core.circle(image, pt, 1, scalarColorT, 0);
        }
        /***************************************************************/
        
        /***************************************************************/
        /**************Finding picture in P2D and drawing***************/
		//obstacle in P2D
		Point2D obstacleTopLeft = pt2D.create(obstacleMinX,obstacleMinY);
		Point2D obstacleBottomRight = pt2D.create(obstacleMaxX,obstacleMaxY);
		// Drawing obstacle
		Core.line(image, new Point(obstacleMinX,obstacleMinY), new Point(obstacleMinX,obstacleMaxY), scalarColorT,2);
		Core.line(image, new Point(obstacleMinX,obstacleMinY), new Point(obstacleMaxX,obstacleMinY), scalarColorT,2);
		Core.line(image, new Point(obstacleMaxX,obstacleMaxY), new Point(obstacleMinX,obstacleMaxY), scalarColorT,2);
		Core.line(image, new Point(obstacleMaxX,obstacleMaxY), new Point(obstacleMaxX,obstacleMinY), scalarColorT,2);
		
		// Goals in P2D
		double tempd = (p2DCorners[0].distance(p2DCorners[2])/2)+p2DCorners[0].getY();
		goal[0] = pt2D.create(p2DCorners[0].getX(), tempd);
		tempd = (p2DCorners[1].distance(p2DCorners[3])/2)+p2DCorners[1].getY();
		goal[1] = pt2D.create(p2DCorners[1].getX(), tempd);
		// Drawing goal points
		Core.circle(image, new Point(goal[0].getX(), goal[0].getY()), 3, scalarColorT,2);
		Core.circle(image, new Point(goal[1].getX(), goal[1].getY()), 3, scalarColorT,2);
		
		// Robot front and back coordinates in P2D
		scalarColorT = new Scalar(0,0,255);
		Point2D robotF = pt2D.create(robotFront[0]/robotFront[2],robotFront[1]/robotFront[2]);
		Point2D robotB = pt2D.create(robotBack[0]/robotBack[2],robotBack[1]/robotBack[2]);
		// Drawing front and back of robot
		Core.circle(image, new Point(robotF.getX(),robotF.getY()), 3,  scalarColorT,2);
		Core.circle(image, new Point(robotB.getX(),robotB.getY()), 3,  scalarColorT,2);
	
		//drawing frame
		Core.line(image, new Point(p2DCorners[0].getX(),p2DCorners[0].getY()), new Point(p2DCorners[1].getX(),p2DCorners[1].getY()), scalarColorB,2);
		Core.line(image, new Point(p2DCorners[0].getX(),p2DCorners[0].getY()), new Point(p2DCorners[2].getX(),p2DCorners[2].getY()), scalarColorB,2);
		Core.line(image, new Point(p2DCorners[3].getX(),p2DCorners[3].getY()), new Point(p2DCorners[1].getX(),p2DCorners[1].getY()), scalarColorB,2);
		Core.line(image, new Point(p2DCorners[3].getX(),p2DCorners[3].getY()), new Point(p2DCorners[2].getX(),p2DCorners[2].getY()), scalarColorB,2);
        /***************************************************************/
		
		Highgui.imwrite("images\\scrCap.jpg",image);
        long endTime = System.nanoTime();
//		System.out.println((endTime-startTime)/1000000);

		//TODO releaseCam skal først kaldes ved program slut. 
//		releaseCam();
		
		//return an info object
		return new ImgInfo(ballSet,new Point2D[]{obstacleTopLeft, obstacleBottomRight},p2DCorners,new Point2D[]{robotF,robotB}, goal);
	} 
	
	public void releaseCam() {
		webCam.release();
	}
}