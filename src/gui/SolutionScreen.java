package gui;

import circle_packing.*;
import util.draw.Collage;
import util.draw.shape.Circle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public class SolutionScreen extends JFrame {

	private static final int SIZE = 800;

	public SolutionScreen() {
		super();
		setTitle("Display");
		createWindow();
	}

	public void showSolution(Solution solution, boolean done) {
		double minRadius = solution.minRadius();
		Collage collage = new Collage(new Point2D.Double(minRadius * 2, minRadius * 2));
		collage.setBackgroundColor(done ? new Color(255, 220, 140) : Color.LIGHT_GRAY);
		collage.addElement(new Point2D.Double(), new Circle(minRadius).fill().color(Color.WHITE));
		for(int i = 0; i < solution.getCircleCount(); i++) {
			Circle circle = new Circle(solution.getCircle(i).getRadius());
			Color color = new Color(255, (int) (i/(double)solution.getCircleCount() * 150 + 100), 0);
			collage.addElement(solution.getCircle(i).getPosition(), circle.fill().color(color));
			collage.addElement(solution.getCircle(i).getPosition(), circle.color(Color.BLACK));
		}
		collage.setPreferredSize(new Dimension(SIZE, SIZE));
		getContentPane().removeAll();
		getContentPane().add(collage);
		pack();
		validate();
	}

	private void createWindow() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			throw new IllegalStateException();
		}
		setVisible(true);
		pack();
	}

	public void print(File file) {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		paint(g);
		g.dispose();
		try {
			ImageIO.write(image, "png", file);
		} catch (Exception ignored) {}

	}
}
