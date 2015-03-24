package heuristic;

import AbstractClasses.ProblemDomain;

import java.util.Random;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public interface Heuristic<S> {

	/**
	 * Sets the heuristics random generator to the given generator
	 * @param random	The random generator to use
	 */
	public void setRandom(Random random);

	/**
	 * Returns the type of this heuristic
	 * @return	The type of this heuristic
	 */
	public ProblemDomain.HeuristicType getType();

	/**
	 * Returns whether this heuristic uses the depth of search parameter
	 * @return	True iff this heuristic uses depth of search
	 */
	public boolean usesDepthOfSearch();

	/**
	 * Sets the depth of search
	 * @param depth	The new depth
	 */
	public void setDepthOfSearch(double depth);

	/**
	 * Returns whether this heuristic uses the mutation intensity parameter
	 * @return	True iff this heuristic uses mutation intensity
	 */
	public boolean usesMutationIntensity();

	/**
	 * Sets the mutation intensity
	 * @param intensity	The new intensity
	 */
	public void setMutationIntensity(double intensity);

	/**
	 * Apply the heuristic to a solution
	 * @param solution	The solution the heuristic should be applied to
	 * @return	The resulting solution
	 */
	public S apply(S solution);

	/**
	 * Apply the heuristic to two solutions
	 * @param solution1	The first solution the heuristic should be applied to
	 * @param solution2	The second solution the heuristic should be applied to
	 * @return	The resulting solution
	 */
	public S apply(S solution1, S solution2);
}
