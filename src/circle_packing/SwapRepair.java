package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public class SwapRepair implements Heuristic<Solution> {

	private Random random;

	private double mutationIntensity = 0.1;

	@Override
	public void setRandom(Random random) {
		this.random = random;
	}

	@Override
	public ProblemDomain.HeuristicType getType() {
		return ProblemDomain.HeuristicType.RUIN_RECREATE;
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
		// NOTE: also switches circles with the same radius
		Point2D.Double[] positions = solution.getPositions();
		for(int i = 0; i < solution.getCircleCount(); i++)
			for(int j = i + 1; j < solution.getCircleCount(); j++)
				if(random.nextDouble() < mutationIntensity) {
					Point2D.Double temp = positions[i];
					positions[i] = positions[j];
					positions[j] = temp;
				}
		return new InflateCompressMutation().apply(solution.clone(positions));
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}
