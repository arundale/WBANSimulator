package wban.simulate.path;

public class Point {

    int x, y;
    Point previous = null;
    double distanceFromPrevious = 99999;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPrevious() {
        return previous;
    }

    public void setPrevious(Point previous) {
        this.previous = previous;
    }

}
