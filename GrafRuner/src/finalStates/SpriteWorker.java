package finalStates;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @Author Sergey Pensov
 */
public class SpriteWorker {
    private Sprite[] sprites = new Sprite[6];
    private int spriteIndex = 0;

    public Sprite getWorkerSprite() {
        return sprites[spriteIndex];
    }

    public void rc() {
        if (spriteIndex == 5) {
            spriteIndex = 0;
        } else {
            spriteIndex++;
        }
    }

    public void rcc() {
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

        this.sprites = sprites;
    }
}
