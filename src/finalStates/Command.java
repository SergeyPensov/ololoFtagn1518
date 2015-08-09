package finalStates;

import finalStates.com.Left;
import finalStates.com.Right;
import finalStates.com.SE;
import finalStates.com.SW;

import java.util.List;

/**
 * Created by kirill.sidorchuk on 8/9/2015.
 */
public abstract class Command {
    public abstract String letters();
    public abstract UnitState apply(UnitState s);

    public static final Command C_LEFT = new Left();
    public static final Command C_RIGHT = new Right();
    public static final Command C_SW = new SW();
    public static final Command C_SE = new SE();

    public static Command[] commands = {C_LEFT, C_RIGHT, C_SW, C_SE };

    public static Command getCommand(int index) {
        return commands[index];
    }

    public static String encode(List<Command> list) {
        StringBuilder sb = new StringBuilder(list.size());

        for (Command command : list) {
            sb.append(command.letters().charAt(0));
        }

        return sb.toString();
    }
}
