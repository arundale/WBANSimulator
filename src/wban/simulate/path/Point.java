package wban.simulate.path;

import java.io.Serializable;

public class Point implements Serializable {

    private static final long serialVersionUID = -7557568204627720060L;

    protected int posX;
    protected int posY;
    protected int midX;
    protected int midY;

    private Point previous = null;
    double distanceFromPrevious;

    protected Point() {
        resetPrevious();
    }

    public int getX() {
        return midX;
    }

    public int getY() {
        return midY;
    }

    public Point getPrevious() {
        return previous;
    }

    public void setPrevious(Point previous) {
        this.previous = previous;
    }

    public double getDistanceFromPrevious() {
        return distanceFromPrevious;
    }

    public void setDistanceFromPrevious(double distanceFromPrevious) {
        this.distanceFromPrevious = distanceFromPrevious;
    }

    public void resetPrevious() {
        previous = null;
        distanceFromPrevious = 99999.0;
    }

}
