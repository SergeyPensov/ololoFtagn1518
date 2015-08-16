package finalStates;

/**
 * @Author Sergey Pensov
 */
public class OptimalUnitPosition {
    public final UnitState state;
    public final Board.PosScore posScore;
    public int score; // heuristic score

    public OptimalUnitPosition(UnitState state, Board.PosScore posScore, int score) {
        this.state = state;
        this.posScore = posScore;
        this.score = score;
    }

    public OptimalUnitPosition(UnitState testState, Board.PosScore posScore, Board board) {
        this.state = testState;
        this.posScore = posScore;
        this.score =
                (200*posScore.depth)/board.height +
                (100*posScore.totalFilledX)/board.width +
                posScore.addedScore;
    }

    @Override
    public String toString() {
        return "OptimalUnitPosition{" +
                "state=" + state +
                ", posScore=" + posScore +
                ", score=" + score +
                '}';
    }
}
