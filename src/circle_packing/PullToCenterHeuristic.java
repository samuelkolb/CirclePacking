package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;
import util.Operation;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by samuelkolb on 29/03/15.
 */
public class PullToCenterHeuristic implements Heuristic<Solution> {

	private static final double STEP_SIZE = 0.005;

	@Override
	public void setRandom(Random random) {

	}

	@Override
	public ProblemDomain.HeuristicType getType() {
		return ProblemDomain.HeuristicType.MUTATION;
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
		return solution.pullToCenter(STEP_SIZE, Math.pow(10, -15));
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}
