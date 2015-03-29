package circle_packing;

import org.junit.Assert;
import org.junit.Test;
import util.MathUtil;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;
import static util.MathUtil.*;

public class SolutionTest {

	public static final double DELTA = Math.pow(10, -15);

	private static final Solution solution = new Solution(new double[]{1, 1.5, 2}, new Point2D.Double[]{
			new Point2D.Double(10, 0),
			new Point2D.Double(0, 0),
			new Point2D.Double(-10, 0)
	});

	private static final Solution overlapping = new Solution(new double[]{2, 2}, new Point2D.Double[]{
			new Point2D.Double(0, 0),
			new Point2D.Double(0.5, 0.5)
	});

	@Test
	public void testClone() throws Exception {
		Point2D.Double origin = new Point2D.Double(0, 0);
		Point2D.Double[] positions = new Point2D.Double[]{origin, origin, origin};
		Solution clone = solution.clone(positions);
		assertEquals(solution.getCircleCount(), clone.getCircleCount());
		for(int i = 0; i < clone.getCircleCount(); i++) {
			assertEquals(origin, clone.getCircle(i).getPosition());
			assertEquals(solution.getCircle(i).getRadius(), clone.getCircle(i).getRadius(), DELTA);
		}
	}

	@Test
	public void testMoveTowards() throws Exception {
		// TODO
	}

	@Test
	public void testMinRadius() throws Exception {
		assertEquals(12.0, solution.minRadius(), DELTA);
		assertEquals(distance(new Point2D.Double(0.5, 0.5)) + 2, overlapping.minRadius(), DELTA);
	}

	@Test
	public void testOverlaps() throws Exception {
		assertFalse(solution.overlaps());
		assertTrue(overlapping.overlaps());
	}

	@Test
	public void testGetCoverage() throws Exception {
		assertEquals(getArea(1) + getArea(1.5) + getArea(2), solution.getCoverage(), DELTA);
	}
}