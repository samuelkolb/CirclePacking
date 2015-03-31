package util.draw;

import java.awt.*;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public interface GeometricShape extends Shape {

	/**
	 * Colors this geometric shape
	 * @param color	The color of the new shape
	 * @return	A geometric shape in the new color
	 */
	public GeometricShape color(Color color);

	/**
	 * Returns the shape corresponding to the outline of this shape
	 * @return	A shape of the outline of this shape
	 */
	public GeometricShape outline();

	/**
	 * Returns a shape corresponding to the filled version of this shape
	 * @return	A filled version of this shape
	 */
	public GeometricShape fill();
}
