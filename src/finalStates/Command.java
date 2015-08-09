package finalStates;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public abstract class Command {
    public abstract String letters();
    public abstract UnitState apply(UnitState s);
}
