package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;

import java.util.Random;

/**
 * Created by samuelkolb on 30/03/15.
 */
public class BlowUpMutation implements Heuristic<Solution> {

	private static final double STEP_SIZE = 0.2;

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
		return solution.blowUp(STEP_SIZE);
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}
