package util.draw;

import util.Operation;

import javax.swing.*;
import java.awt.*;
import java.awt.font.OpenType;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	private Optional<Color> backgroundColor = Optional.empty();

	public Optional<Color> getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color color) {
		this.backgroundColor = Optional.of(color);
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
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if(getBackgroundColor().isPresent()) {
			graphics2D.setColor(getBackgroundColor().get());
			graphics2D.fillRect(0, 0, getWidth(), getHeight());
		}

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
