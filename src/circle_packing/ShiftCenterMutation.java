package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;
import util.Operation;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class ShiftCenterMutation implements Heuristic<Solution> {

	private double mutationIntensity = 0.1;

	public final double maxShiftFactor = 0.7;

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
	public void setDepthOfSearch(double depth) { }

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
		Point2D.Double[] positions = new Point2D.Double[solution.getCircleCount()];
		for(int i = 0; i < solution.getCircleCount(); i++) {
			if(random.nextDouble() < mutationIntensity) {
				double distance = solution.minRadius() * maxShiftFactor * mutationIntensity * random.nextDouble();
				Point2D.Double direction = Operation.getUnit(Operation.invert(solution.getCircle(i).getPosition()));
				Point2D.Double destination = Operation.scale(direction, distance);
				positions[i] = solution.moveTowards(i, destination);
			} else {
				positions[i] = solution.getCircle(i).getPosition();
			}
		}
		return solution.clone(positions);
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}
