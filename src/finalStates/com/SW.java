package finalStates.com;

import finalStates.Command;
import finalStates.Pivot;
import finalStates.UnitState;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public class SW extends Command {

    @Override
    public String letters() {
        return "aghij4";
    }

    @Override
    public UnitState apply(UnitState s) {
        return new UnitState(new Pivot(s.start.x-1, s.start.y+1), s.angle, s.width, s.height );
    }
}