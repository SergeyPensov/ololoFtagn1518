package finalStates;

import finalStates.com.*;

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
    public static final Command C_CW = new CW();
    public static final Command C_CCW = new CCW();

    public static Command[] commands = {C_LEFT, C_RIGHT, C_SW, C_SE, C_CW, C_CCW };

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
