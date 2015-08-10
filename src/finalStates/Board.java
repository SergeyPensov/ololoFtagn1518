package finalStates;

import java.util.Arrays;

/**
 * Created by kirill on 8/8/2015.
 */
public class Board {

    public static final float R = 1.0f/(float)Math.sqrt(3);
    public static final float DY = R * 1.5f;

    public final int width;
    public final int height;
    private final int[] array;
    public int score=0;
    private int oldLinesKilled=0;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        array = new int[width*height];
    }

    public Board(Board b) {
        width = b.width;
        height = b.height;
        array = Arrays.copyOf(b.array,b.array.length);
        score = b.score;
        oldLinesKilled = b.oldLinesKilled;
    }

    public Board(Unit unit) {
        // min & max
        int maxCx = unit.pivot.x;
        int maxCy = unit.pivot.y;
        for (Point member : unit.members) {
            if( member.x > maxCx ) maxCx = member.x;
            if( member.y > maxCy ) maxCy = member.y;
        }

        width = Math.max(1, maxCx+1);
        height = Math.max(1, maxCy + 1);
        array = new int[width*height];

        setCell(unit.pivot, CellState.PIVOT.getState());
        for (Point member : unit.members) {
            final int state = member.equals(unit.pivot) ? CellState.FILLED_PIVOT.getState() : CellState.FILLED.getState();
            setCell(member,state);
        }
    }

    public static FPoint getCoordsForIndexes(final Point index) {
        float x = index.x;
        if( 1 == (index.y & 1)) x += 0.5f;
        float y = index.y * DY;
        return new FPoint(x, y);
    }

    public static Point getIndexForCoordinates(final FPoint point) {
        int j = Math.round(point.y / DY);
        int i = Math.round((1 == (j & 1)) ? point.x - 0.5f : point.x);
        return new Point(i,j);
    }

    public int readCell( int i, int j) {
        final int index = getIndex(i, j);
        return array[index];
    }

    public void setCell( int i, int j, int value) {
        final int index = getIndex(i, j);
        array[index] = value;
    }

    public void setCell( final Point p, int value) {
        setCell(p.x, p.y, value);
    }

    private int getIndex(int i, int j) {
        if( i < 0 || i >= width || j < 0 || j >= height ) throw new IllegalArgumentException("indexes out of bounds");
        return i + j*width;
    }

    public Unit transform(Unit unit, UnitState state) {

        // todo support all other angles

        Unit result = new Unit();
        result.members = new Point[unit.members.length];

        if( 0 == (state.start.y & 1)) {
            // rendering starting from EVEN row

            int i = unit.pivot.x + state.start.x;
            int j = unit.pivot.y + state.start.y;
            result.pivot = new Point(i,j);

            int index=0;
            for (Point member : unit.members) {
                i = member.x + state.start.x;
                j = member.y + state.start.y;
                result.members[index] = new Point(i,j);
                ++index;
            }
        }
        else {
            // rendering starting from ODD row

            int i = (unit.pivot.y & 1) == 0 ?  unit.pivot.x + state.start.x : unit.pivot.x + state.start.x + 1;
            int j = unit.pivot.y + state.start.y;
            result.pivot = new Point(i,j);

            int index = 0;
            for (Point member : unit.members) {
                i = (member.y & 1) == 0 ?  member.x + state.start.x : member.x + state.start.x + 1;
                j = member.y + state.start.y;
                result.members[index] = new Point(i,j);
                ++index;
            }
        }

        return result;
    }

    public boolean isValid(Unit unit, UnitState state) {
        final Unit transformed = transform(unit, state);
        for (Point member : transformed.members) {
            final int i = member.x;
            final int j = member.y;
            if( i < 0 || i >= width || j >= height || readCell(i,j) != 0 ) return false;
        }
        return true;
    }

    public int updateBoard(Unit unit, UnitState state) {
        final Unit transformed = transform(unit, state);
        for (Point member : transformed.members) {
            final int i = member.x;
            final int j = member.y;
            setCell(i, j, CellState.FILLED.getState());
        }

        int points = unit.members.length;

        // searching for completely filled lines
        int linesKilled = 0;
        for( int y=height-1; y>=0; y--) {
            boolean filled = true;
            for( int x=0; x<width; ++x) {
                if( readCell(x,y) == 0) {
                    filled = false;
                    break;
                }
            }

            if( filled) {
                linesKilled++;
                // shifting all cells down
                for( int y2=y-1; y2>=0; y2--) {
                    for( int x=0; x<width; ++x) {
                        setCell(x,y2+1, readCell(x, y2));
                    }
                }
                y++;
            }
        }

        points += 100 *(1 + linesKilled) * linesKilled / 2;

        int bonus = 0;
        if( oldLinesKilled != 0 ) {
            bonus += ((oldLinesKilled-1) * points / 10);
        }

        final int addedScore = points + bonus;
        score += addedScore;

        oldLinesKilled = linesKilled;
        return addedScore;
    }

    public int getPositionScore(Unit unit, UnitState state, int lockCounter) {

        Board newBoard = new Board(this);
        int addedScore = newBoard.updateBoard(unit, state);

        return ((addedScore<<4) + lockCounter)*2 + state.start.y;
    }

    public UnitState getSpawnState(Unit unit, Board unitBoard) {
        int i = (width - unitBoard.width)/2;
        int j = 0;

        return new UnitState(new Point(i,j), 0);
    }
}
