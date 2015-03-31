package circle_packing;

import AbstractClasses.ProblemDomain;
import gui.SolutionScreen;
import heuristic.Heuristic;
import heuristic.Instance;
import heuristic.ObjectiveFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public class CirclePacking extends ProblemDomain {

	//region Variables
	private final SolutionScreen screen = new SolutionScreen();

	public SolutionScreen getScreen() {
		return screen;
	}

	private final ObjectiveFunction<Solution> objectiveFunction;

	private ObjectiveFunction<Solution> getObjectiveFunction() {
		return objectiveFunction;
	}

	private static final List<Heuristic<Solution>> heuristics = Arrays.asList(
			new PointCrossover(1),
			new UniformCrossover(),
			new ShiftMutation(),
			new ShiftCenterMutation(),
			new SwapRepair(),
			new CenterHeuristic(),
			new HugCircleHeuristic(),
			new HugPairHeuristic());

	private Heuristic<Solution> getHeuristic(int index) {
		return heuristics.get(index);
	}

	private final List<Instance<Solution>> instances;

	private Instance<Solution> instance;

	private Instance<Solution> getInstance() {
		return instance;
	}

	private int bestSolutionId = -1;

	private double bestSolutionValue = Double.POSITIVE_INFINITY;

	private Solution[] solutions;

	private Solution getSolution(int index) {
		return this.solutions[index];
	}

	private void setSolution(int index, Solution solution) {
		this.solutions[index] = solution;
	}
	//endregion

	//region Construction

	/**
	 * Creates a new circle packing domain with the given random seed
	 * @param seed	The random seed used as source of randomness
	 */
	public CirclePacking(long seed) {
		super(seed);
		this.objectiveFunction = solution -> getInstance().score(solution);
		this.instances = Arrays.asList(getLinearInstance(10));
		for(Heuristic<Solution> heuristic : heuristics)
			heuristic.setRandom(rng);
	}

	public static Instance<Solution> getLinearInstance(int size) {
		double[] radii = new double[size];
		for(int i = 0; i < size; i++)
			radii[i] = i + 1;
		return new FixedRadiiInstance(radii);
	}
	//endregion

	//region Public methods
	@Override
	public int[] getHeuristicsOfType(HeuristicType heuristicType) {
		return getIds(heuristic -> heuristic.getType().equals(heuristicType));
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {
		return getIds(Heuristic::usesMutationIntensity);
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {
		return getIds(Heuristic::usesDepthOfSearch);
	}

	@Override
	public void loadInstance(int i) {
		this.instance = this.instances.get(i);
	}

	@Override
	public void setMemorySize(int i) {
		this.solutions = new Solution[i];
	}

	@Override
	public void initialiseSolution(int position) {
		setSolution(position, getInstance().getInstance(rng));
		updateBest(position);
	}

	@Override
	public int getNumberOfHeuristics() {
		return heuristics.size();
	}

	@Override
	public double applyHeuristic(int heuristicId, int sourceIndex, int resultIndex) {
		System.out.println("[CirclePacking] Apply " + getHeuristic(heuristicId).getClass().getName());
		Solution solution = getHeuristic(heuristicId).apply(getSolution(sourceIndex));
		setSolution(resultIndex, solution);
		return updateBest(resultIndex);
	}

	@Override
	public double applyHeuristic(int heuristicId, int sourceIndex1, int sourceIndex2, int resultIndex) {
		System.out.println("[CirclePacking] Apply " + getHeuristic(heuristicId).getClass().getName());
		Solution solution = getHeuristic(heuristicId).apply(getSolution(sourceIndex1), getSolution(sourceIndex2));
		setSolution(resultIndex, solution);
		return updateBest(resultIndex);
	}

	@Override
	public void copySolution(int sourceIndex, int resultIndex) {
		setSolution(resultIndex, getSolution(sourceIndex));
	}

	@Override
	public String toString() {
		return "Circle Packing";
	}

	@Override
	public int getNumberOfInstances() {
		return this.instances.size();
	}

	@Override
	public String bestSolutionToString() {
		return this.bestSolutionValue < 0 ? "No best solution" : solutionToString(this.bestSolutionId);
	}

	@Override
	public double getBestSolutionValue() {
		return this.bestSolutionValue;
	}

	@Override
	public String solutionToString(int i) {
		return getSolution(i).toString();
	}

	@Override
	public double getFunctionValue(int i) {
		return getObjectiveFunction().score(getSolution(i));
	}

	@Override
	public boolean compareSolutions(int i1, int i2) {
		return getSolution(i1).equals(getSolution(i2));
	}

	//endregion

	private int[] getIds(Predicate<Heuristic> heuristicPredicate) {
		List<Integer> idList = new ArrayList<>();
		for(int i = 0; i < heuristics.size(); i++)
			if(heuristicPredicate.test(heuristics.get(i)))
				idList.add(i);
		int[] ids = new int[idList.size()];
		for(int i = 0; i < ids.length; i++)
			ids[i] = idList.get(i);
		return ids;
	}

	private double updateBest(int index) {
		double value = getFunctionValue(index);
		if(value < this.bestSolutionValue) {
			this.bestSolutionId = index;
			this.bestSolutionValue = value;
			getScreen().showSolution(getSolution(index));
		}
		return value;
	}
}
