package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;
import util.MathUtil;
import util.Operation;

import java.awt.geom.Point2D;
import java.util.Random;

import static util.Operation.*;

/**
 * Created by samuelkolb on 31/03/15.
 *
 * @author Samuel Kolb
 */
public class MigrateHeuristic implements Heuristic<Solution> {

	private static final double STEP_SIZE = 0.005;

	private static final int MAX_STEPS = 5000;

	private double depth = 0.1;

	public double getDepth() {
		return depth;
	}

	private Random random;

	@Override
	public void setRandom(Random random) {
		this.random = random;
	}

	@Override
	public ProblemDomain.HeuristicType getType() {
		return ProblemDomain.HeuristicType.LOCAL_SEARCH;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return true;
	}

	@Override
	public void setDepthOfSearch(double depth) {
		this.depth = depth;
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
		int steps = 0;
		Solution best = solution;
		while(steps++ < MAX_STEPS * getDepth()) {
			Solution current = best;
			for(int i = 0; i < solution.getCircleCount(); i++) {
				if(MathUtil.distance(current.getCircle(i).getPosition()) == 0)
					continue;
				Point2D move = scale(getUnit(current.getCircle(i).getPosition()), STEP_SIZE);
				Solution clone1 = current.clone(movePositions(current, move));
				Solution clone2 = current.clone(movePositions(current, invert(move)));
				Solution clone = clone1.minRadius() < clone2.minRadius() ? clone1 : clone2;
				if(clone.minRadius() < best.minRadius())
					best = clone;
			}
		}
		return best;
	}

	private Point2D[] movePositions(Solution current, Point2D move) {
		Point2D[] positions = current.getPositions();
		for(int j = 0; j < positions.length; j++)
			positions[j] = add(positions[j], move);
		return positions;
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}
