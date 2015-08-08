package vis;

import com.sun.org.apache.xml.internal.utils.res.XResources_zh_TW;
import finalStates.Board;
import finalStates.FPoint;
import finalStates.Pivot;
import finalStates.Unit;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by kirill on 8/8/2015.
 */
public class BoardVis {

    private final static int SCALE = 32;

//    private final static int zeroCellX = X_STEP;
//    private final static int zeroCellY = X_STEP;

    public BufferedImage draw(Board board, Unit unit,  Unit[] nextUnits) {

        // detecting image size
        FPoint f0 = Board.getCoordsForIndexes(new Pivot(0, 0));
        FPoint f1 = Board.getCoordsForIndexes(new Pivot(board.width, board.height));

        int width = (int) ((f1.x - f0.x + 1)*SCALE);
        int height = (int) ((f1.y - f0.y + 1)*SCALE);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D gr = image.createGraphics();
        gr.setBackground(Color.WHITE);
        gr.fillRect(0,0,image.getWidth(),image.getHeight());

        for( int j=0; j<board.height; ++j) {
            for( int i=0; i<board.width; ++i) {
                drawCell(gr,i,j,board.readCell(i,j));
            }
        }

        return image;
    }

    private void drawCell(Graphics2D gr, int i, int j, int state) {
        FPoint p = Board.getCoordsForIndexes(new Pivot(i,j));

        Pivot center = new Pivot( (int)((p.x+1) * SCALE), (int)((p.y+1) * SCALE));
        final float R = Board.R * SCALE;

        Polygon polygon = new Polygon();
        for( int v=0; v<6; ++v) {
            final float a = (float) (2*Math.PI*(0.5f+v)/6.0f);
            int x = center.x + (int) (R * Math.cos(a));
            int y = center.y + (int) (R * Math.sin(a));
            polygon.addPoint(x,y);
        }
        gr.setColor(state == 0 ? Color.BLUE : Color.YELLOW);
        gr.fillPolygon(polygon);
        gr.setColor(Color.BLACK);
        gr.drawPolygon(polygon);
    }
}
