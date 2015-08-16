package finalStates;

import java.util.*;
import java.util.concurrent.*;

/**
 * @Author Sergey Pensov
 */
public class FindFinalStates {
    private final Board board;
    private final Unit[] units;
    private final int currentUnitIndex;
    private int maxAddedScore = 0;
    private int maxKilledLines = 0;
    private boolean killedLinesFulfilled = false;
    private final OptimalUnitPosition startPosition;

    private static Map<Integer,ExecutorService> executorsMap = new HashMap<>();


    public FindFinalStates(Board board, Unit[] units, int currentUnitIndex, final OptimalUnitPosition startPosition) {
        this.board = board;
        this.units = units;
        this.currentUnitIndex = currentUnitIndex;
        this.startPosition = startPosition;
    }

    private static ExecutorService getExecutorService(int countOfThreads) {
        ExecutorService service = executorsMap.get(countOfThreads);
        if( service == null) {
            service = Executors.newFixedThreadPool(countOfThreads);
            executorsMap.put(countOfThreads, service);
        }
        return service;
    }

    public ArrayList<OptimalUnitPosition> getOptimalPositionInMap(int depth, int beamWidth,
                                                                  int linesKilled, int threadCount) throws Exception {
        ArrayList<OptimalUnitPosition> optimalUnitPositions = new ArrayList<>(230);
        maxKilledLines = 0;
        maxAddedScore = 0;
        final Unit unit = units[currentUnitIndex];
        for (int j = 0; j < board.height; j++) {
            for (int i = -4; i < board.width+4; i++) {
                for (int a=0; a<unit.maxAngle; ++a) {
                    final Point testPoint = new Point(i, board.height - j - 1);
                    final UnitState testState = new UnitState(testPoint, a, unit.maxAngle);
                    if (board.isValid(unit, testState)) {
                        int lockCounter = 0;
                        for (int k = 0; k < Command.commands.length; k++) {
                            if (!board.isValid(unit, testState.applyCommand(Command.getCommand(k)))) {
                                lockCounter++;
                                break;
                            }
                        }

                        if (lockCounter != 0) {
                            Board.PosScore posScore = board.getPositionScore(unit, testState);

                            if( posScore.linesKilled > maxKilledLines ) {
                                maxKilledLines = posScore.linesKilled;
                            }

                            if( posScore.addedScore > maxAddedScore ) {
                                maxAddedScore = posScore.addedScore;
                            }

                            optimalUnitPositions.add(new OptimalUnitPosition(testState, posScore, board));
                        }
                    }
                }
            }
        }

        if( optimalUnitPositions.size() == 0 ) return optimalUnitPositions;

        // updating starting position

        if( maxKilledLines >= linesKilled ) {
            // goal fulfilled
            killedLinesFulfilled = true;
            if( startPosition != null ) {
                startPosition.posScore.addedScore += maxAddedScore;
                if (maxKilledLines > startPosition.posScore.linesKilled)
                    startPosition.posScore.linesKilled = maxKilledLines;
            }
            return optimalUnitPositions;
        }

        if( depth > 0 && currentUnitIndex < units.length-1) {

            // sorting positions on heuristic score
            Collections.sort(optimalUnitPositions, (o1, o2) -> o2.score - o1.score);

            final int countOfBestPositions = beamWidth < 0 ?
                    optimalUnitPositions.size() :
                    Math.min(beamWidth, optimalUnitPositions.size());

            ExecutorService threadPool = threadCount > 1 ? Executors.newFixedThreadPool(threadCount) : null;

            List<Future<FindFinalStates>> threadResults = new ArrayList<>();

            final int bestScore = optimalUnitPositions.get(0).score;
            for( int i=0; i<countOfBestPositions; ++i) {
                final OptimalUnitPosition position = optimalUnitPositions.get(i);
                if (position.score * 3 < bestScore) break;

                Callable<FindFinalStates> task = new Callable<FindFinalStates>() {
                    @Override
                    public FindFinalStates call() {
                        Board newBoard = new Board(board);
                        newBoard.updateBoard(unit, position.state);

                        FindFinalStates newFFS = new FindFinalStates(newBoard, units, currentUnitIndex + 1, position);
                        try {
                            newFFS.getOptimalPositionInMap(depth - 1, beamWidth, linesKilled, 1);
                        } catch (Exception ignored) {
                        }
                        return newFFS;
                    }
                };
                if( threadPool != null) {
                    Future<FindFinalStates> f = threadPool.submit(task);
                    threadResults.add(f);
                }
                else {
                    FindFinalStates ff = task.call();
                    if( ff.killedLinesFulfilled) {
                        killedLinesFulfilled = true;
                        if(ff.maxAddedScore > maxAddedScore) maxAddedScore = ff.maxAddedScore;
                        if(ff.maxKilledLines > maxKilledLines) maxKilledLines = ff.maxKilledLines;
                        if( startPosition != null ) {
                            startPosition.posScore.addedScore += ff.maxAddedScore;
                            if (ff.maxKilledLines > startPosition.posScore.linesKilled)
                                startPosition.posScore.linesKilled = ff.maxKilledLines;
                        }
                        break;
                    }
                }

            }

            if( threadPool != null) {
                threadPool.shutdown();
                while (!threadPool.awaitTermination(20, TimeUnit.SECONDS)) {
                    System.out.println("still executing");
                }

                // checking thread results
                for (Future<FindFinalStates> threadResult : threadResults) {
                    FindFinalStates ff = threadResult.get();
                    if (ff != null) {
                        if( ff.killedLinesFulfilled) {
                            killedLinesFulfilled = true;
                            if(ff.maxAddedScore > maxAddedScore) maxAddedScore = ff.maxAddedScore;
                            if(ff.maxKilledLines > maxKilledLines) maxKilledLines = ff.maxKilledLines;
                            if( startPosition != null ) {
                                startPosition.posScore.addedScore += ff.maxAddedScore;
                                if (ff.maxKilledLines > startPosition.posScore.linesKilled)
                                    startPosition.posScore.linesKilled = ff.maxKilledLines;
                            }
                        }
                    }
                }
            }

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

    public boolean isKilledLinesFulfilled() {
        return killedLinesFulfilled;
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
