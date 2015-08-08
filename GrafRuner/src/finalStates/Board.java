package finalStates;

/**
 * Created by kirill on 8/8/2015.
 */
public class Board {

    private static final float R = 1.0f/(float)Math.sqrt(3);
    private static final float DY = R * 1.5f;


    public static FPoint getCoordsForIndexes(final Pivot index) {
        float x = index.x;
        if( 1 == (index.y & 1)) x += 0.5f;
        float y = index.y * DY;
        return new FPoint(x, y);
    }

    public static Pivot getIndexForCoordinates(final FPoint point) {
        // todo
        return new Pivot(0,0);
    }
}
