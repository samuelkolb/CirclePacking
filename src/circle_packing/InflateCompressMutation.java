package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;

import java.util.Random;

/**
 * Created by samuelkolb on 30/03/15.
 */
public class InflateCompressMutation implements Heuristic<Solution> {

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
		return new CenterHeuristic().apply(new PullToCenterHeuristic().apply(new BlowUpMutation().apply(solution)));
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}
