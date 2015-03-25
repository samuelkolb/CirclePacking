package util;

import java.awt.geom.Point2D;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public interface Operation {

	/**
	 * Subtract a point from another point
	 * @param from	The point to subtract from
	 * @param point	The point to subtract
	 * @return	return == from - point
	 */
	public static Point2D.Double subtract(Point2D.Double from, Point2D.Double point) {
		return new Point2D.Double(from.x - point.x, from.y - point.y);
	}

	/**
	 * Project the given point onto the given line
	 * @param point	The point to project
	 * @param line	The line to project on to
	 * @return	The projection of the given point on the line
	 */
	public static Point2D.Double project(Point2D.Double point, Point2D.Double line) {
		Point2D.Double unitLine = scale(line, 1 / MathUtil.distance(line));
		return scale(unitLine, MathUtil.dotProduct(point, unitLine));
	}

	/**
	 * Scales the given point with the given factor
	 * @param point		The point to scale
	 * @param factor	The factor to scale with
	 * @return	The scaled point
	 */
	public static Point2D.Double scale(Point2D.Double point, double factor) {
		return new Point2D.Double(point.x * factor, point.y * factor);
	}
}
