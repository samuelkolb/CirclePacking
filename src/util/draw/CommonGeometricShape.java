package util.draw;

import util.Operation;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Optional;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public abstract class CommonGeometricShape implements GeometricShape {

	private Optional<Color> colorOptional;

	/**
	 * Returns whether this shape has a color, if not it will be drawn in the current graphics context color
	 * @return	True iff this shape has a specified color
	 */
	public boolean hasColor() {
		return this.colorOptional.isPresent();
	}

	/**
	 * Returns the color of this shape
	 * @return	The color of this shape, if this shape has no color an exception will be thrown
	 */
	public Color getColor() {
		return this.colorOptional.get();
	}

	private boolean filled;

	public boolean isFilled() {
		return filled;
	}

	private final Point2D size;

	protected CommonGeometricShape(Point2D size, boolean filled, Optional<Color> colorOptional) {
		this.size = size;
		this.filled = filled;
		this.colorOptional = colorOptional;
	}

	/**
	 * Returns a clone of this object with the given color and filled value
	 * @param factor		The resize-factor
	 * @param colorOptional	The optional color of the new shape
	 * @param filled		Whether or not the new shape will be filled
	 * @return	A clone of this shape with the specified color and filled value
	 */
	protected abstract GeometricShape clone(double factor, Optional<Color> colorOptional, boolean filled);

	@Override
	public GeometricShape color(Color color) {
		return clone(1.0, Optional.of(color), isFilled());
	}

	@Override
	public GeometricShape outline() {
		return !isFilled() ? this : clone(1.0, this.colorOptional, false);
	}

	@Override
	public GeometricShape fill() {
		return isFilled() ? this : clone(1.0, this.colorOptional, true);
	}

	@Override
	public Point2D getSize() {
		return this.size;
	}

	@Override
	public Shape scale(double factor) {
		return clone(factor, this.colorOptional, isFilled());
	}
}
