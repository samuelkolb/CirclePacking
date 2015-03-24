package heuristic;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public interface ObjectiveFunction<S> {

	/**
	 * Returns the score of the given solution
	 * @param solution	The solution to assign a score to
	 * @return	The score of the solution (smaller is better)
	 */
	public double score(S solution);
}
