package util.draw.shape;

import util.draw.CommonGeometricShape;
import util.draw.GeometricShape;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Optional;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public class Circle extends CommonGeometricShape {

	private final double radius;

	public double getRadius() {
		return radius;
	}

	/**
	 * Creates a new colorless circle outline with the given radius
	 * @param radius	The radius of the circle
	 */
	public Circle(double radius) {
		this(radius, false, Optional.<Color>empty());
	}

	private Circle(double radius, boolean filled, Optional<Color> colorOptional) {
		super(new Point2D.Double(radius * 2, radius * 2), filled, colorOptional);
		this.radius = radius;
	}

	@Override
	protected GeometricShape clone(double factor, Optional<Color> colorOptional, boolean filled) {
		return new Circle(getRadius() * factor, filled, colorOptional);
	}

	@Override
	public void draw(Graphics2D graphics2D, Point center) {
		if(hasColor())
			graphics2D.setColor(getColor());
		int x = (int) (center.getX() - getRadius()), y = (int) (center.getY() - getRadius());
		int size = (int) (getRadius() * 2);
		if(isFilled())
			graphics2D.fillOval(x, y, size, size);
		else
			graphics2D.drawOval(x, y, size, size);
	}

	@Override
	public boolean includes(Point point) {
		double x = point.getX() / getRadius(), y = point.getY() / getRadius();
		return x * x + y * y <= 1;
	}
}
