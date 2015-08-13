package finalStates;

/**
 * @Author Sergey Pensov
 */
public class OptimalUnitPosition {
    public final UnitState state;
    public int score;

    public OptimalUnitPosition(UnitState state, int score) {
        this.state = state;
        this.score = score;
    }

    @Override
    public String toString() {
        return "OptimalUnitPosition{" +
                "state=" + state +
                ", score=" + score +
                '}';
    }
}
