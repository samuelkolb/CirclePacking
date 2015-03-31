package util.draw;

import util.Operation;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public class ShapeElement implements Element {

	private final Point2D position;

	@Override
	public Point2D getPosition() {
		return position;
	}

	private final Shape shape;

	public Shape getShape() {
		return shape;
	}

	/**
	 * Creates an element at the given position with the given shape
	 * @param position	The position of the element
	 * @param shape		The shape of the element
	 */
	public ShapeElement(Point2D position, Shape shape) {
		this.position = position;
		this.shape = shape;
	}

	@Override
	public Point2D getSize() {
		return getShape().getSize();
	}

	/**
	 * Scales the element by the given factor
	 * @param factor	The factor to use for scaling
	 * @return	A scaled version of this element
	 */
	@Override
	public ShapeElement scale(double factor) {
		return new ShapeElement(Operation.scale(this.position, factor), shape.scale(factor));
	}

	/**
	 * Moves the element to the new position
	 * @param newPosition	The position to move to
	 * @return	A moved version of this element
	 */
	@Override
	public ShapeElement moveTo(Point2D newPosition) {
		return new ShapeElement(newPosition, this.shape);
	}

	@Override
	public void draw(Graphics2D graphics2D) {
		getShape().draw(graphics2D, new Point((int) getPosition().getX(), (int) getPosition().getY()));
	}
}
