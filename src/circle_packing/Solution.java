package circle_packing;

import util.MathUtil;
import util.Operation;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
	public Solution(double[] radii, Point2D[] positions) {
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
	public Point2D[] getPositions() {
		Point2D[] array = new Point2D[getCircleCount()];
		for(int i = 0; i < getCircleCount(); i++)
			array[i] = getCircle(i).getPosition();
		return array;
	}

	/**
	 * Creates a new solution in which circles are pulled towards the center in (small) steps
	 * @param stepSize	The step size in which circles move towards the origin
	 * @param delta		The delta to use to determine if the circle positions have converged
	 * @param maxSteps	The maximum number of movement steps to set
	 * @return	A clone of this solution in which circles have been moved towards the origin
	 */
	public Solution pullToCenter(double stepSize, double delta, int maxSteps) {
		Solution clone = clone(getPositions());
		boolean change = true;
		while(change && maxSteps > 0) {
			change = false;
			for(int i = 0; i < getCircleCount(); i++) {
				Point2D oldPosition = clone.getCircle(i).getPosition();
				Point2D destination = MathUtil.distance(oldPosition) <= stepSize
					? new Point2D.Double()
					: scale(invert(getUnit(oldPosition)), stepSize);
				Point2D.Double position = clone.moveTowards(i, destination);
				clone.circles[i] = new Circle(getCircle(i).getRadius(), position);
				if(MathUtil.distance(oldPosition, position) >= delta)
					change = true;
			}
			maxSteps--;
		}
		return clone;
	}

	/**
	 * Blows up the circles by moving them away from center until no circle overlaps with another circle
	 * @param stepSize	The step size in which circles move circles away
	 * @return	A clone of this solution in which circles have been moved away from the origin and do not overlap
	 */
	public Solution blowUp(double stepSize) {
		Solution clone = clone(getPositions());
		while(clone.overlaps()) {
			double minRadius = clone.minRadius();
			for(int i = 0; i < getCircleCount(); i++) {
				Point2D oldPosition = clone.getCircle(i).getPosition();
				double factor = stepSize + MathUtil.distance(oldPosition) / minRadius * stepSize / 2;
				Point2D.Double position = add(oldPosition, scale(getUnit(oldPosition), factor));
				clone.circles[i] = new Circle(getCircle(i).getRadius(), position);
			}
		}
		return clone;
	}

	/**
	 * Clone this solution with a new position for one circle
	 * @param index			The index of the circle with a new position
	 * @param newPosition	The new position of the circle
	 * @return	A clone of this current solution with the different position for the circle
	 */
	public Solution clone(int index, Point2D newPosition) {
		Point2D[] positions = getPositions();
		positions[index] = newPosition;
		return clone(positions);
	}

	/**
	 * Clone this solution with the given positions
	 * @param positions	The positions of the circles
	 * @return	A clone of this current solution with different positions for the circles
	 */
	public Solution clone(Point2D[] positions) {
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
	public Point2D.Double moveTowards(int index, Point2D position) {
		Circle circle = getCircle(index);
		Point2D destination = subtract(position, circle.getPosition());
		for(int i = 0; i < getCircleCount(); i++)
			if(i != index)
				destination = updateDestination(circle, getCircle(i), destination);
        return add(destination, circle.getPosition());
	}

	private Point2D updateDestination(Circle circle, Circle other, Point2D destination) {
		Point2D.Double circleVector = subtract(other.getPosition(), circle.getPosition());
		Point2D.Double projection = project(circleVector, destination);
		double combinedRadius = other.getRadius() + circle.getRadius();
		double distance = MathUtil.distance(projection, circleVector);
		if(distance < combinedRadius
				&& MathUtil.distance(circleVector) < combinedRadius + MathUtil.distance(destination)) {
			double l = Math.sqrt(combinedRadius * combinedRadius - distance * distance);
			Point2D.Double newDestination = scale(getUnit(projection), MathUtil.distance(projection) - l);
			if(MathUtil.distance(newDestination) < MathUtil.distance(destination))
				return newDestination;
		}
		return destination;
	}

	/**
	 * Returns the farthest point on the line towards the given position that can be occupied by the given circle
	 * @param index		The index of the circle to move
	 * @param position	The position to jump towards
	 * @return	The farthest point that can be reached
	 */
	public Point2D jumpTowards(int index, Point2D position) {
		Circle circle = getCircle(index);
		Point2D.Double destination = subtract(position, circle.getPosition());
		List<Circle> circles = new LinkedList<>(Arrays.asList(this.circles));
		circles.remove(circle);
		return add(jumpTowards(circle, circles, destination), circle.getPosition());
	}

	private Point2D jumpTowards(Circle circle, List<Circle> circles, Point2D destination) {
		Optional<Circle> circleOptional = tryJump(circle, circles, destination);
		if(!circleOptional.isPresent())
			return destination;
		circles.remove(circleOptional.get());
		return jumpTowards(circle, circles, updateDestination(circle, circleOptional.get(), destination));
	}

	private Optional<Circle> tryJump(Circle circle, List<Circle> circles, Point2D destination) {
		for(Circle otherCircle : circles) {
			double combinedRadius = circle.getRadius() + otherCircle.getRadius();
			Point2D.Double circleVector = subtract(otherCircle.getPosition(), circle.getPosition());
			if(MathUtil.distance(circleVector, destination) < combinedRadius)
				return Optional.of(otherCircle);
		}
		return Optional.empty();
	}

	@Override
	public String toString() {
		return "Radius: " + minRadius() + ", density: " + getDensity() + " with " + getCircleCount() + " instances: "
				+ Arrays.toString(this.circles);
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

	public double getDensity() {
		double minRadius = minRadius();
		return getCoverage() / MathUtil.getArea(minRadius);
	}

	//endregion
}
