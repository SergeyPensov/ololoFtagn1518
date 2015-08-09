package finalStates;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class Solver {

    private Problem problem;

    public Solver(Problem problem) {
        this.problem = problem;
    }

    public SolverResult solve(int seed) {
        final String solution = "";
        return new SolverResult(problem.id, seed, "", solution);
    }

    public SolverResult[] solveAll(int[] seeds) {
        SolverResult[] result = new SolverResult[seeds.length];
        for( int i=0; i<seeds.length; ++i) {
            result[i] = solve(seeds[i]);
        }
        return result;
    }

    public SolverResult[] solveAll() {
        return solveAll(problem.sourceSeeds);
    }
}
