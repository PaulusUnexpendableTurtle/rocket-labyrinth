package paulusunexpendableturtle.rocketlabyrinth.gui.sprites;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Sprite {

    private Bitmap image;

    public Sprite(Resources resources, int bitmap, int maxx, int maxy){
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(resources, bitmap, options);

            options.inSampleSize = calculateInSampleSize(options, maxx, maxy);

            options.inJustDecodeBounds = false;
            image = BitmapFactory.decodeResource(resources, bitmap, options);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public String toString(){
        return "" + image;
    }

    public int getWidth(Canvas canvas){
        return image.getScaledWidth(canvas);
    }

    public int getHeight(Canvas canvas){
        return image.getScaledHeight(canvas);
    }

    public void draw(Canvas canvas, float x, float y){
        canvas.drawBitmap(this.image, x, y, null);
    }

}