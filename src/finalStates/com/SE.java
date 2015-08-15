package finalStates.com;

import finalStates.Command;
import finalStates.Point;
import finalStates.UnitState;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class SE extends Command {

    @Override
    public String letters() {
        return "lmno 5";
    }

    @Override
    public UnitState apply(UnitState s) {
        final int newX = 0 == (s.start.y & 1) ? s.start.x : s.start.x + 1;
        return new UnitState(new Point(newX, s.start.y+1), s.angle, s.maxAngle);
    }

    @Override
    public String toString() {
        return "SE{}";
    }
}
