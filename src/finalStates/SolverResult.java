package finalStates;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class SolverResult {
    public int problemId;
    public int seed;
    public String tag;
    public String solution;

    public SolverResult(int problemId, int seed, String tag, String solution) {
        this.problemId = problemId;
        this.seed = seed;
        this.tag = tag;
        this.solution = solution;
    }
}
