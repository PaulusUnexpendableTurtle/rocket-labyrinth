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
        p.setTextSize(getHeight(canvas) >> 1);
        p.setColor(Color.argb(textColor[0], textColor[1], textColor[2], textColor[3]));
        canvas.drawText(text, x + (getWidth(canvas) >> 1), y + (getHeight(canvas) >> 1), p);
    }

}
