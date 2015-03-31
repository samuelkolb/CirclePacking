package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;
import util.MathUtil;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Random;

/**
 * Created by anna on 31-Mar-15.
 */
public class HugPair implements Heuristic<Solution> {


	@Override
	public void setRandom(Random random) { }

	@Override
	public ProblemDomain.HeuristicType getType() { return ProblemDomain.HeuristicType.LOCAL_SEARCH; }


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
	public void setMutationIntensity(double intensity) { }

	@Override
	public Solution apply(Solution solution) {
		Solution best = solution;
		for (int c = 0; c < solution.getCircleCount(); c++) {
			for (int k = c + 1; k < solution.getCircleCount(); k++) {
				for (int j = k + 1; j < solution.getCircleCount(); j++) {
					// Note: variables to shorten the MathUtil.intersections call
					Circle c1 = solution.getCircle(k);
					double r1 = c1.getRadius();
					Point2D p1 = c1.getPosition();
					Circle c2 = solution.getCircle(j);
					double r2 = c2.getRadius();
					Point2D p2 = c2.getPosition();
					List<Point2D> intersections = MathUtil.intersections(p1, r1, p2, r2);
					if (intersections.size() != 0) {
						for (Point2D i : intersections) {
							Point2D newPos = solution.getCircle(c).moveTo(i).getPosition();
							Solution clone = solution.clone(c, newPos);
							if (clone.minRadius() < best.minRadius())
								best = clone;
						}
					}
				}
			}
		}
		// return best solution
		return best;
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}