package wban.simulate.path;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import wban.simulate.config.SlaveConfig;

public class PointSet {

    Pt from, to;
    BoundingSquare bs;
    List<Pt> in_between_points;
    List<Hashtable<SlaveConfig, List<LineSegment>>> paths = new LinkedList<Hashtable<SlaveConfig, List<LineSegment>>>();

    public void setFrom(Pt p) {
        from = p;
    }

    public void setTo(Pt p) {
        to = p;
    }

    public void addBetweenPt(Pt p) {
        in_between_points.add(p);
    }

    public Pt getFrom() {
        return from;
    }

    public Pt getTo() {
        return to;
    }

    public List<Pt> getInBetweenPts() {
        return in_between_points;
    }

    public void setBoundingSquare(int x1, int y1, int x2, int y2) {
        bs = new BoundingSquare(x1, y1, x2, y2);
    }

    public BoundingSquare getBoundingSquare() {
        return bs;
    }

    public void addPath(Hashtable<SlaveConfig, List<LineSegment>> lstLS) {
        paths.add(lstLS);
    }

    public List<Hashtable<SlaveConfig, List<LineSegment>>> getPaths() {
        return paths;
    }

}
