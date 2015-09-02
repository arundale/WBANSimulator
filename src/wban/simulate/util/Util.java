package wban.simulate.util;

import wban.simulate.path.Point;

public class Util {

    public static double distanceBetween(Point from, Point to) {
        double xDiff = to.getX() - from.getX();
        double yDiff = to.getY() - from.getY();
        double sqrXDiff = xDiff * xDiff;
        double sqrYDiff = yDiff * yDiff;
        return Math.sqrt(sqrXDiff + sqrYDiff);
    }

    public static double angle180(Point pt1, Point pt2) {
        return angle180(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
    }

    private static double angle180(int x1, int y1, int x2, int y2) {
        int dy = y2 - y1;
        int dx = x2 - x1;
        double theta = Math.atan2(dy, dx); // range (-PI, PI]
        theta *= 180 / Math.PI; // rads to degs, range (-180, 180]
        return theta;
    }

    public static double angle360(Point pt1, Point pt2) {
        double theta = angle180(pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY());
        if (theta < 0)
            theta = 360 + theta; // range [0, 360)
        return theta;
    }

}
