package circle_packing;

import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;
import static util.MathUtil.distance;
import static util.MathUtil.getArea;

public class SolutionTest {

	public static final double DELTA = Math.pow(10, -15);

	private static final Solution solution = new Solution(new double[]{1, 1.5, 2}, new Point2D.Double[]{
			new Point2D.Double(10, 0),
			new Point2D.Double(0, 0),
			new Point2D.Double(-10, 0)
	});

	private static final Solution touching = new Solution(new double[]{1, 1}, new Point2D.Double[]{
			new Point2D.Double(1, 0),
			new Point2D.Double(-1, 0)
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
		Point2D.Double origin = new Point2D.Double(0, 0);
		Point2D.Double moved = solution.moveTowards(0, origin);
		assertEquals(1.5 + 1, moved.getX(), DELTA);
		assertEquals(0, moved.getY(), DELTA);
	}

	@Test
	public void testJumpTowards() throws Exception {
		double[] radii = new double[]{1, 1, 1, 1};
		Point2D[] positions = new Point2D[]{new Point2D.Double(0, 0), new Point2D.Double(2.5, 0),
				new Point2D.Double(6.5, 0), new Point2D.Double(10, 0)};
		Solution solution = new Solution(radii, positions);
		Point2D moved = solution.jumpTowards(3, new Point2D.Double());
		assertEquals(4.5, moved.getX(), DELTA);
		assertEquals(0, moved.getY(), DELTA);
	}

	@Test
	public void testMinRadius() throws Exception {
		assertEquals(12.0, solution.minRadius(), DELTA);
		assertEquals(distance(new Point2D.Double(0.5, 0.5)) + 2, overlapping.minRadius(), DELTA);
	}

	@Test
	public void testOverlaps() throws Exception {
		assertFalse(solution.overlaps());
		assertFalse(touching.overlaps());
		assertTrue(overlapping.overlaps());
	}

	@Test
	public void testGetCoverage() throws Exception {
		assertEquals(getArea(1) + getArea(1.5) + getArea(2), solution.getCoverage(), DELTA);
	}
}