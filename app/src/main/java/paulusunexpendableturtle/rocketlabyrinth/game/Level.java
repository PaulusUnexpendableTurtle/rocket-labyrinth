package paulusunexpendableturtle.rocketlabyrinth.game;

import java.lang.reflect.Field;
import java.util.Map;

import paulusunexpendableturtle.rocketlabyrinth.geometry.Rect;
import static paulusunexpendableturtle.rocketlabyrinth.statics.IconicConstants.iconicVel;

import static java.lang.Math.abs;

public class Level implements Cloneable{

    private int dirx, diry;
    private float x, y;

    private int vel;
    private float step;

    private long life, startlife;
    public boolean complete;

    private int iterations, steps;
    private long firstTime, lastTime;

    private int side, width, height;

    private int sizeX, sizeY;

    private boolean[][] field;

    public Level(boolean[][] field, int x, int y, long startlife, long curlife, int vel){
        this.field = field;
        sizeX = x;
        sizeY = y;

        iterations = 0;
        steps = 0;

        dirx = 0; diry = 0;
        this.x = 0; this.y = 0;

        this.vel = vel;

        this.startlife = startlife;
        this.life = curlife;
        complete = false;
    }

    private void toStart(){

        x = 0; y = 0;
        dirx = 0; diry = 0;
        life = startlife;

    }

    public void setTime(long first, long last){
        firstTime = first;
        lastTime = last;
    }
    public void upTime(long last){lastTime = last;}

    public long getFirstTime(){
        return firstTime;
    }

    public long getLastTime(){
        return lastTime;
    }

    public void updateDirection(float x, float y){

        if(x == 0 && y == 0)
            return;

        if(abs(x) > abs(y)){
            diry = 0;
            dirx = x > 0 ? 1 : -1;
        }else{
            dirx = 0;
            diry = y > 0 ? 1 : -1;
        }

    }

    public void setSideAndStep(int side, float step){
        this.side = side;
        setDims();

        this.step = step;
    }
    private void setDims(){
        width = sizeX * side;
        height = sizeY * side;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public void setCoords(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setSteps(int iterations, int steps){
        this.iterations = iterations;
        this.steps = steps;
    }

    public void update(){

        while(lastTime - firstTime >= iterations * 1000){
            ++iterations;
            boolean a = Math.random() < 0.5,
                    b = Math.random() < 0.5;
            shift(a, b, (int)(Math.random() * (a ? sizeY : sizeX)));
        }

        while(lastTime - firstTime >= steps * vel){

            steps++;

            if(checkPos())
                life -= iconicVel;

            if(dirx > 0){
                if(x <= width - side / 2 - step)
                    x+= step;
                else
                    dirx = 0;
            }
            if(dirx < 0){
                if(x >= step)
                    x-= step;
                else
                    dirx = 0;
            }

            if(diry > 0){
                if(y <= height - side / 2 - step)
                    y+= step;
                else
                    diry = 0;
            }
            if(diry < 0){
                if(y >= step)
                    y-= step;
                else
                    diry = 0;
            }

        }

        if(new Rect(x, y, side / 2, side / 2).cross(new Rect(width - side, height - side, side, side))){
            complete = true;
            dirx = 0; diry = 0;
        }
        else if(life <= 0) {
            life = 0;
            toStart();
        }

    }

    private boolean checkPos(){
        Rect t = new Rect(x, y, side >> 1, side >> 1);

        int x0 = (int)x / side,
                y0 = (int)y / side,
                x1 = (int)x % side > (side >> 1) ? floorDiv(((int)x - 1), side) + 1 : x0,
                y1 = (int)y % side > (side >> 1) ? floorDiv(((int)y - 1), side) + 1 : y0;

        Rect a = !field[y0][x0] ? new Rect(x0, y0, side) : new Rect(),
                b = !field[y1][x0] ? new Rect(x0, y1, side) : new Rect(),
                c = !field[y0][x1] ? new Rect(x1, y0, side) : new Rect(),
                d = !field[y1][x1] ? new Rect(x1, y1, side) : new Rect();

        return !(x0 == y0 && y0 == 0) && ( t.cross(a) || t.cross(b) || t.cross(c) || t.cross(d) );
    }

    private static int floorDiv(int x, int y) {
        int r = x / y;
        if ((x ^ y) < 0 && (r * y != x)) r--;
        return r;
    }

    private void shift(boolean hor, boolean left, int col){
        if(hor){
            if(left){
                boolean t = field[col][0];
                System.arraycopy(field[col], 1, field[col], 0, sizeX - 1);
                field[col][sizeX - 1] = t;
            }else{
                boolean t = field[col][sizeX - 1];
                System.arraycopy(field[col], 0, field[col], 1, sizeX - 1);
                field[col][0] = t;
            }
        }else{
            if(left){
                boolean t = field[0][col];
                for(int i = 1; i < sizeY; i++)
                    field[i - 1][col] = field[i][col];
                field[sizeY - 1][col] = t;
            }else{
                boolean t = field[sizeY - 1][col];
                for(int i = sizeY - 1; i > 0; i--)
                    field[i][col] = field[i - 1][col];
                field[0][col] = t;
            }
        }
    }

    public float getX(){return x;}
    public float getY(){return y;}

    public int getXSize(){return sizeX;}
    public int getYSize(){return sizeY;}

    public int getDirX(){return dirx;}
    public int getDirY(){return diry;}

    public long getLife(){return life;}

    public int getVel(){return vel;}

    public boolean[][] getField(){return field;}

    public static void translateToMap(Map<String, Object> map, Level level){
        try{
            level = (Level)level.clone();

            String[] name = {
                    "dirx",
                    "diry",

                    "x",
                    "y",

                    "vel",
                    "step",

                    "life",
                    "startlife",

                    "complete",

                    "iterations",
                    "steps",

                    "firstTime",
                    "lastTime",

                    "side",

                    "sizeX",
                    "sizeY",

                    "field"
            };

            Field[] fields = new Field[name.length];
            for(int i = 0; i < name.length; ++i)
                fields[i] = Level.class.getDeclaredField(name[i]);

            for(Field f : fields)
                if(!f.getName().equals("field"))
                     map.put(f.getName(), f.get(level));

        }catch(IllegalAccessException|CloneNotSupportedException|NoSuchFieldException e){
            e.printStackTrace();
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException{
        Level res = (Level)super.clone();

        res.field = new boolean[sizeY][sizeX];
        for(int i = 0; i < sizeY; ++i)
            System.arraycopy(field[i], 0, res.field[i], 0, sizeX);

        return res;
    }

}
