package wban.simulate.path;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import wban.simulate.config.SlaveConfig;

public class PathSet {

    Point from, to;
    Hashtable<SlaveConfig, List<LineSegment>> pathMap = new Hashtable<SlaveConfig, List<LineSegment>>();

    public void setFrom(Point p) {
        from = p;
    }

    public void setTo(Point p) {
        to = p;
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }

    public List<LineSegment> getPath(SlaveConfig sc) {
        return pathMap.get(sc);
    }

    public void setPath(SlaveConfig sc, List<LineSegment> path) {
        pathMap.put(sc, path);
    }

    public Hashtable<SlaveConfig, List<LineSegment>> getAllPaths() {
        return pathMap;
    }

}
