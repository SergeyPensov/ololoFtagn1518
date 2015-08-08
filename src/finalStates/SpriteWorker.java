package finalStates;

/**
 * @Author Sergey Pensov
 */
public class SpriteWorker {
    private Sprite[] sprites = new Sprite[6];
    private int spriteIndex = 0;
    private boolean isTurn;

    public Sprite getWorkerSprite() {
        return sprites[spriteIndex];
    }

    private void rc() {
        if (spriteIndex == 5) {
            spriteIndex = 0;
        } else {
            spriteIndex++;
        }
    }

    private void rcc() {
        if (spriteIndex == 0) {
            spriteIndex = 5;
        } else {
            spriteIndex--;
        }
    }

    public Sprite getSpriteAfterRC() {
        rc();
        return sprites[spriteIndex];
    }

    public Sprite getSpriteAfterRCC() {
        rcc();
        return sprites[spriteIndex];
    }

    public int getSpriteIndex() {
        return spriteIndex;
    }

    public SpriteWorker(Unit unit) {
        sprites[0] = new Sprite(unit);
        isTurn = false;

    }
}
