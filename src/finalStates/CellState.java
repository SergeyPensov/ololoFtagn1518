package finalStates;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public enum CellState {
    FREE(0),
    FILLED(1),
    PIVOT(2),
    FILLED_PIVOT(3);

    private final int state;

    CellState(int _i) {
        state = _i;
    }

    public int getState() {
        return state;
    }
}
