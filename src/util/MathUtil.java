package util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.Operation.*;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class MathUtil {


	public static double getArea(double radius) {
		return radius * radius * Math.PI;
	}

	public static double distance(Point2D point) {
		return Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
	}

	public static double distance(Point2D point1, Point2D point2) {
		return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2) + Math.pow(point1.getY() - point2.getY(), 2));
	}

	public static double dotProduct(Point2D point1, Point2D point2) {
		return point1.getX() * point2.getX() + point1.getY() * point2.getY();
	}

	public static List<Point2D> intersections(Point2D position1, double radius1, Point2D position2, double radius2) {
		double d = distance(position1, position2);
		if(d > radius1 + radius2 || d < Math.abs(radius1 - radius2) || (d == 0 && radius1 == radius2))
			return Arrays.asList();
		if(d == radius1 + radius2)
			return Arrays.asList(add(position2, scale(getUnit(subtract(position1, position2)), radius2)));
		List<Point2D> list = new ArrayList<>();
		double a = (radius1 * radius1 - radius2 * radius2 + d * d) / 2 / d;
		double h = Math.sqrt(radius1 * radius1 - a * a);
		Point2D center = add(position1, scale(subtract(position2, position1), a / d));
		double x = h * (position2.getY() - position1.getY()) / d;
		double y = h * (position2.getX() - position1.getX()) / d;
		list.add(new Point2D.Double(center.getX() + x, center.getY() - y));
		list.add(new Point2D.Double(center.getX() - x, center.getY() + y));
		return list;
	}
}
