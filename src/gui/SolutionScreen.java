package gui;

import circle_packing.*;
import util.draw.Collage;
import util.draw.GeometricShape;
import util.draw.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by samuelkolb on 30/03/15.
 *
 * @author Samuel Kolb
 */
public class SolutionScreen extends JFrame {

	private static final int SIZE = 500;

	public SolutionScreen() {
		super();
		setTitle("Display");
		createWindow();
	}

	public void showSolution(Solution solution) {
		double minRadius = solution.minRadius();
		Collage collage = new Collage(new Point2D.Double(minRadius * 2, minRadius * 2));
		collage.addElement(new Point2D.Double(), new Circle(minRadius).color(Color.BLACK));
		for(int i = 0; i < solution.getCircleCount(); i++) {
			GeometricShape shape = new Circle(solution.getCircle(i).getRadius()).fill().color(Color.ORANGE);
			collage.addElement(solution.getCircle(i).getPosition(), shape);
		}
		collage.setPreferredSize(new Dimension(SIZE, SIZE));
		getContentPane().removeAll();
		getContentPane().add(collage);
		pack();
		repaint();
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

	public static void main(String[] args) throws InterruptedException {
		SolutionScreen solutionScreen = new SolutionScreen();

		double[] radii = new double[]{1, 2, 3, 4};
		Random random = new Random(1234);
		Point2D.Double[] positions = new Point2D.Double[radii.length];
		for(int i = 0; i < radii.length; i++)
			positions[i] = new Point2D.Double(Math.cos(i * Math.PI * 2 / radii.length), Math.sin(i * Math.PI * 2 / radii.length));

		Solution solution = new Solution(radii, positions);
		solutionScreen.showSolution(solution);

		Thread.sleep(2000);

		Solution blownUp = new InflateCompressMutation().apply(solution);
		solutionScreen.showSolution(blownUp);
	}
}
