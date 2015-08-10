package finalStates;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class UnitState {
    public final Point start; // local coordinate system shift
    public final int angle; // 0..5
    public final int width;
    public final int height;
    public final int wh;

    public UnitState(Point start, int angle, int width, int height) {
        this.start = start;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.wh = width * height;
    }

    public UnitState(UnitState state) {
        this.start = state.start;
        this.angle = state.angle;
        this.width = state.width;
        this.height = state.height;
        this.wh = width * height;
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
        return 17 * (start.x + 1) + 2560 * (start.y + 1) + width + angle * wh;
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
