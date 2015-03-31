package util;

import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.List;

import static org.junit.Assert.*;

public class MathUtilTest {

	public static final double DELTA = Math.pow(10, -15);

	@Test
	public void testGetArea() throws Exception {
		assertEquals(Math.PI * 3.5 * 3.5, MathUtil.getArea(3.5), DELTA);

	}

	@Test
	public void testDistanceBetween() throws Exception {
		double x1 = 2.3, x2 = 1.9, y1 = 10007, y2 = -18.76;
		Point2D.Double p1 = new Point2D.Double(x1, y1), p2 = new Point2D.Double(x2, y2);
		assertEquals(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)), MathUtil.distance(p1, p2), DELTA);
	}

	@Test
	public void testDistanceToOrigin() throws Exception {
		Point2D.Double point = new Point2D.Double(17.8229, - 1007829334.45);
		assertEquals(MathUtil.distance(point, new Point2D.Double(0, 0)), MathUtil.distance(point), DELTA);
	}

	@Test
	public void testDotProduct() throws Exception {
		double x1 = 2.3, x2 = 1.9, y1 = 10007, y2 = -18.76;
		Point2D.Double p1 = new Point2D.Double(x1, y1), p2 = new Point2D.Double(x2, y2);
		assertEquals(x1 * x2 + y1 * y2, MathUtil.dotProduct(p1, p2), DELTA);
	}

	@Test
	public void testIntersections_NoIntersections() throws Exception {
		assertTrue(MathUtil.intersections(new Point2D.Double(), 10, new Point2D.Double(100, 100), 20).isEmpty());
		assertTrue(MathUtil.intersections(new Point2D.Double(), 10, new Point2D.Double(), 20).isEmpty());
		assertTrue(MathUtil.intersections(new Point2D.Double(), 10, new Point2D.Double(), 10).isEmpty());
	}

	@Test
	public void testIntersections_OneIntersection() throws Exception {
		List<Point2D> list = MathUtil.intersections(new Point2D.Double(), 1, new Point2D.Double(2, 0), 1);
		assertEquals(1, list.size());
		assertEquals(new Point2D.Double(1, 0), list.get(0));
	}

	@Test
	public void testIntersections_TwoIntersections() throws Exception {
		double r1 = 2, r2 = 4;
		double d1 = Math.sqrt(r1 * r1 - 1), d2 = Math.sqrt(r2 * r2 - 1);
		List<Point2D> list = MathUtil.intersections(new Point2D.Double(d1, 0), r1, new Point2D.Double(-d2, 0), r2);
		assertEquals(2, list.size());

		assertEquals(0, list.get(0).getX(), DELTA);
		assertEquals(1, list.get(0).getY(), DELTA);

		assertEquals(0, list.get(1).getX(), DELTA);
		assertEquals(-1, list.get(1).getY(), DELTA);
	}
}