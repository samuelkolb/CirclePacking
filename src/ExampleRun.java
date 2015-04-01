/*  
    Adapted from ExampleRun1.java (chesc.jar @ http://www.asap.cs.nott.ac.uk/external/chesc2011/hyflex_download.html)
*/

import java.io.PrintStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import be.kuleuven.kahosl.acceptance.AcceptanceCriterionType;
import be.kuleuven.kahosl.hyperheuristic.GIHH;
import be.kuleuven.kahosl.selection.SelectionMethodType;
import be.kuleuven.kahosl.util.WriteInfo;
import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import SAT.SAT;
import circle_packing.CirclePacking;
import circle_packing.Solution;

/**
 * This class show hows to run GIHH
 */
public class ExampleRun {

	public static final int EXECUTION_TIME = 120000;

	public static void main(String[] args) {

		System.out.println("Which instance should be run?");
		int instance = new Scanner(System.in).nextInt();

		SelectionMethodType selectionType = SelectionMethodType.AdaptiveLimitedLAassistedDHSMentorSTD;
		AcceptanceCriterionType acceptanceType = AcceptanceCriterionType.AdaptiveIterationLimitedListBasedTA;
		String resultFileName = "GIHH_";
		Format dateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");

		List<Solution> runs = new ArrayList<>();
		double best = Double.NEGATIVE_INFINITY;
		double average = 0;

		int numberRuns = 8;

		while(runs.size() < numberRuns) {

			long seed = System.currentTimeMillis();

			WriteInfo.resultSubFolderName = dateFormatter.format(new Date());

			//create a ProblemDomain object with a seed for the random number generator
			CirclePacking problem = new CirclePacking(seed, false);

			//creates an HyperHeuristic object with a seed for the random number generator
			HyperHeuristic hyper_heuristic_object = new GIHH(seed, problem.getNumberOfHeuristics(), (long) EXECUTION_TIME,
					resultFileName, selectionType, acceptanceType);

			//we must load an instance within the problem domain, in this case we choose instance 2
			problem.loadInstance(instance);

			//we must set the time limit for the hyper-heuristic in milliseconds
			hyper_heuristic_object.setTimeLimit((long) EXECUTION_TIME);

			//a key step is to assign the ProblemDomain object to the HyperHeuristic object.
			//However, this should be done after the instance has been loaded, and after the time limit has been set
			hyper_heuristic_object.loadProblemDomain(problem);

			//now that all of the parameters have been loaded, the run method can be called.
			//this method starts the timer, and then calls the solve() method of the hyper_heuristic_object.
			hyper_heuristic_object.run();

			if(problem.getBestSolutionValue() > 0) {
				runs.add(problem.getBest());
				best = Math.max(best, problem.getBest().getDensity());
				average += problem.getBest().getDensity() / numberRuns;
				System.out.println(problem.bestSolutionToString());
			} else {
				System.out.println("Skipped");
			}
		}
		double std = 0;
		for(Solution solution : runs) {
			double density = solution.getDensity();
			std += (density - average) * (density - average);
		}
		std = Math.sqrt(std / runs.size());
		System.out.println();
		for(Solution solution : runs)
			System.out.println(solution.getDensity());
		System.out.println();
		System.out.println("\nBest:\t\t" + best + "\nAverage:\t" + average + "\nStd:\t\t" + std);
	}
}
