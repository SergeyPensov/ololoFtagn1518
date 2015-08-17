package finalStates;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by kirill.sidorchuk on 8/17/2015.
 */
public class UnitPositionSearcher {

    public static HashSet<UnitState> search(Board board, Unit unit, UnitState startState) {

        HashSet<UnitState> visitedStates = new HashSet<>(board.height*board.width);

        if( !board.isValid(unit, startState)) return null;

        HashSet<UnitState> lockingStates = new HashSet<>(100);

        LinkedList<UnitState> toVisit = new LinkedList<>();
        toVisit.add(startState);
        visitedStates.add(startState);

        while( toVisit.size() != 0 ) {
            UnitState state = toVisit.pop();

            for (Command cmd : Command.commands) {
                final UnitState newState = cmd.apply(state);
                if (newState.angle < unit.maxAngle) {
                    if( !visitedStates.contains(newState)) {
                        visitedStates.add(newState);

                        if (!board.isValid(unit, newState)) {
                            // parent state is locking state
                            lockingStates.add(state);
                        }
                        else {
                            toVisit.add(newState);
                        }
                    }
                }
            }
        }

        // here we have list of lockingStates
        return lockingStates;
    }

}
