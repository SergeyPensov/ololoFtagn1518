package finalStates;

import java.util.*;

/**
 * @Author Sergey Pensov
 */
public class FindFinalStates {
    private final Board board;
    private final Unit unit;

    public FindFinalStates(Unit unit, Board board, Unit[] nextUnits) {
        this.board = board;
        this.unit = unit;
    }

    public ArrayList<OptimalUnitPosition> getOptimalPositionInMap() {
        ArrayList<OptimalUnitPosition> optimalUnitPositions = new ArrayList<>();
        for (int j = 0; j < board.height; j++) {
            for (int i = 0; i < board.width; i++) {
                Point testPoint = new Point(i, board.height - j - 1);
                UnitState testState = new UnitState(testPoint, 0);
                if (board.isValid(unit, testState)) {
                    int lockCounter = 0;
                    for (int k = 0; k < Command.commands.length; k++) {
                        UnitState state = testState.applyCommand(Command.getCommand(k));
                        if (!board.isValid(unit, state)) {
                            lockCounter++;
                        }
                    }
                    if (lockCounter != 0)
                        optimalUnitPositions.add(new OptimalUnitPosition(new UnitState(testState), lockCounter));

                }
            }
        }
        Collections.sort(optimalUnitPositions, (o1, o2) -> o2.lockCounter - o1.lockCounter);
        return optimalUnitPositions;
    }

    public ThreeNode getAllPath(OptimalUnitPosition optimalUnitPosition, Unit unit, Board board, Board unitBoard) {

        ThreeNode parent = new ThreeNode(null, null, board.getSpawnState(unit, unitBoard), board, unit);
        boolean canCreateChild = false;
        LinkedHashMap<UnitState, ThreeNode> nodeHashMap = new LinkedHashMap<>();
        ArrayList<ThreeNode> nextParent = new ArrayList<>();
        ArrayList<ThreeNode> childNodes = new ArrayList<>();
        nextParent.add(parent);
        while (!canCreateChild) {
            int createCounter = 0;
            for (ThreeNode node : nextParent) {
                if (ThreeNode.creatorNextNodes(node, optimalUnitPosition.state, nodeHashMap)) {
                    createCounter++;
                    for (Command key : node.child.keySet()) {
                        childNodes.add(node.child.get(key));
                    }
                }
            }
            canCreateChild = createCounter == 0;
            if (!canCreateChild) {
                nextParent.clear();
                nextParent.addAll(childNodes);
                childNodes.clear();
            }
        }
        System.out.println(nodeHashMap.size());
        System.out.println(nodeHashMap.keySet().toString());
        if (parent.finalState != false) return parent;
        return null;
    }

    public static ArrayList<ThreeNode> getShortPath(ThreeNode node) {
        ArrayList<ThreeNode> nodes = new ArrayList<>();

        while (node.parent != null) {
            nodes.add(new ThreeNode(node));
            node = node.parent;
        }

        return nodes;
    }


}
