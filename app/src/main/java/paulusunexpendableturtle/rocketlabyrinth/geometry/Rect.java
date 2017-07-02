package paulusunexpendableturtle.rocketlabyrinth.geometry;

public class Rect {
    private float x0, y0, x1, y1;

    public Rect(float x, float y, float w, float h){
        x0 = x;
        y0 = y;
        x1 = x0 + w;
        y1 = y0 + h;
    }

    public Rect(int x, int y, int side){
        x0 = x * side;
        y0 = y * side;
        x1 = x0 + side;
        y1 = y0 + side;
    }

    public Rect(){
        x0 = y0 = x1 = y1 = -1;
    }

    public boolean cross(Rect a){
        return isIn(x0, x1, a.x0, a.x1) && isIn(y0, y1, a.y0, a.y1);
    }

    public boolean isIn(float x, float y){
        return x >= x0 && x <= x1 && y >= y0 && y <= y1;
    }

    private static boolean isIn(float a, float b, float c, float d){
        return !(b < c || d < a);
    }
}
