package circle_packing;

import heuristic.Instance;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class FixedRadiiInstance implements Instance<Solution> {

	//region Variables
	private final double[] radii;

	//endregion

	//region Construction

	/**
	 * Creates a new fixed radii instance
	 * @param radii	The radii of solutions of this instance
	 */
	public FixedRadiiInstance(double[] radii) {
		this.radii = radii;
	}

	//endregion

	//region Public methods

	@Override
	public Solution getInstance(Random random) {
		double maxRadius = 0;
		for(double radius : this.radii)
			maxRadius = Math.max(maxRadius, radius);
		Point2D.Double[] positions = new Point2D.Double[this.radii.length];
		for(int i = 0; i < this.radii.length; i++)
			positions[i] = new Point2D.Double(random.nextDouble() * maxRadius * 2, random.nextDouble() * maxRadius * 2);
		return new InflateCompressMutation().apply(new Solution(this.radii, positions));
	}

	@Override
	public double score(Solution solution) {
		double density = solution.getDensity();
		double overlapPenalty = 5;

		return 1 - density + (solution.overlaps() ? overlapPenalty : 0);
	}

	//endregion

}
