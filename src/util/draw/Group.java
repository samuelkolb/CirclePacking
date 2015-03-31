package util.draw;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelkolb on 30/03/15.
 * // TODO
 *
 * @author Samuel Kolb
 */
public class Group implements Shape {

	private final List<Element> elements = new ArrayList<>();

	private List<Element> getElements() {
		return elements;
	}

	@Override
	public Point2D getSize() {
		double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
		double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY;

		/*for(Element element : getElements()) {
			maxX = Math.max(maxX, element.)
		}*/
		return null;
	}

	@Override
	public Shape scale(double factor) {
		return null;
	}

	@Override
	public void draw(Graphics2D graphics2D, Point center) {

	}

	@Override
	public boolean includes(Point point) {
		return false;
	}
}
