package finalStates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by kirill.sidorchuk on 8/17/2015.
 */
public class UnitPositionSearcher {

    public static HashSet<UnitState> search(Board board, Unit unit, UnitState startState) {

        HashSet<UnitState> visitedStates = new HashSet<>(board.height * board.width);

        if (!board.isValid(unit, startState)) return null;

        HashSet<UnitState> lockingStates = new HashSet<>(100);

        LinkedList<UnitState> toVisit = new LinkedList<>();
        toVisit.add(startState);

        HashSet<UnitState> invalidStates = new HashSet<>(board.height * board.width);

        int nIterations = 0;
        while (toVisit.size() != 0) {
            nIterations++;

            UnitState state = toVisit.pop();
            if (!visitedStates.contains(state)) {
                visitedStates.add(state);

                // trying all transitions
                for (Command cmd : Command.commands) {
                    final UnitState newState = cmd.apply(state);

                    if (invalidStates.contains(newState) || !board.isValid(unit, newState)) {
                        // parent state is locking state
                        invalidStates.add(newState);
                        lockingStates.add(state);
                    } else {
                        toVisit.add(newState);
                    }
                }
            }
        }

        // here we have list of lockingStates
        return lockingStates;
    }

    private static class Node {
        private final Node parent;
        private final Command command;
        private final UnitState state;

        public Node(Command command, Node parent, UnitState state) {
            this.command = command;
            this.parent = parent;
            this.state = state;
        }
    }

    public static ArrayList<Command> searchPath(Board board, Unit unit, UnitState startState, UnitState targetState) {

        if (startState.equals(targetState)) throw new RuntimeException("start and target states are the same");

        HashSet<UnitState> visitedStates = new HashSet<>(board.height * board.width);

        if (!board.isValid(unit, startState)) throw new RuntimeException("start state is invalid for the board");

        LinkedList<Node> toVisit = new LinkedList<>();
        Node parent = new Node(null, null, startState);

        toVisit.add(parent);

        HashSet<UnitState> invalidStates = new HashSet<>(board.height * board.width);

        Node targetNode = null;
        int nIterations = 0;
        while (toVisit.size() != 0) {
            nIterations++;

            final Node node = toVisit.pop();
            UnitState state = node.state;
            if (state.equals(targetState)) {
                targetNode = node;
                break;
            }

            visitedStates.add(state);

            // trying all transitions
            for (Command cmd : Command.commands) {
                final UnitState newState = cmd.apply(state);

                if (!visitedStates.contains(newState) && !invalidStates.contains(newState)) {

                    if (!board.isValid(unit, newState)) {
                        invalidStates.add(newState);
                    } else {
                        Node nextNode = new Node(cmd, node, newState);
                        toVisit.add(nextNode);
                    }
                }
            }
        }

        if( targetNode == null ) return null; // path not found

        ArrayList<Command> commands = new ArrayList<>(20);
        while(targetNode != null) {
            if(targetNode.command!=null) commands.add(targetNode.command);
            targetNode = targetNode.parent;
        }

        final int size = commands.size();
        ArrayList<Command> reversed = new ArrayList<>(size);
        for( int i=size-1; i>=0; --i) {
            reversed.add(commands.get(i));
        }

        return reversed;
    }
}
