package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;

import java.util.Random;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class ShiftMutation implements Heuristic<Solution> {

	private double mutationIntensity = 0.1;

	private Random random;

	@Override
	public void setRandom(Random random) {
		this.random = random;
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
		return true;
	}

	@Override
	public void setMutationIntensity(double intensity) {
		this.mutationIntensity = intensity;
	}

	@Override
	public Solution apply(Solution solution) {
		return null;
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		return null;
	}
}
