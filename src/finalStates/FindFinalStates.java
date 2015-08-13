package finalStates;

import java.util.*;

/**
 * @Author Sergey Pensov
 */
public class FindFinalStates {
    private final Board board;
    private final Unit unit;
    private Unit[] nextUnits;

    public FindFinalStates(Unit unit, Board board, Unit[] nextUnits) {
        this.board = board;
        this.unit = unit;
        this.nextUnits = nextUnits;
    }

    public ArrayList<OptimalUnitPosition> getOptimalPositionInMap(int depth) {
        ArrayList<OptimalUnitPosition> optimalUnitPositions = new ArrayList<>();
        for (int j = 0; j < board.height; j++) {
            for (int i = -3; i < board.width+3; i++) {
                for (int a = 0; a < 5; ++a) {
                    final Point testPoint = new Point(i, board.height - j - 1);
                    final UnitState testState = new UnitState(testPoint, a);
                    if (board.isValid(unit, testState)) {
                        int lockCounter = 0;
                        for (int k = 0; k < Command.commands.length; k++) {
                            if (!board.isValid(unit, testState.applyCommand(Command.getCommand(k)))) {
                                lockCounter++;
                            }
                        }

                        if (lockCounter != 0) {
                            int score = board.getPositionScore(unit, testState, lockCounter);
                            optimalUnitPositions.add(new OptimalUnitPosition(testState, score));
                        }
                    }
                }
            }
        }

        Collections.sort(optimalUnitPositions, (o1, o2) -> o2.score - o1.score);

        if( depth > 0 && nextUnits != null && nextUnits.length != 0) {

            final int countOfBestPositions = Math.min(7, optimalUnitPositions.size());

            for( int i=0; i<countOfBestPositions; ++i) {
                OptimalUnitPosition position = optimalUnitPositions.get(i);

                Board newBoard = new Board(board);
                newBoard.updateBoard(unit, position.state);

                final Unit[] nextNextUnits = nextUnits.length > 1 ? Arrays.copyOfRange(nextUnits, 1, nextUnits.length) : null;
                FindFinalStates newFFS = new FindFinalStates(nextUnits[0], newBoard, nextNextUnits);
                final ArrayList<OptimalUnitPosition> newOptimalPoss = newFFS.getOptimalPositionInMap(depth - 1);

                position.score += newOptimalPoss.get(0).score;
            }

            Collections.sort(optimalUnitPositions, (o1, o2) -> o2.score - o1.score);
        }

        return optimalUnitPositions;
    }

    public ThreeNode getAllPath(OptimalUnitPosition optimalUnitPosition, Unit unit, Board board) {

        ThreeNode parent = new ThreeNode(null, null, board.getSpawnState(unit), board, unit);
        boolean canCreateChild = false;
        LinkedHashMap<UnitState, ThreeNode> nodeHashMap = new LinkedHashMap<>();
        ArrayList<ThreeNode> nextParent = new ArrayList<>();
        ArrayList<ThreeNode> childNodes = new ArrayList<>();
        nextParent.add(parent);
        while (!canCreateChild) {
            int createCounter = 0;
            for (ThreeNode node : nextParent) {
                if (ThreeNode.creatorNextNodes(node, optimalUnitPosition.state, nodeHashMap)) {
                    if (parent.finalState == true) return parent;
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
