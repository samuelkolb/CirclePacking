import AbstractClasses.HyperHeuristic;
import be.kuleuven.kahosl.acceptance.AcceptanceCriterionType;
import be.kuleuven.kahosl.hyperheuristic.GIHH;
import be.kuleuven.kahosl.selection.SelectionMethodType;
import be.kuleuven.kahosl.util.WriteInfo;
import circle_packing.CirclePacking;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by samuelkolb on 06/04/15.
 *
 * @author Samuel Kolb
 */
public class SingleRun {

	public static final int EXECUTION_TIME = 60000;

	public static void main(String[] args) {

		System.out.println("Which instance should be run?");
		int instance = new Scanner(System.in).nextInt();

		SelectionMethodType selectionType = SelectionMethodType.AdaptiveLimitedLAassistedDHSMentorSTD;
		AcceptanceCriterionType acceptanceType = AcceptanceCriterionType.AdaptiveIterationLimitedListBasedTA;
		String resultFileName = "GIHH_";
		Format dateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");

		long seed = System.currentTimeMillis();
		System.out.println("Seed: " + seed);

		WriteInfo.resultSubFolderName = dateFormatter.format(new Date());
		CirclePacking problem = new CirclePacking(seed, true);

		HyperHeuristic hyper_heuristic_object = new GIHH(seed, problem.getNumberOfHeuristics(), (long) EXECUTION_TIME,
					resultFileName, selectionType, acceptanceType);
		problem.loadInstance(instance);
		hyper_heuristic_object.setTimeLimit((long) EXECUTION_TIME);
		hyper_heuristic_object.loadProblemDomain(problem);
		hyper_heuristic_object.run();

		System.out.println(problem.bestSolutionToString());
	}
}
