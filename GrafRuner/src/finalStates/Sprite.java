package finalStates;

/**
 * @Author Sergey Pensov
 */
public class Sprite {
    private int[][] spriteArr;
    private static final int MEMBER_NUMBER = 1;
    private static final int PIVOT_NUMBER = 2;
    private static final int PIVOT_IN_MEMBER_NUMBER = 3;
    private static final int HOLE_NUMBER = 0;

    public int[][] getSpriteArr() {
        return spriteArr;
    }

    public Sprite(Unit unit) {
        int maxX = -1;
        int minX = -1;
        int maxY = -1;
        int minY = -1;
        for (Pivot member : unit.members) {
            if (member.getX() > maxX)
                maxX = member.getX();
            else if (member.getX() < minX)
                minX = member.getX();

            if (member.getY() > maxY)
                maxY = member.getY();
            else if (member.getY() < minY)
                minY = member.getY();
        }

        if (unit.pivot.getX() > maxX)
            maxX = unit.pivot.getX();
        else if (unit.pivot.getX() < minX)
            minX = unit.pivot.getX();

        if (unit.pivot.getY() > maxY)
            maxY = unit.pivot.getY();
        else if (unit.pivot.getY() < minY)
            minY = unit.pivot.getY();

        spriteArr = new int[maxX - minX][maxY - minY];
        for (Pivot member : unit.members) {
            spriteArr[member.getX()][member.getY()] = MEMBER_NUMBER;
        }
        if (spriteArr[unit.pivot.getX()][unit.pivot.getY()] == HOLE_NUMBER) {
            spriteArr[unit.pivot.getX()][unit.pivot.getY()] = PIVOT_NUMBER;
        } else {
            spriteArr[unit.pivot.getX()][unit.pivot.getY()] = PIVOT_IN_MEMBER_NUMBER;
        }
    }
}
