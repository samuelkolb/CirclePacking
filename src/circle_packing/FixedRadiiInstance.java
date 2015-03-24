package circle_packing;

import heuristic.Instance;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class FixedRadiiInstance implements Instance<Solution> {

	//region Variables
	private final double[] radii;

	private final double overlapPenalty = 5;
	//endregion

	//region Construction

	/**
	 * Creates a new fixed radii instance
	 * @param radii	The radii of solutions of this instance
	 */
	public FixedRadiiInstance(double[] radii) {
		this.radii = radii;
	}

	//endregion

	//region Public methods

	@Override
	public Solution getInstance(Random random) {
		Point2D.Double[] positions = new Point2D.Double[this.radii.length];
		for(int i = 0; i < this.radii.length; i++)
			positions[i] = new Point2D.Double(random.nextInt(), random.nextInt());
		return new Solution(this.radii, positions);
	}

	@Override
	public double score(Solution solution) {
		double minRadius = minRadius(solution);
		double density = getArea(minRadius) - getCoverage(solution);
		return 1 / density;
	}

	//endregion

	private double minRadius(Solution solution) {
		double minRadius = 0;
		for(int i = 0; i < solution.getRadii().length; i++) {
			double radius = distance(solution.getPositions()[i]) + solution.getRadii()[i];
			if(radius > minRadius)
				minRadius = radius;
		}
		return minRadius;
	}

	private double getCoverage(Solution solution) {
		double solutionCoverage = 0;
		for(double radius : solution.getRadii())
			solutionCoverage += getArea(radius);
		return solutionCoverage;
	}

	private boolean overlaps(Solution sol) {
		for(int i = 0; i < sol.getPositions().length; i++)
			for(int j = i + 1; j < sol.getPositions().length; j++)
				if(distance(sol.getPositions()[i], sol.getPositions()[j]) < sol.getRadii()[i] + sol.getRadii()[j])
					return true;
		return false;
	}

	private double getArea(double radius) {
		return radius * radius * Math.PI;
	}

	private double distance(Point2D.Double point) {
		return Math.sqrt(point.x * point.x + point.y * point.y);
	}

	private double distance(Point2D.Double point1, Point2D.Double point2) {
		return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
	}
}
