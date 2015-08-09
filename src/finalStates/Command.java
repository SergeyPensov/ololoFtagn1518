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

    public static Command[] commands = {new Left(), new Right(), new SW(), new SE()  };

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
