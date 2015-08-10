package finalStates;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kirill on 8/8/2015.
 */
public class Board {

    public static final float R = 1.0f / (float) Math.sqrt(3);
    public static final float DY = R * 1.5f;

    public final int width;
    public final int height;
    private final int[] array;
    public int score = 0;
    private int oldLinesKilled = 0;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        array = new int[width * height];
    }

    public Board(Board b) {
        width = b.width;
        height = b.height;
        array = Arrays.copyOf(b.array, b.array.length);
        score = b.score;
        oldLinesKilled = b.oldLinesKilled;
    }

    public Board(Unit unit) {
        // min & max
        int maxCx = unit.pivot.x;
        int maxCy = unit.pivot.y;
        for (Point member : unit.members) {
            if (member.x > maxCx) maxCx = member.x;
            if (member.y > maxCy) maxCy = member.y;
        }

        width = Math.max(1, maxCx + 1);
        height = Math.max(1, maxCy + 1);
        array = new int[width * height];

        setCell(unit.pivot, CellState.PIVOT.getState());
        for (Point member : unit.members) {
            final int state = member.equals(unit.pivot) ? CellState.FILLED_PIVOT.getState() : CellState.FILLED.getState();
            setCell(member, state);
        }
    }

    public static FPoint getCoordsForIndexes(final Point index) {
        float x = index.x;
        if (1 == (index.y & 1)) x += 0.5f;
        float y = index.y * DY;
        return new FPoint(x, y);
    }

    public static Point getIndexForCoordinates(final FPoint point) {
        int j = Math.round(point.y / DY);
        int i = Math.round((1 == (j & 1)) ? point.x - 0.5f : point.x);
        return new Point(i, j);
    }

    public int readCell(int i, int j) {
        final int index = getIndex(i, j);
        return array[index];
    }

    public void setCell(int i, int j, int value) {
        final int index = getIndex(i, j);
        array[index] = value;
    }

    public void setCell(final Point p, int value) {
        setCell(p.x, p.y, value);
    }

    private int getIndex(int i, int j) {
        if (i < 0 || i >= width || j < 0 || j >= height) throw new IllegalArgumentException("indexes out of bounds");
        return i + j * width;
    }
