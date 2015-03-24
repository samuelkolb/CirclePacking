package heuristic;

import java.util.Random;

/**
 * Created by samuelkolb on 24/03/15.
 *
 * @author Samuel Kolb
 */
public interface Instance<S> extends ObjectiveFunction<S> {

	/**
	 * Returns a random instance using the given random generator
	 * @param random	The random generator to be used
	 * @return	A random solution
	 */
	public S getInstance(Random random);
}
