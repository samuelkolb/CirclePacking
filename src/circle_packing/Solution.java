package circle_packing;

import util.MathUtil;
import util.Operation;

import java.awt.geom.Point2D;
import java.util.Arrays;

import static util.Operation.*;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class Solution {

	//region Variables
	private final Circle[] circles;

	/**
	 * Returns the circle at the specified index
	 * @param index	The index of the circle to return
	 * @return	The circle at the given index
	 */
	public Circle getCircle(int index) {
		return this.circles[index];
	}

	/**
	 * Returns the number of circles in this solution
	 * @return	An integer specifying the amount of circles in this solution
	 */
	public int getCircleCount() {
		return this.circles.length;
	}

	//endregion

	//region Construction

	/**
	 * Creates a new solution
	 * @param radii		The radii of the circles
	 * @param positions	The positions of the circles
	 */
	public Solution(double[] radii, Point2D.Double[] positions) {
		if(radii.length != positions.length)
			throw new IllegalArgumentException();
		this.circles = new Circle[radii.length];
		for(int i = 0; i < radii.length; i++)
			circles[i] = new Circle(radii[i], positions[i]);
	}

	private Solution(Circle[] circles) {
		this.circles = circles;
	}

	//endregion

	//region Public methods

	/**
	 * Returns the positions of the circles in this solution
	 * @return	An array of points
	 */
	public Point2D.Double[] getPositions() {
		Point2D.Double[] array = new Point2D.Double[getCircleCount()];
		for(int i = 0; i < getCircleCount(); i++)
			array[i] = getCircle(i).getPosition();
		return array;
	}

	public Solution pullToCenter(double stepSize, double delta) {
		Solution clone = clone(getPositions());
		Point2D.Double origin = new Point2D.Double(0, 0);
		boolean change = true;
		while(change) {
			change = false;
			for(int i = 0; i < getCircleCount(); i++) {
				Point2D.Double oldPosition = clone.getCircle(i).getPosition();
				Point2D.Double destination = scale(invert(getUnit(oldPosition)), stepSize);
				Point2D.Double position = clone.moveTowards(i, destination);
				clone.circles[i] = new Circle(getCircle(i).getRadius(), position);
				if(MathUtil.distance(oldPosition, position) >= delta)
					change = true;
			}
		}
		return clone;
	}

	public Solution blowUp(double stepSize) {
		Solution clone = clone(getPositions());
		while(clone.overlaps()) {
			double minRadius = clone.minRadius();
			for(int i = 0; i < getCircleCount(); i++) {
				Point2D.Double oldPosition = clone.getCircle(i).getPosition();
				double factor = stepSize + MathUtil.distance(oldPosition) / minRadius * stepSize / 2;
				Point2D.Double position = add(oldPosition, scale(getUnit(oldPosition), factor));
				clone.circles[i] = new Circle(getCircle(i).getRadius(), position);
			}
		}
		return clone;
	}

	/**
	 * Clone this solution with the given positions
	 * @param positions	The positions of the circles
	 * @return	A clone of this current solution with different positions for the circles
	 */
	public Solution clone(Point2D.Double[] positions) {
		if(positions.length != getCircleCount())
			throw new IllegalArgumentException("The number of positions does not correspond to the number of circles");
		Circle[] circles = new Circle[getCircleCount()];
		for(int i = 0; i < circles.length; i++)
			circles[i] = getCircle(i).moveTo(positions[i]);
		return new Solution(circles);
	}

	/**
	 * Returns the furthest point that the circle at the given index can reach by moving towards the given position
	 * @param index		The index of the circle to move
	 * @param position	The position to move towards
	 * @return	The furthest point in the direction of the destination that can be reached without colliding
	 */
	public Point2D.Double moveTowards(int index, Point2D.Double position) {
		Circle circle = getCircle(index);
		Point2D.Double line = subtract(position, circle.getPosition());
		Point2D.Double destination = line;
		for(int i = 0; i < getCircleCount(); i++) {
			if(i == index)
				continue;
			Point2D.Double circleVector = subtract(getCircle(i).getPosition(), circle.getPosition());
			Point2D.Double projection = project(circleVector, line);
			double combinedRadius = getCircle(i).getRadius() + circle.getRadius();
			if(MathUtil.distance(projection, circleVector) < combinedRadius) {
                double x = getCircle(i).getPosition().getX() - projection.getX();
                double y = getCircle(i).getPosition().getY() - projection.getY();
                double l = Math.sqrt(Math.pow(combinedRadius, 2) - Math.pow(x, 2) - Math.pow(y, 2));
                Point2D.Double t = scale(getUnit(projection), MathUtil.distance(projection) - l);
                if(MathUtil.distance(t) < MathUtil.distance(destination))
                    destination = t;
			}
		}
        return add(destination, circle.getPosition());
	}

	@Override
	public String toString() {
		return Arrays.toString(this.circles);
	}

	/**
	 * Calculates the minimum radius to enclose all circles in this solution
	 * @return	The minimal radius enclosing all circles
	 */
	public double minRadius() {
		double minRadius = 0;
		for(int i = 0; i < getCircleCount(); i++) {
			double radius = getCircle(i).getDistanceToOrigin() + getCircle(i).getRadius();
			if(radius > minRadius)
				minRadius = radius;
		}
		return minRadius;
	}

	/**
	 * Calculates whether or not the circles in this solution overlap
	 * @return	True iff one or more circles overlap
	 */
	public boolean overlaps() {
		for(int i = 0; i < getCircleCount(); i++)
			for(int j = i + 1; j < getCircleCount(); j++)
				if(getCircle(i).overlapsWith(getCircle(j)))
					return true;
		return false;
	}

	public double getCoverage() {
		double solutionCoverage = 0;
		for(int i = 0; i < getCircleCount(); i++)
			solutionCoverage += MathUtil.getArea(getCircle(i).getRadius());
		return solutionCoverage;
	}

	//endregion
}
