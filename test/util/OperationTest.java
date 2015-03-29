package util;

import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Point2D;

import static org.junit.Assert.*;

public class OperationTest {

	public static final double DELTA = Math.pow(10, -15);

	public static final double X1 = 18.6663129, X2 = -1988.27378, Y1 = -20000.82, Y2 = -0.287390;

	public static final Point2D.Double P1 = new Point2D.Double(X1, Y1), P2 = new Point2D.Double(X2, Y2);

	@Test
	public void testInvert() throws Exception {
		Assert.assertEquals(new Point2D.Double(0 - X1, 0 - Y1), Operation.invert(P1));
	}

	@Test
	public void testSubtract() throws Exception {
		Assert.assertEquals(new Point2D.Double(X1 - X2, Y1 - Y2), Operation.subtract(P1, P2));
	}

	@Test
	public void testAdd() throws Exception {
		Assert.assertEquals(new Point2D.Double(X1 + X2, Y1 + Y2), Operation.add(P1, P2));
	}

	@Test
	public void testProject() throws Exception {
		Assert.assertEquals(new Point2D.Double(X1, 0), Operation.project(P1, new Point2D.Double(1, 0)));
		Assert.assertEquals(new Point2D.Double(0, Y1), Operation.project(P1, new Point2D.Double(0, 1)));
	}

	@Test
	public void testGetUnit() throws Exception {
		Assert.assertEquals(new Point2D.Double(1, 0), Operation.getUnit(new Point2D.Double(2, 0)));
		double factor = Math.sqrt(X1 * X1 + Y1 * Y1);
		Assert.assertEquals(new Point2D.Double(X1 / factor, Y1 / factor), Operation.getUnit(P1));
	}

	@Test
	public void testScale() throws Exception {
		double factor = 4.87;
		Assert.assertEquals(new Point2D.Double(X1 * factor, Y1 * factor), Operation.scale(P1, factor));
	}
}