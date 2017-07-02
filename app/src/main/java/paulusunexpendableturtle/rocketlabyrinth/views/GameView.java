package paulusunexpendableturtle.rocketlabyrinth.views;

import android.content.Context;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import paulusunexpendableturtle.rocketlabyrinth.activities.GameActivity;
import paulusunexpendableturtle.rocketlabyrinth.game.Level;
import paulusunexpendableturtle.rocketlabyrinth.game.modes.Mode;
import paulusunexpendableturtle.rocketlabyrinth.input.LevelReader;
import paulusunexpendableturtle.rocketlabyrinth.gui.GameCanvas;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    public void set(String mode){
        this.mode = Mode.ModeFactory.buildMode(mode);
    }

    public GameView(Context context){
        super(context);
        getHolder().addCallback(this);

        destroyed = false;

        this.owner = (GameActivity)context;
    }

    public boolean destroyed;

    private DrawThread drawThread;
    private Mode mode;

    private GameActivity owner;

    private static class DrawThread extends Thread{

        private GameCanvas canvas;
        private LevelReader reader;
        private Mode mode;
        private Level level;

        private long lastTime, firstTime;
        private volatile int state = 2;

        DrawThread(Resources r, SurfaceHolder h, Mode m){
            canvas = new GameCanvas(r, h);
            reader = new LevelReader(r);
            mode = m;
        }

        @Override
        public void run(){

            lastTime = firstTime = System.currentTimeMillis();
            while(state > 0){

                canvas.lock();

                if(canvas.notNull()){
                    try{
                        run:
                        if(state > 0) {

                            if(state == 2) lastTime = System.currentTimeMillis();

                            if(level == null || level.complete) {
                                level = reader.readLevel(mode.update(level == null ? 0 : level.getLife(), level == null ? -1 : level.getVel()));
                                if(level == null) {
                                    canvas.drawEnd();
                                    requestStop();
                                    break run;
                                }

                                firstTime = lastTime;
                                level.setTime(firstTime, lastTime);
                            }

                            level.upTime(lastTime);
                            canvas.update(level, state);
                        }
                    }finally {
                        canvas.unlock();
                    }
                }
            }

        }

        void requestStop(){state = 0;}
        void requestRestore(){
            synchronized(this){
                long curTime = System.currentTimeMillis();
                firstTime += (curTime - lastTime);
                lastTime = curTime;
                level.setTime(firstTime, lastTime);
            }

            state = 2;
        }

    }

    float startX, startY;
    boolean ignore;

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(drawThread.state){
            case 0:
                owner.finish();
                break;
            case 1:
                if(drawThread.canvas.check_exit(event.getX(), event.getY())){
                    drawThread.requestStop();
                    owner.finish();
                    return false;
                }
                else if(event.getAction() == ACTION_DOWN) {
                    drawThread.requestRestore();
                    ignore = true;
                }
                break;
            case 2:
                switch (event.getAction()) {
                    case ACTION_DOWN:
                        if(event.getX() > drawThread.canvas.getPause_zone_x() && event.getY() < drawThread.canvas.getPause_zone_y())
                            drawThread.state = 1;
                        else {
                            startX = event.getX();
                            startY = event.getY();
                        }
                        break;
                    case ACTION_UP:
                        if(!ignore)
                            drawThread.level.updateDirection(event.getX() - startX, event.getY() - startY);
                        ignore = false;
                        break;
                }
                break;
        }
        return true;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        drawThread = new DrawThread(getContext().getResources(), getHolder(), mode);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){

        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
