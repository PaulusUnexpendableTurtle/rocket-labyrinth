package paulusunexpendableturtle.rocketlabyrinth.gui.sprites;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CanvasButton extends Sprite {

    private String text;
    private int[] textColor;

    public CanvasButton(Resources resources, int bitmap, int maxx, int maxy, String text, int[] color){
        super(resources, bitmap, maxx, maxy);
        this.text = text;
        textColor = color;
    }

    @Override
    public void draw(Canvas canvas, float x, float y){
        super.draw(canvas, x, y);

        Paint p = new Paint();
        p.setTextAlign(Paint.Align.CENTER);

        p.setStyle(Paint.Style.STROKE);
        p.setTextSize(getHeight(canvas) >> 1);
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeWidth(getHeight(canvas) / 20);
        p.setColor(Color.BLACK);
        canvas.drawText(text, x + getWidth(canvas) / 2, y + 3 * getHeight(canvas) / 4, p);

        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.argb(textColor[0], textColor[1], textColor[2], textColor[3]));
        canvas.drawText(text, x + getWidth(canvas) / 2, y + 3 * getHeight(canvas) / 4, p);
    }

}
