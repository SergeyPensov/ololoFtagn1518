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
        return new UnitState(new Point(s.start.x, s.start.y+1), s.angle, s.width, s.height );
    }

    @Override
    public String toString() {
        return "SE{}";
    }
}
