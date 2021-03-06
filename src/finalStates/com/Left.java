package finalStates.com;

import finalStates.Command;
import finalStates.Point;
import finalStates.UnitState;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class Left extends Command {

    @Override
    public String letters() {
        return "p'!.03";
    }

    @Override
    public UnitState apply(UnitState s) {
        return new UnitState(new Point(s.start.x-1, s.start.y), s.angle, s.maxAngle);
    }

    @Override
    public String toString() {
        return "Left{}";
    }
}
