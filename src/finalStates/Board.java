package finalStates;

import java.util.Arrays;

/**
 * Created by kirill on 8/8/2015.
 */
public class Board {

    public static final float R = 1.0f/(float)Math.sqrt(3);
    public static final float DY = R * 1.5f;

    public int width;
    public int height;
    private int[] array;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        array = new int[width*height];
    }

    public Board(Board b) {
        width = b.width;
        height = b.height;
        array = Arrays.copyOf(b.array,b.array.length);
    }

    public Board(Unit unit) {
        // min & max
        Pivot maxC = new Pivot(unit.pivot);
        for (Pivot member : unit.members) {
            if( member.x > maxC.x ) maxC.x = member.x;
            if( member.y > maxC.y ) maxC.y = member.y;
        }

        width = Math.max(1, maxC.x+1);
        height = Math.max(1, maxC.y+1);
        array = new int[width*height];

        setCell(unit.pivot, CellState.PIVOT.getState());
        for (Pivot member : unit.members) {
            final int state = member.equals(unit.pivot) ? CellState.FILLED_PIVOT.getState() : CellState.FILLED.getState();
            setCell(member,state);
        }
    }

    public static FPoint getCoordsForIndexes(final Pivot index) {
        float x = index.x;
        if( 1 == (index.y & 1)) x += 0.5f;
        float y = index.y * DY;
        return new FPoint(x, y);
    }

    public static Pivot getIndexForCoordinates(final FPoint point) {
        int j = Math.round(point.y / DY);
        int i = Math.round( (1==(j&1)) ? point.x-0.5f : point.x);
        return new Pivot(i,j);
    }

    public int readCell( int i, int j) {
        final int index = getIndex(i, j);
        return array[index];
    }

    public void setCell( int i, int j, int value) {
        final int index = getIndex(i, j);
        array[index] = value;
    }

    public void setCell( final Pivot p, int value) {
        setCell(p.x, p.y, value);
    }

    private int getIndex(int i, int j) {
        if( i < 0 || i >= width || j < 0 || j >= height ) throw new IllegalArgumentException("indexes out of bounds");
        return i + j*width;
    }

    public boolean isValid(Unit unit, Pivot start, int angle) {
        // todo support all other angles
        if( 0 == (start.y & 1)) {
            // rendering starting from EVEN row
            for (Pivot member : unit.members) {
                int i = member.x + start.x;
                int j = member.y + start.y;
                if( i < 0 || i >= width || j >= height || readCell(i,j) != 0 ) return false;
            }
        }
        else {
            // rendering starting from ODD row
            for (Pivot member : unit.members) {
                int i = (member.y & 1) == 0 ?  member.x + start.x : member.x + start.x + 1;
                int j = member.y + start.y;
                if( i < 0 || i >= width || j >= height || readCell(i,j) != 0 ) return false;
            }
        }
        return true;
    }

    public boolean updateBoard(Unit unit, Pivot start, int angle) {
        // todo support all other angles
        if( 0 == (start.y & 1)) {
            // rendering starting from EVEN row
            for (Pivot member : unit.members) {
                int i = member.x + start.x;
                int j = member.y + start.y;
                setCell(i,j,CellState.FILLED.getState());
            }
        }
        else {
            // rendering starting from ODD row
            for (Pivot member : unit.members) {
                int i = (member.y & 1) == 0 ?  member.x + start.x : member.x + start.x + 1;
                int j = member.y + start.y;
                setCell(i,j,CellState.FILLED.getState());
            }
        }
        return true;
    }

    public UnitState getSpawnState(Unit unit, Board unitBoard) {
        int i = (width - unitBoard.width)/2;
        int j = 0;

        return new UnitState(new Pivot(i,j), 0, width, height );
    }
}