/*
    private Unit rotate(Unit unit, int angle) {

        Unit result = new Unit();
        result.pivot = unit.pivot;
        result.members = new Point[unit.members.length];

        float pivot_x_d = (float) (unit.pivot.x + (unit.pivot.y % 2 == 0 ? 0 : 0.5));
        float pivot_y_d = (float) (unit.pivot.y * Math.sqrt(3) / 2);

        final float alpha = (float) (Math.PI / 3) * angle;

        for (int i = 0; i < unit.members.length; i++) {
            float hex_x_d = (float) (unit.members[i].x + ((unit.members[i].y % 2 == 0) ? 0 : 0.5));
            float hex_y_d = (float) (unit.members[i].y * Math.sqrt(3) / 2);

            float rlx = hex_x_d - pivot_x_d;
            float rly = hex_y_d - pivot_y_d;
            float rl2x = (float) (rlx * Math.cos(alpha) - rly * Math.sin(alpha));
            float rl2y = (float) (rlx * Math.sin(alpha) + rly * Math.cos(alpha));
            rl2x += pivot_x_d;
            rl2y += pivot_y_d;

            float hex_y2 = (float) (2 * rl2y / Math.sqrt(3));
            float hex_x2 = (float) (rl2x - ((hex_y2 % 2) == 0 ? 0 : 0.5));

            result.members[i] = new Point(Math.round(hex_x2), Math.round(hex_y2));
        }

        return result;
    }
*/

    private Unit rotate(Unit unit, int angle) {

        Unit result = new Unit();
        result.pivot = unit.pivot;
        result.members = new Point[unit.members.length];

        FPoint fPivot = getCoordsForIndexes(unit.pivot);

        final float alpha = (float) (Math.PI / 3) * angle;
        final float cosA = (float) Math.cos(alpha);
        final float sinA = (float) Math.sin(alpha);

        for (int i = 0; i < unit.members.length; i++) {

            FPoint fPoint = getCoordsForIndexes(unit.members[i]);

            fPoint.x -= fPivot.x;
            fPoint.y -= fPivot.y;

            final float rl2x = fPoint.x * cosA - fPoint.y * sinA + fPivot.x;
            final float rl2y = fPoint.x * sinA + fPoint.y * cosA + fPivot.y;

            Point newHex = getIndexForCoordinates(new FPoint(rl2x, rl2y));

            result.members[i] = newHex;
        }

        return result;
    }

    public Unit transform(Unit unit, UnitState state) {

        // rotating
        unit = rotate(unit, state.angle);

        Unit result = new Unit();
        result.members = new Point[unit.members.length];

        if (0 == (state.start.y & 1)) {
            // rendering starting from EVEN row

            int i = unit.pivot.x + state.start.x;
            int j = unit.pivot.y + state.start.y;
            result.pivot = new Point(i, j);

            int index = 0;
            for (Point member : unit.members) {
                i = member.x + state.start.x;
                j = member.y + state.start.y;
                result.members[index] = new Point(i, j);
                ++index;
            }
        } else {
            // rendering starting from ODD row

            int i = (unit.pivot.y & 1) == 0 ? unit.pivot.x + state.start.x : unit.pivot.x + state.start.x + 1;
            int j = unit.pivot.y + state.start.y;
            result.pivot = new Point(i, j);

            int index = 0;
            for (Point member : unit.members) {
                i = (member.y & 1) == 0 ? member.x + state.start.x : member.x + state.start.x + 1;
                j = member.y + state.start.y;
                result.members[index] = new Point(i, j);
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
            if (i < 0 || i >= width || j >= height || j < 0 || readCell(i, j) != 0) return false;
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
        for (int y = height - 1; y >= 0; y--) {
            boolean filled = true;
            for (int x = 0; x < width; ++x) {
                if (readCell(x, y) == 0) {
                    filled = false;
                    break;
                }
            }

            if (filled) {
                linesKilled++;
                // shifting all cells down
                for (int y2 = y - 1; y2 >= 0; y2--) {
                    for (int x = 0; x < width; ++x) {
                        setCell(x, y2 + 1, readCell(x, y2));
                    }
                }
                y++;
            }
        }

        points += 100 * (1 + linesKilled) * linesKilled / 2;

        int bonus = 0;
        if (oldLinesKilled != 0) {
            bonus += ((oldLinesKilled - 1) * points / 10);
        }

        final int addedScore = points + bonus;
        score += addedScore;

        oldLinesKilled = linesKilled;
        return addedScore;
    }

    public int getPositionScore(Unit unit, UnitState state, int lockCounter) {

        final Unit transformed = transform(unit, state);
        for (Point member : transformed.members) {
            final int i = member.x;
            final int j = member.y;
            setCell(i, j, CellState.FILLED.getState());
        }

        Set<Integer> checkedY = new HashSet<>(unit.members.length);

        int linesKilled = 0;
        for (Point member : transformed.members) {
            if (checkedY.contains(member.y) == false) {
                checkedY.add(member.y);

                boolean filled = true;
                for (int x = 0; x < width; ++x) {
                    if (readCell(x, member.y) == 0) {
                        filled = false;
                        break;
                    }
                }
                if (filled) linesKilled++;
            }
        }

        // cleaning
        for (Point member : transformed.members) {
            final int i = member.x;
            final int j = member.y;
            setCell(i, j, CellState.FREE.getState());
        }

        int points = unit.members.length;
        points += 100 * (1 + linesKilled) * linesKilled / 2;

        int bonus = 0;
        if (oldLinesKilled != 0) {
            bonus += ((oldLinesKilled - 1) * points / 10);
        }

        final int addedScore = points + bonus;

        return ((addedScore << 4) + lockCounter) * 3 + state.start.y * 2;
    }

    public UnitState getSpawnState(Unit unit, Board unitBoard) {
        int i = (width - unitBoard.width) / 2;
        int j = 0;

        return new UnitState(new Point(i, j), 0);
    }
}
