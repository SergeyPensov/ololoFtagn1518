package finalStates;

/**
 * @Author Sergey Pensov
 */
public class Sprite {
    private int[][] spriteArr;
    private Pivot pivot;

    public int[][] getSpriteArr() {
        return spriteArr;
    }

    public Sprite(int[][] spriteArr, Pivot pivot) {
        this.spriteArr = spriteArr;
        this.pivot = pivot;
    }
}
