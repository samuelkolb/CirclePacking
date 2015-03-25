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
}
