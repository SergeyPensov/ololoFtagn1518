package finalStates.com;

import finalStates.Command;
import finalStates.UnitState;

/**
 * Created by kirill.sidorchuk on 8/10/2015.
 */
public class CCW extends Command {

    @Override
    public String letters() {
        return "kstuwx";
    }

    @Override
    public UnitState apply(UnitState s) {
        return new UnitState(s.start, (s.angle+5)%6, s.maxAngle);
    }

    @Override
    public String toString() {
        return "CCW{}";
    }
}
