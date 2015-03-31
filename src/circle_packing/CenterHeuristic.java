package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;
import util.Operation;

import java.awt.geom.Point2D;
import java.util.DoubleSummaryStatistics;
import java.util.Random;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public class CenterHeuristic implements Heuristic<Solution> {

	@Override
	public void setRandom(Random random) {

	}

	@Override
	public ProblemDomain.HeuristicType getType() {
		return ProblemDomain.HeuristicType.LOCAL_SEARCH;
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
		System.out.println("[CenterHeuristic] Center");
		double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
		double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY;
		for(int i = 0; i < solution.getCircleCount(); i++) {
			maxX = Math.max(maxX, solution.getCircle(i).getPosition().getX() + solution.getCircle(i).getRadius());
			maxY = Math.max(maxY, solution.getCircle(i).getPosition().getY() + solution.getCircle(i).getRadius());
			minX = Math.min(minX, solution.getCircle(i).getPosition().getX() - solution.getCircle(i).getRadius());
			minY = Math.min(minY, solution.getCircle(i).getPosition().getY() - solution.getCircle(i).getRadius());
		}
		Point2D shift = Operation.invert(new Point2D.Double((maxX + minX) / 2, (maxY + minY) / 2));
		Point2D[] positions = solution.getPositions();
		for(int i = 0; i < positions.length; i++)
			positions[i] = Operation.add(positions[i], shift);
		Solution clone = solution.clone(positions);
		return clone.minRadius() < solution.minRadius() ? clone : solution;
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}
