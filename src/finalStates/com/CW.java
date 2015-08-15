package finalStates.com;

import finalStates.Command;
import finalStates.UnitState;

/**
 * Created by kirill.sidorchuk on 8/10/2015.
 */
public class CW extends Command {

    @Override
    public String letters() {
        return "dqrvz1";
    }

    @Override
    public UnitState apply(UnitState s) {
        return new UnitState(s.start, (s.angle+1) % 6, s.maxAngle);
    }

    @Override
    public String toString() {
        return "CW{}";
    }
}
