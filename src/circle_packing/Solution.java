package circle_packing;

import util.MathUtil;
import util.Operation;

import java.awt.geom.Point2D;
import java.util.Arrays;

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
		Point2D.Double line = Operation.subtract(position, circle.getPosition());
		Point2D.Double destination = position;
		for(int i = 0; i < getCircleCount(); i++) {
			if(i == index)
				continue;
			Point2D.Double circleVector = Operation.subtract(getCircle(i).getPosition(), circle.getPosition());
			Point2D.Double projection = Operation.project(circleVector, line);
			double combinedRadius = getCircle(i).getRadius() + circle.getRadius();
			if(MathUtil.distance(projection, circleVector) < combinedRadius) {

			}
		}
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
