package finalStates;

/**
 * @Author Sergey Pensov
 */
public class OptimalUnitPosition {
    public final UnitState state;
    public final Board.PosScore posScore;
    public int score; // heuristic score
    public OptimalUnitPosition next = null;

    public OptimalUnitPosition(UnitState state, Board.PosScore posScore, int score) {
        this.state = state;
        this.posScore = posScore;
        this.score = score;
    }

    public OptimalUnitPosition(UnitState testState, Board.PosScore posScore, Board board) {
        this.state = testState;
        this.posScore = posScore;

        if( posScore.depth > board.height/2 && posScore.linesKilled != 0) {
            // deep place -> avoiding burning lines too soon to get better score with multiple lines burnt
            this.score = posScore.depth;// (300 * posScore.depth) / board.height + (10 * posScore.filledToGap) / board.width - posScore.gameScore;
        }
        else {
            this.score =
                    (300 * posScore.depth) / board.height + (10 * posScore.filledToGap) / board.width + posScore.gameScore;
        }
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
