package circle_packing;

import AbstractClasses.ProblemDomain;
import heuristic.Heuristic;

import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Created by anna on 30-Mar-15.
 */
public class InsertAdjacent implements Heuristic<Solution> {

    private Random random;

    private double mutationIntensity = 0.1;

    @Override
    public void setRandom(Random random) { this.random = random; }

    @Override
    public ProblemDomain.HeuristicType getType() { return ProblemDomain.HeuristicType.RUIN_RECREATE; }

    @Override
    public boolean usesDepthOfSearch() { return false; }

    @Override
    public void setDepthOfSearch(double depth) {

    }

    @Override
    public boolean usesMutationIntensity() { return true; }

    @Override
    public void setMutationIntensity(double intensity) { this.mutationIntensity = intensity; }

    @Override
    public Solution apply(Solution solution) {
        // find 2 adjacent circles, push circle in between

        Point2D.Double[] positions = new Point2D.Double[solution.getCircleCount()];
        /*for(int i = 0; i < solution.getCircleCount(); i++) {
            Circle c = solution.getCircle(i);
            // Note: needed in class Circle: method Circle findNeighbour()
            // return circle to which the distance = this.radius + otherCircle.radius
            // maybe hashmap of all distances + bijhorende circles?
            Circle neighbour = c.findNeighbour();
            Circle neighbour2 = neighbour.findNeighbour();
            // Note: needed in class Circle: method boolean isNeighbour(Circle)
            // pseudocode: return getDistanceTo(Circle) == Circle.radius + this.radius
            if(! neighbour2.equals(c) && neighbour2.isNeighbour(c)) {
                //Note: needed in class Circle: method Point2D.Double getTouchPosition(Circle)
                // no idea how yet..
                Point2D.Double touch = neighbour.getTouchPosition(neighbour2);
                c.moveTo(touch);
            }
            positions[i] = solution.getCircle(i).getPosition();
        }*/
        return new InflateCompressMutation().apply(solution.clone(positions));
    }


/**
            for(int j = i + 1; j < solution.getCircleCount(); j++)
                if(random.nextDouble() < mutationIntensity) {
                    Point2D.Double temp = positions[i];
                    positions[i] = positions[j];
                    solution.getCircle(i).
                    positions[j] = temp;
                }
        return new InflateCompressMutation().apply(solution.clone(positions));
    }*/

    @Override
    public Solution apply(Solution solution1, Solution solution2) { throw new UnsupportedOperationException(); }

}
