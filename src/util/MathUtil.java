package util;

import java.awt.geom.Point2D;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class MathUtil {


	public static double getArea(double radius) {
		return radius * radius * Math.PI;
	}

	public static double distance(Point2D.Double point) {
		return Math.sqrt(point.x * point.x + point.y * point.y);
	}

	public static double distance(Point2D.Double point1, Point2D.Double point2) {
		return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
	}

	public static double dotProduct(Point2D.Double point1, Point2D.Double point2) {
		return point1.x * point2.x + point1.y * point2.y;
	}
}
