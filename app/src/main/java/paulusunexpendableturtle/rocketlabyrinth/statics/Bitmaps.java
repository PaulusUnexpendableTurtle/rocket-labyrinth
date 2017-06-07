package paulusunexpendableturtle.rocketlabyrinth.statics;

import android.content.res.Resources;

import paulusunexpendableturtle.rocketlabyrinth.R;
import paulusunexpendableturtle.rocketlabyrinth.gui.sprites.CanvasButton;
import paulusunexpendableturtle.rocketlabyrinth.gui.sprites.Hero;
import paulusunexpendableturtle.rocketlabyrinth.gui.sprites.Sprite;

import static paulusunexpendableturtle.rocketlabyrinth.statics.IconicConstants.maxlen;

public final class Bitmaps {
    public static Sprite heart, cell, back, portal, pause;
    public static Hero hero;
    public static CanvasButton exit;

    public static synchronized void tryDecodeResources(Resources c){

        if(back != null) return;

        int w = c.getDisplayMetrics().widthPixels,
                h = c.getDisplayMetrics().heightPixels;

        heart = new Sprite(c, R.drawable.fuel1, w, h);
        cell = new Sprite(c, R.drawable.cell, w, h);
        back = new Sprite(c, R.drawable.background_game, w + maxlen, h + maxlen);
        portal = new Sprite(c, R.drawable.portal, w, h);
        pause = new Sprite(c,R.drawable.pause_button_1, w, h);

        int[] color = {255, 255, 255, 255};
        exit = new CanvasButton(c, R.drawable.overlay_button_shape, w, h, c.getString(R.string.quit_game_process_button), color);

        int[] t = {R.drawable.herow,
                R.drawable.heroa,
                R.drawable.heros,
                R.drawable.herod};
        hero = new Hero(c, t);
    }

}
