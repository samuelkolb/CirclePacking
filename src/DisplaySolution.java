import circle_packing.Circle;
import circle_packing.Solution;
import gui.SolutionScreen;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by samuelkolb on 02/04/15.
 *
 * @author Samuel Kolb
 */
public class DisplaySolution {

	public static void main(String[] args) throws InterruptedException {
		String input = new Scanner(System.in).nextLine();
		SolutionScreen solutionScreen = new SolutionScreen();
		String circleString = input.substring(input.indexOf("["));
		Pattern pattern = Pattern.compile("Circle at \\((-?\\d+.\\d+), (-?\\d+.\\d+)\\) with radius (\\d+.\\d+)");
		Matcher matcher = pattern.matcher(circleString);
		List<Circle> circles = new ArrayList<>();
		while(matcher.find()) {
			double radius = Double.parseDouble(matcher.group(3));
			double x = Double.parseDouble(matcher.group(1)), y = Double.parseDouble(matcher.group(2));
			circles.add(new circle_packing.Circle(radius, new Point2D.Double(x, y)));
		}
		solutionScreen.showSolution(new Solution(circles));
		String fileName = new Scanner(System.in).nextLine();
		solutionScreen.print(new File(fileName + ".png"));
	}

}
