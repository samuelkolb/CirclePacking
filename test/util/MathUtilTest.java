package util;

import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

public class MathUtilTest {

	public static final double DELTA = Math.pow(10, -15);

	@Test
	public void testGetArea() throws Exception {
		Assert.assertEquals(Math.PI * 3.5 * 3.5, MathUtil.getArea(3.5), DELTA);

	}

	@Test
	public void testDistanceBetween() throws Exception {
		double x1 = 2.3, x2 = 1.9, y1 = 10007, y2 = -18.76;
		Point2D.Double p1 = new Point2D.Double(x1, y1), p2 = new Point2D.Double(x2, y2);
		Assert.assertEquals(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)), MathUtil.distance(p1, p2), DELTA);
	}

	@Test
	public void testDistanceToOrigin() throws Exception {
		Point2D.Double point = new Point2D.Double(17.8229, - 1007829334.45);
		Assert.assertEquals(MathUtil.distance(point, new Point2D.Double(0, 0)), MathUtil.distance(point), DELTA);
	}

	@Test
	public void testDotProduct() throws Exception {
		double x1 = 2.3, x2 = 1.9, y1 = 10007, y2 = -18.76;
		Point2D.Double p1 = new Point2D.Double(x1, y1), p2 = new Point2D.Double(x2, y2);
		Assert.assertEquals(x1 * x2 + y1 * y2, MathUtil.dotProduct(p1, p2), DELTA);
	}
}