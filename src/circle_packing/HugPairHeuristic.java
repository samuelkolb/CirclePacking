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
public class HugPairHeuristic implements Heuristic<Solution> {


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
			Circle moving = solution.getCircle(c);
			for (int k = 0; k < solution.getCircleCount(); k++) {
				if(k == c)
					continue;
				for (int j = k + 1; j < solution.getCircleCount(); j++) {
					if(j == c)
						continue;

					Circle c1 = solution.getCircle(k);
					Circle c2 = solution.getCircle(j);

					double r1 = c1.getRadius() + moving.getRadius();
					double r2 = c2.getRadius() + moving.getRadius();
					List<Point2D> intersections = MathUtil.intersections(c1.getPosition(), r1, c2.getPosition(), r2);

					for (Point2D intersection : intersections) {
						Point2D newPos = solution.getCircle(c).moveTo(intersection).getPosition();
						Solution clone = solution.clone(c, newPos);
						if (!clone.overlaps() && clone.minRadius() < best.minRadius())
							best = clone;
					}
				}
			}
		}
		return best;
	}

	@Override
	public Solution apply(Solution solution1, Solution solution2) {
		throw new UnsupportedOperationException();
	}
}