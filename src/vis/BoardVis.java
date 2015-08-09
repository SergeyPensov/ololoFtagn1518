package vis;

import finalStates.*;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by kirill on 8/8/2015.
 */
public class BoardVis {

    private final static int SCALE = 32;

    public static BufferedImage draw(Board board, Unit unit, UnitState state) {

        // detecting image size
        FPoint f0 = Board.getCoordsForIndexes(new Pivot(0, 0));
        FPoint f1 = Board.getCoordsForIndexes(new Pivot(board.width, board.height));

        int width = (int) ((f1.x - f0.x + 1)*SCALE);
        int height = (int) ((f1.y - f0.y + 1)*SCALE);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D gr = image.createGraphics();
        gr.setBackground(Color.WHITE);
        gr.fillRect(0, 0, image.getWidth(), image.getHeight());

        for( int j=0; j<board.height; ++j) {
            for( int i=0; i<board.width; ++i) {
                drawCell(gr,i,j,board.readCell(i,j), false);
            }
        }

        // showing unit
        if( unit != null) {
            final Unit transformed = board.transform(unit, state);

            for (Pivot member : transformed.members) {
                drawCell(gr, member.x, member.y, 1, true);
            }
            drawCell(gr, transformed.pivot.x, transformed.pivot.y, 2, true);
        }

        return image;
    }

    private static void drawCell(Graphics2D gr, int i, int j, int state, boolean isUnit) {
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

        if( state < 2) {
            if( isUnit )
                gr.setColor(state == 0 ? Color.BLUE : Color.green);
            else
                gr.setColor(state == 0 ? Color.BLUE : Color.YELLOW);

            gr.fillPolygon(polygon);
            gr.setColor(Color.BLACK);
            gr.drawPolygon(polygon);
        }

        if( 0 != (state & 2) ) {
            // pivot
            gr.setColor(Color.BLACK);
            final int d = SCALE/3;
            gr.fillOval(center.x - d / 2, center.y - d / 2, d, d);
        }
    }
}
