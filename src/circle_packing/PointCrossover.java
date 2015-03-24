package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class PointCrossover implements Heuristic<Solution> {

	private final int points;

	/**
	 * Creates a new point crossover
	 * @param points	How many points are used to crossover
	 */
	public PointCrossover(int points) {
		this.points = points;
	}

	@Override
	public void setRandom(Random random) {

	}

	@Override
	public ProblemDomain.HeuristicType getType() {
		return ProblemDomain.HeuristicType.CROSSOVER;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return false;
	}

	@Override
	public void setDepthOfSearch(double depth) {

	}

	@Override
	public boolean usesMutationIntensity() {
		return false;
	}

	@Override
	public void setMutationIntensity(double intensity) {

	}

	@Override
	public Solution apply(Solution solution) {
		return solution;
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		if(solution1.getRadii().length != solution2.getRadii().length)
			throw new IllegalArgumentException("Solutions are not of the same size");
		int points = Math.min(this.points, solution1.getRadii().length - 1);
		double blockSize = solution1.getRadii().length / (double) (points + 1);
		Point2D.Double[] positions = new Point2D.Double[solution1.getPositions().length];
		boolean one = true;
		double sum = 0;
		for(int i = 0; i < positions.length; i++) {
			sum += 1;
			positions[i] = one ? solution1.getPositions()[i] : solution2.getPositions()[i];
			if(sum >= blockSize) {
				sum -= blockSize;
				one = !one;
			}
		}
		return solution1.clone(positions);
	}

	/**
	 * Provides a visual test of the heuristic
	 * @param args	Arguments (ignored)
	 */
	public static void main(String[] args) {
		Solution solution1 = new Solution(new double[]{1, 2, 3, 4, 5},
				new Point2D.Double[]{new Point2D.Double(1,1), new Point2D.Double(1,1), new Point2D.Double(1,1), new Point2D.Double(1,1), new Point2D.Double(1,1)});
		Solution solution2 = new Solution(new double[]{1, 2, 3, 4, 5},
				new Point2D.Double[]{new Point2D.Double(2,2), new Point2D.Double(2,2), new Point2D.Double(2,2), new Point2D.Double(2,2), new Point2D.Double(2,2)});

		System.out.println(new PointCrossover(1).apply(solution1, solution2));
	}
}