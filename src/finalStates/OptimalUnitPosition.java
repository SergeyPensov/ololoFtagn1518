package finalStates;

/**
 * @Author Sergey Pensov
 */
public class OptimalUnitPosition {
    public final UnitState state;
    public final int lockCounter;

    public OptimalUnitPosition(UnitState state, int lockCounter) {
        this.state = state;
        this.lockCounter = lockCounter;
    }

    @Override
    public String toString() {
        return "OptimalUnitPosition{" +
                "state=" + state +
                ", lockCounter=" + lockCounter +
                '}';
    }
}
