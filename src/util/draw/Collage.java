package util.draw;

import util.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.font.OpenType;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A collage is a fixed size container to which elements or shapes can be added.
 * The collage has a centered Euclidean coordinate system and will be resized to fit the panel size
 *
 * @author Samuel Kolb
 */
public class Collage extends JPanel {

	private final List<Element> elements = new ArrayList<>();

	private List<Element> getElements() {
		return elements;
	}

	/**
	 * Add an element to the collage
	 * @param element	The element to add
	 */
	public void addElement(Element element) {
		this.elements.add(element);
	}

	/**
	 * Add a shape at a specified position
	 * @param point2D	The position (center) of the shape in the collage
	 * @param shape		The shape to add
	 */
	public void addElement(Point2D point2D, Shape shape) {
		this.addElement(new ShapeElement(point2D, shape));
	}

	private final Point2D size;

	public Point2D getCollageSize() {
		return this.size;
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
	}

	/**
	 * Creates a new collage with the given size
	 * @param size	The size of the collage
	 */
	public Collage(Point2D size) {
		this.size = size;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;

		double factor = getCollageSize().getX() / getCollageSize().getY() > getWidth() / getHeight()
				? getWidth() / getCollageSize().getX()
				: getHeight() / getCollageSize().getY();

		for(Element element : getElements()) {
			Element scaled = element.scale(factor);
			Point2D.Double position = new Point2D.Double(scaled.getPosition().getX(), 0 - scaled.getPosition().getY());
			position = Operation.add(position, Operation.scale(size, factor / 2));
			Element e = scaled.moveTo(position);
			e.draw(graphics2D);
		}
	}
}
