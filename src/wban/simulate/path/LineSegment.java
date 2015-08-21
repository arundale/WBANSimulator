package wban.simulate.path;

import wban.simulate.util.Util;

public class LineSegment {

    Point pt1, pt2;
    String label = null;
    double length = 0;

    public LineSegment(Point pt1, Point pt2, String l) {
        this.pt1 = pt1;
        this.pt2 = pt2;
        label = l;
        length = Util.distanceBetween(pt1, pt2);
    }

    public void setLabel(String l) {
        label = l;
    }

    public int[] getFromTo() {
        return new int[] {pt1.getX(), pt1.getY(), pt2.getX(), pt2.getY()};
    }

    public Point getFrom() {
        return pt1;
    }

    public Point getTo() {
        return pt2;
    }

    public String getLabel() {
        return label;
    }

    public boolean isShortestPath() {
        Point prev = pt2.getPrevious();
        if (prev != null && prev == pt1)
            return true;
        return false;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

}
