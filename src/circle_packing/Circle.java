package circle_packing;

import util.MathUtil;

import java.awt.geom.Point2D;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class Circle {

	//region Variables
	private final double radius;

	public double getRadius() {
		return radius;
	}

	private final Point2D.Double position;

	public Point2D.Double getPosition() {
		return position;
	}

	//endregion

	//region Construction

	/**
	 * Creates a new circle with the given radius and position
	 * @param radius	The radius of the circle
	 * @param position	The position of the circle
	 */
	public Circle(double radius, Point2D.Double position) {
		if(radius < 0)
			throw new IllegalArgumentException("Radius has to be positive");
		this.radius = radius;
		this.position = position;
	}

	//endregion

	//region Public methods

	/**
	 * Returns a new circle that has been moved to the given position
	 * @param position	The new position
	 * @return	A circle with the same radius as this circle and the new position
	 */
	public Circle moveTo(Point2D.Double position) {
		return new Circle(getRadius(), position);
	}

	/**
	 * Returns the distance of this circles center to the origin
	 * @return	The distance
	 */
	public double getDistanceToOrigin() {
		return MathUtil.distance(getPosition());
	}

	/**
	 * Returns the distance of this circles center to the center of the given circle
	 * @param circle	The circle to measure the distance to
	 * @return	The distance
	 */
	public double getDistanceTo(Circle circle) {
		return MathUtil.distance(getPosition(), circle.getPosition());
	}

	/**
	 * Calculates whether this circle overlaps with the given circle
	 * @param circle	The circle to calculate overlap for
	 * @return	True iff this circle overlaps with the given circle
	 */
	public boolean overlapsWith(Circle circle) {
		return getDistanceTo(circle) < getRadius() + circle.getRadius();
	}

	@Override
	public String toString() {
		return "Circle at (" + getPosition().x + ", " + getPosition().y + ") with radius " + getRadius();
	}

	//endregion
}
