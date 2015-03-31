package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;

import java.util.Random;

/**
 * Created by samuelkolb on 31/03/15.
 *
 * @author Samuel Kolb
 */
public class HugCircleHeuristic implements Heuristic<Solution> {

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
		Solution best = solution;
		for(int i = 0; i < solution.getCircleCount(); i++) {
			for(int j = i + 1; j < solution.getCircleCount(); j++) {
				Solution clone = solution.clone(i, solution.jumpTowards(i, solution.getCircle(j).getPosition()));
				if(clone.minRadius() < best.minRadius())
					best = clone;
			}
		}
		return best;
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}
