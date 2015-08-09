import finalStates.*;

import java.util.ArrayList;

/**
 * @Author Sergey Pensov
 */
public class FindFinalStates {
    private Board board;
    private Unit unit;

    public FindFinalStates(Unit unit, Board board) {
        this.board = board;
        this.unit = unit;
    }

    public UnitState[] getOptimalPositionInMap() {
        ArrayList<UnitState> unitStates = new ArrayList<>();
        for (int i = board.height; 0 < i; i--) {
         /*   if (board.isValid(unit,new Uni))*/
        }
        return null;
    }


}
