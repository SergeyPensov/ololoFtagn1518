package finalStates;

/**
 * @Author Sergey Pensov
 */
public class Sprite {
    private int[][] spriteArr;
    private int width;
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getSpriteArr() {
        return spriteArr;
    }

    public Sprite(Unit unit) {
        int maxX = -1;
        int minX = -1;
        int maxY = -1;
        int minY = -1;
        for (Point member : unit.members) {
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
        height = maxY - minY + 1;
        width = maxX - minX + 1;
        spriteArr = new int[width][height];
        for (Point member : unit.members) {
            spriteArr[member.getX()][member.getY()] = Constants.MEMBER_NUMBER;
        }
        if (spriteArr[unit.pivot.getX()][unit.pivot.getY()] == Constants.HOLE_NUMBER) {
            spriteArr[unit.pivot.getX()][unit.pivot.getY()] = Constants.PIVOT_NUMBER;
        } else if (spriteArr[unit.pivot.getX()][unit.pivot.getY()] == Constants.MEMBER_NUMBER) {
            spriteArr[unit.pivot.getX()][unit.pivot.getY()] = Constants.PIVOT_IN_MEMBER_NUMBER;
        }

        throw new IllegalArgumentException("indexes out of bounds");
    }
}
