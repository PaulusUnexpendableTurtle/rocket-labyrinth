package paulusunexpendableturtle.rocketlabyrinth.gui;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import static java.lang.Math.abs;

import paulusunexpendableturtle.rocketlabyrinth.game.Level;

import static paulusunexpendableturtle.rocketlabyrinth.statics.IconicConstants.*;
import static paulusunexpendableturtle.rocketlabyrinth.statics.Bitmaps.*;

public class GameCanvas{

    private Canvas canvas;
    private SurfaceHolder holder;

    private int side;
    private int pause_zone_x, pause_zone_y;

    private int divx, divy;

    public GameCanvas(Resources c, SurfaceHolder h){

        holder = h;

        divx = c.getDisplayMetrics().widthPixels;
        divy = c.getDisplayMetrics().heightPixels;

    }

    public void lock(){
        canvas = holder.lockCanvas();
    }

    public void update(Level level, int state){

        side = cell.getWidth(canvas);
        float step = (float)side / iconicSide;
        level.setSideAndStep(side, step);

        level.update();

        float x = level.getX(), y = level.getY();
        int sizeX = level.getXSize(), sizeY = level.getYSize(),
                dirx = level.getDirX(), diry = level.getDirY(),
                divx = this.divx >> 1, divy = this.divy >> 1;
        long life = level.getLife();
        boolean[][] field = level.getField();

        back.draw(canvas, -x / 10, -y / 10);

        cell.draw(canvas, divx - x, divy - y);
        for(int i = 0; i < sizeY; i++)
            for(int j = 0; j < sizeX; j++)
                if(field[i][j] && (i < sizeY - 1 || j < sizeX - 1))
                    cell.draw(canvas, j * side + divx - x, i * side + divy - y);

        portal.draw(canvas, (sizeX - 1) * side + divx - x, (sizeY - 1) * side + divy - y);

        hero.draw(canvas, divx, divy, 2 + dirx + (diry - 1) * abs(diry));

        heart.draw(canvas, 0, 0);

        Paint p = new Paint();
        p.setTextAlign(Paint.Align.LEFT);


        p.setStyle(Paint.Style.STROKE);
        p.setTextSize(heart.getHeight(canvas));
        p.setStrokeJoin(Paint.Join.ROUND);
        p.setStrokeWidth(heart.getHeight(canvas) / 10);
        p.setColor(Color.BLACK);
        canvas.drawText(Long.toString(life), side, side, p);

        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.YELLOW);
        canvas.drawText(Long.toString(life), side, side, p);

        pause.draw(canvas, this.divx - pause.getWidth(canvas), 0);
        pause_zone_x = this.divx - pause.getWidth(canvas);
        pause_zone_y = pause.getHeight(canvas);

        if(state == 1){
            p = new Paint();
            p.setColor(Color.argb(66, 0, 0, 0));
            p.setStyle(Paint.Style.FILL);
            canvas.drawRect(0f, 0f, this.divx, this.divy, p);

            exit.draw(canvas, this.divx - (exit.getWidth(canvas) >> 1), this.divy - (exit.getHeight(canvas) >> 1));
        }

    }

    public int getPause_zone_x(){
        return pause_zone_x;
    }
    public int getPause_zone_y(){
        return pause_zone_y;
    }

    public boolean notNull(){
        return canvas != null;
    }

    public void unlock(){
        holder.unlockCanvasAndPost(canvas);
    }

    public void drawEnd(){
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        canvas.drawRect(0, 0, divx, divy, p);
        p.setColor(Color.WHITE);
        p.setTextSize(side);
        p.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("YOU WON!", divx >> 1, (divy + side) >> 1, p);
    }

}
