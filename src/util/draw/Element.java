package util.draw;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public interface Element {

	public Point2D getPosition();

	public Point2D getSize();

	/**
	 * Scales the element by the given factor
	 * @param factor	The factor to use for scaling
	 * @return	A scaled version of this element
	 */
	public Element scale(double factor);

	/**
	 * Moves the element to the new position
	 * @param newPosition	The position to move to
	 * @return	A moved version of this element
	 */
	public Element moveTo(Point2D newPosition);

	/**
	 * Draws the element onto the given graphics context
	 * @param graphics2D	The 2D graphics context to draw on
	 */
	public void draw(Graphics2D graphics2D);
}
