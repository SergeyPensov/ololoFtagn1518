package finalStates;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class UnitState {
    public final Pivot start; // local coordinate system shift
    public final int angle; // 0..5
    public final int width;
    public final int height;
    public final int wh;

    public UnitState(Pivot start, int angle, int width, int height) {
        this.start = start;
        this.angle = angle;
        this.width = width;
        this.height = height;
        this.wh = width*height;
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
        return start.x + start.y * width + angle * wh;
    }

    @Override
    public int hashCode() {
        return getIndex();
    }

    @Override
    public boolean equals(Object obj) {
        if( obj == null || !(obj instanceof UnitState)) return false;

        UnitState us = (UnitState) obj;
        return getIndex() == us.getIndex();
    }
}
