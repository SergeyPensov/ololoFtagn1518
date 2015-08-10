package finalStates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @Author Sergey Pensov
 */
public class ThreeNode {
    public final ThreeNode genParent;
    public final ThreeNode parent;
    public final Command command;
    public final HashMap<Command, ThreeNode> child = new HashMap<>();
    public final UnitState state;
    public final Board board;
    public final Unit unit;
    public boolean finalState = false;
    public final ArrayList<ThreeNode> subParent = new ArrayList<>();
    public ThreeNode finalThreeNode;

    public ThreeNode(ThreeNode genParent, ThreeNode parent, Command command, UnitState state, Board board, Unit unit) {
        this.parent = parent;
        this.command = command;
        this.state = state;
        this.board = board;
        this.unit = unit;
        this.genParent = genParent;
    }

    public ThreeNode(ThreeNode parent, Command command, UnitState state, Board board, Unit unit) {
        this.parent = parent;
        this.command = command;
        this.state = state;
        this.board = board;
        this.unit = unit;
        this.genParent = this;
    }

    public ThreeNode(ThreeNode node) {
        this.parent = node.parent;
        this.command = node.command;
        this.state = node.state;
        this.board = node.board;
        this.unit = node.unit;
        this.genParent = node.genParent;
        this.finalState = node.finalState;
        this.subParent.addAll(node.subParent);
    }

    public static void removeChild(ThreeNode node) {
        if (node.child.size() != 0) return;
        if (node.parent == null) return;
        while (node.parent.child.size() == 1) {
            node.parent.child.remove(node.command);
            node = node.parent;
        }
    }

    public static boolean createChildNode(ThreeNode parent, Command command, UnitState childState, Board board, Unit unit, UnitState finalState, LinkedHashMap<UnitState, ThreeNode> nodeHashMap) {
        if ((parent.command == Command.C_LEFT && command == Command.C_RIGHT) || (parent.command == Command.C_RIGHT && command == Command.C_LEFT)) {
            removeChild(parent);
            return false;
        }
        if (nodeHashMap.containsKey(childState)) {
            parent.child.put(command, nodeHashMap.get(childState));
            nodeHashMap.get(childState).subParent.add(parent);
            return false;
        }
        if (canCreateChild(childState, board, unit)) {
            ThreeNode child = new ThreeNode(parent.genParent, parent, command, childState, board, unit);
            parent.child.put(command, child);
            nodeHashMap.put(childState, child);
            if (childState.equals(finalState)) {
                child.finalState = true;
                parent.genParent.finalState = true;
                parent.genParent.finalThreeNode = child;
                return false;
            }
            return true;
        }
        removeChild(parent);
        return false;
    }


    public static boolean canCreateChild(UnitState childState, Board board, Unit unit) {
        return board.isValid(unit, childState);
    }

    public static boolean creatorNextNodes(ThreeNode parent, UnitState finalState, LinkedHashMap<UnitState, ThreeNode> nodeHashMap) {
        int createCounter = 0;
        for (int i = 0; i < Command.commands.length; i++) {
            if (createChildNode(parent, Command.getCommand(i), parent.state.applyCommand(Command.getCommand(i)), parent.board, parent.unit, finalState, nodeHashMap)) {
                createCounter++;
            }
        }
        return createCounter != 0;
    }

    @Override
    public String toString() {
        return "ThreeNode{" +
                "state=" + state +
                '}';
    }
}