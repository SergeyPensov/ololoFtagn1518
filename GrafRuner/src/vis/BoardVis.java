package vis;

import com.sun.org.apache.xml.internal.utils.res.XResources_zh_TW;
import finalStates.Board;
import finalStates.FPoint;
import finalStates.Pivot;
import finalStates.Unit;

import java.awt.image.BufferedImage;

/**
 * Created by kirill on 8/8/2015.
 */
public class BoardVis {

    private final static int SCALE = 32;

//    private final static int zeroCellX = X_STEP;
//    private final static int zeroCellY = X_STEP;

    public BufferedImage draw(Board board, Unit unit,  Unit[] nextUnits) {
        BufferedImage image;

        return image;
    }

    private void drawCell(BufferedImage image, int i, int j) {
        FPoint p = Board.getCoordsForIndexes(new Pivot(i,j));

        Pivot center = new Pivot( (int)(p.x * SCALE), (int)(p.y * SCALE));

    }
}
