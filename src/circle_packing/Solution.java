package circle_packing;

import java.awt.geom.Point2D;
import java.util.Arrays;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class Solution {

	//region Variables
	private final double[] radii;

	public double[] getRadii() {
		return radii;
	}

	private final Point2D.Double[] positions;

	public Point2D.Double[] getPositions() {
		return positions;
	}

	//endregion

	//region Construction

	/**
	 * Creates a new solution
	 * @param radii		The radii of the circles
	 * @param positions	The positions of the circles
	 */
	public Solution(double[] radii, Point2D.Double[] positions) {
		if(radii.length != positions.length)
			throw new IllegalArgumentException();
		this.radii = radii;
		this.positions = positions;
	}

	//endregion

	//region Public methods
	/**
	 * Clone this solution with the given positions
	 * @param positions	The positions of the circles
	 * @return	A clone of this current solution with different positions for the circles
	 */
	public Solution clone(Point2D.Double[] positions) {
		return new Solution(this.radii, positions);
	}

	@Override
	public String toString() {
		return Arrays.toString(getRadii()) + ": " + Arrays.toString(getPositions());
	}

	//endregion
}
