package paulusunexpendableturtle.rocketlabyrinth.statics;

import android.content.res.Resources;

import paulusunexpendableturtle.rocketlabyrinth.R;
import paulusunexpendableturtle.rocketlabyrinth.gui.Hero;
import paulusunexpendableturtle.rocketlabyrinth.gui.Sprite;

import static paulusunexpendableturtle.rocketlabyrinth.statics.IconicConstants.maxlen;

public final class Bitmaps {
    public static Sprite heart, cell, back, portal, pause;
    public static Hero hero;

    public static synchronized void decodeResources(Resources c){
        heart = new Sprite(c, R.drawable.fuel1, c.getDisplayMetrics().widthPixels + maxlen, c.getDisplayMetrics().heightPixels + maxlen);
        cell = new Sprite(c, R.drawable.cell, c.getDisplayMetrics().widthPixels + maxlen, c.getDisplayMetrics().heightPixels + maxlen);
        back = new Sprite(c, R.drawable.background_game, c.getDisplayMetrics().widthPixels + maxlen, c.getDisplayMetrics().heightPixels + maxlen);
        portal = new Sprite(c, R.drawable.portal, c.getDisplayMetrics().widthPixels + maxlen, c.getDisplayMetrics().heightPixels + maxlen);
        pause = new Sprite(c,R.drawable.pause_button_1, c.getDisplayMetrics().widthPixels + maxlen, c.getDisplayMetrics().heightPixels + maxlen);

        int[] t = {R.drawable.herow,
                R.drawable.heroa,
                R.drawable.heros,
                R.drawable.herod};
        hero = new Hero(c, t);
    }

    public static synchronized boolean isNull(){
        return back == null;
    }
}
