package util;

import java.awt.geom.Point2D;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public interface Operation {

	/**
	 * Inverts the given point (vector)
	 * @param point The point to invert
	 * @return  return.x == -point.x && return.y == -point.y
	 */
	public static Point2D.Double invert(Point2D.Double point) {
		return new Point2D.Double(-point.x, -point.y);
	}

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
     * Add a point to another point
     * @param from	The point to add to
     * @param point	The point to add
     * @return	return == from + point
     */
    public static Point2D.Double add(Point2D.Double from, Point2D.Double point) {
        return new Point2D.Double(from.x + point.x, from.y + point.y);
    }

	/**
	 * Project the given point onto the given line
	 * @param point	The point to project
	 * @param line	The line to project on to
	 * @return	The projection of the given point on the line
	 */
	public static Point2D.Double project(Point2D.Double point, Point2D.Double line) {
        Point2D.Double unitLine = getUnit(line);
        return scale(unitLine, MathUtil.dotProduct(point, unitLine));
	}

    /**
     * Get the unit vector of a line
     * @param line  The line of which the unit vector has to be computed
     * @return  The unit vector
     */
    public static Point2D.Double getUnit(Point2D.Double line) {
        return scale(line, 1 / MathUtil.distance(line));
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
