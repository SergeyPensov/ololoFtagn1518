package finalStates;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class UnitState {
    public final Point start; // local coordinate system shift
    public final int angle; // 0..5
    private static final int width = 10000;
    private static final int height = 10000;
    private final int wh = width * height;

    public UnitState(Point start, int angle) {
        this.start = start;
        this.angle = angle;
    }

    public UnitState(UnitState state) {
        this.start = state.start;
        this.angle = state.angle;
    }

    public UnitState applyCommand(Command c) {
        return c.apply(this);
    }

    @Override
    public String toString() {
        return "UnitState{" +
                "start=" + start +
                ", angle=" + angle +
                '}';
    }

    // todo take symmetry into account when calculating index
    public int getIndex() {
        return start.x + width * start.y + angle * wh;
    }

    @Override
    public int hashCode() {
        return getIndex();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof UnitState)) return false;

        UnitState us = (UnitState) obj;
        return (start.x == us.start.x) && (start.y == us.start.y) && (angle == us.angle);
    }
}
