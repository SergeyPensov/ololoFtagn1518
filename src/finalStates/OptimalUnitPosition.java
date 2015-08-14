package finalStates;

/**
 * @Author Sergey Pensov
 */
public class OptimalUnitPosition {
    public final UnitState state;
    public final Board.PosScore posScore;
    public int score;

    public OptimalUnitPosition(UnitState state, Board.PosScore posScore, int score) {
        this.state = state;
        this.posScore = posScore;
        this.score = score;
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
