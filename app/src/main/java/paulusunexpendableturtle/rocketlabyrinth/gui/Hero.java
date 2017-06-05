package paulusunexpendableturtle.rocketlabyrinth.gui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Hero{

    private Bitmap[] image;

    public Hero(Resources resources, int[] bitmaps){

        image = new Bitmap[bitmaps.length];

        try {
            for (int i = 0; i < bitmaps.length; i++)
                image[i] = BitmapFactory.decodeResource(resources, bitmaps[i]);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    void draw(Canvas canvas, float x, float y, int i){
        canvas.drawBitmap(this.image[i], x, y, null);
    }

}
