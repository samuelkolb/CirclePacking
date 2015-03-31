package util.draw;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public interface Shape {

	public Point2D getSize();

	/**
	 * Returns a scaled version of this shape
	 * @param factor	The scaling factor
	 * @return	A shape that has been scaled with the given factor
	 */
	public Shape scale(double factor);

	/**
	 * Draws this shape on the given graphics object
	 * @param graphics2D	The graphics object to draw on
	 * @param center		The position to draw at (the center of the shape)
	 */
	public void draw(Graphics2D graphics2D, Point center);

	/**
	 * Returns whether this shape includes the given point (relative to the center of the shape)
	 * @param point	The point
	 * @return	True iff the given point lies within this shape
	 */
	public boolean includes(Point point);

	/**
	 * Returns an element with this shape at the given position
	 * @param point2D	The position of the element (the center of the element)
	 * @return	The resulting element object
	 */
	public default Element getElement(Point2D point2D) {
		return new ShapeElement(point2D, this);
	}
}
