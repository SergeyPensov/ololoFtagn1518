package finalStates;

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
            if( !visitedStates.contains(state)) {
                visitedStates.add(state);

                // trying all transitions
                for (Command cmd : Command.commands) {
                    final UnitState newState = cmd.apply(state);

                    if ( invalidStates.contains(newState) || !board.isValid(unit, newState)) {
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

}
