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
    private int maxScore = 0;
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

    public ArrayList<OptimalUnitPosition> getOptimalPositionInMap(final int depth, final int beamWidth,
                                                                  final int linesKilled, final int threadCount) throws Exception {

        final Unit unit = units[currentUnitIndex];
        final HashSet<UnitState> lockingStates = UnitPositionSearcher.search(board, unit, board.getSpawnState(unit));

        if( lockingStates == null || lockingStates.size() == 0) return new ArrayList<>();

        final ArrayList<OptimalUnitPosition> optimalUnitPositions = new ArrayList<>(lockingStates.size());

        maxKilledLines = 0;
        maxScore = 0;
        for (UnitState lockingState : lockingStates) {
            Board.PosScore posScore = board.getPositionScore(unit, lockingState);
            final OptimalUnitPosition position = new OptimalUnitPosition(lockingState, posScore, board);

            if (posScore.linesKilled > maxKilledLines) {
                maxKilledLines = posScore.linesKilled;
            }

            if( posScore.linesKilled >= linesKilled && startPosition != null) {
                startPosition.next = position;
            }

            if (posScore.gameScore > maxScore) {
                maxScore = posScore.gameScore;
            }

            optimalUnitPositions.add(position);
        }

        // updating starting position

        if( maxKilledLines >= linesKilled ) {
            // goal fulfilled
            killedLinesFulfilled = true;
            return optimalUnitPositions;
        }

        // can't find position to fulfill goal with this unit
        // searching for best unit position based on possible future rewards

        if( depth > 0 && currentUnitIndex < units.length-1) {

            // sorting positions on heuristic score
            Collections.sort(optimalUnitPositions, (o1, o2) -> o2.score - o1.score);

            final int countOfBestPositions = beamWidth <= 0 ?
                    optimalUnitPositions.size() :
                    Math.min(beamWidth, optimalUnitPositions.size());

            ExecutorService threadPool = threadCount > 1 ? Executors.newFixedThreadPool(threadCount) : null;

            List<Future<FindFinalStates>> threadResults = new ArrayList<>();

//            final int bestScore = optimalUnitPositions.get(0).score;
            for( int i=0; i<countOfBestPositions; ++i) {
                final OptimalUnitPosition position = optimalUnitPositions.get(i);
//                if (position.score * 3 < bestScore) break;

                Callable<FindFinalStates> task = () -> {

                    // updating the board with this position
                    Board newBoard = new Board(board);
                    newBoard.updateBoard(unit, position.state);

                    // calling search
                    FindFinalStates newFFS = new FindFinalStates(newBoard, units, currentUnitIndex + 1, position);
                    try {
                        final int newBeamWidth = beamWidth < 3 ? beamWidth : beamWidth-1;
                        newFFS.getOptimalPositionInMap(depth - 1, newBeamWidth, linesKilled, 1);
                    } catch (Exception ignored) {
                    }
                    return newFFS;
                };

                if( threadPool != null) {
                    // submitting to thread pool
                    threadResults.add(threadPool.submit(task));
                }
                else {
                    // calling in this thread
                    threadResults.add(new CompletedFuture<FindFinalStates>(task.call()));
                }

            }

            // waiting for all task to complete
            if( threadPool != null) {
                threadPool.shutdown();
                while (!threadPool.awaitTermination(20, TimeUnit.SECONDS)) {
                    System.out.println("still executing..");
                }
            }

            // checking thread results and updating positions with best added score
            for (Future<FindFinalStates> threadResult : threadResults) {
                FindFinalStates ff = threadResult.get();
                if (ff != null) {
                    if( ff.killedLinesFulfilled) {
                        killedLinesFulfilled = true;
                        if( startPosition != null ) startPosition.next = ff.startPosition;
                    }

                    if(ff.maxScore > maxScore) maxScore = ff.maxScore;
                    if(ff.maxKilledLines > maxKilledLines) maxKilledLines = ff.maxKilledLines;
                    ff.startPosition.posScore.gameScore = maxScore;
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
