package wban.simulate.path;

import java.util.Hashtable;
import java.util.List;

import wban.simulate.config.SlaveConfig;

public class PathSet {

    Point from, to;
    Hashtable<Point, List<LineSegment>> pathMap = new Hashtable<Point, List<LineSegment>>();
    private List<Point[]> shortestPath;

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

    public List<LineSegment> getPaths(Point pt) {
        return pathMap.get(pt);
    }

    public void setPath(SlaveConfig sc, List<LineSegment> path) {
        pathMap.put(sc, path);
    }

    public Hashtable<Point, List<LineSegment>> getAllPaths() {
        return pathMap;
    }

    public void setShortest(List<Point[]> lstShortest) {
        shortestPath = lstShortest;
    }

    public List<Point[]> getShortestPath() {
        return shortestPath;
    }

}
