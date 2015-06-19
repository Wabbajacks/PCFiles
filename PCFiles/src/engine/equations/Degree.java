package engine.equations;

import math.geom2d.Vector2D;

public class Degree {
	public static double degree (Vector2D l, Vector2D j) {
		double cos = cos(l, j);
		double sin = sin(l, j);
		if (cos == 0) {
			if (sin > 0) {
				return 90;
			} else {
				return 270;
			}
		}
		if (sin == 0) {
			if (cos > 0) {
				return 0;
			} else {
				return 180; // -PI/2   ...   +PI/2
			}
		}
		double degree = 180 * Math.atan(sin / cos) / Math.PI;

		if (cos < 0) {
			degree = degree + 180;
		}
		if (degree < 0) {
			degree = degree + 360;
		}
		return degree;
		
		/*double degree = 0;
		
		int dotProdukt = (int) Vector2D.dot(l, j);
		
		int kvadratrodL = (int) Math.sqrt((l.getX() * l.getX()) + (l.getY() * l.getY()));
		
		int kvadratrodJ = (int) Math.sqrt((j.getX() * j.getX()) + (j.getY() * j.getY()));
		
//		System.out.println(dotProdukt/kvadratrodJ+kvadratrodL);
		
		degree =  Math.acos(dotProdukt/(kvadratrodL+kvadratrodJ));
		System.out.println(degree);
		return 0;*/
//		return degree;
	}
	
	private static double cos(Vector2D u, Vector2D v) {
		double length = Math.sqrt((u.getX() * u.getX() + u.getY() * u.getY())
				* (v.getX() * v.getX() + v.getY() * v.getY()));
		if (length > 0) {
			return Vector2D.dot(u, v) / length;
		} else {
			return 0;
		}
	}
	
	private static double sin(Vector2D u, Vector2D v) {
		double length = Math.sqrt((u.getX() * u.getX() + u.getY() * u.getY())
				* (v.getX() * v.getX() + v.getY() * v.getY()));
		if (length > 0) {
			return Vector2D.dot(u, v) / length;
		} else {
			return 0;
		}
	}

}
