package wban.simulate.path;

import java.util.LinkedList;
import java.util.List;

import wban.simulate.config.SlaveConfig;

public class Dijkstra {

    public static void findShortestPath(SlaveConfig from, PathSet pathSet) {
        resolveDistances(pathSet, from, 0);
        List<Point[]> lstShortest = new LinkedList<Point[]>();
        Point to = pathSet.getTo();
        Point previous = to.getPrevious();
        while (previous != null) {
            lstShortest.add(new Point[] {previous, to});
            to = previous;
            previous = to.getPrevious();
        }
        pathSet.setShortest(lstShortest);
    }

    private static void resolveDistances(PathSet pathSet, Point from, double d) {
        List<LineSegment> lstLS = pathSet.getPaths(from);
        if (lstLS == null)
            return;
        for (LineSegment ls : lstLS) {
            Point to = ls.getTo();
            double newDistance = d + ls.getLength();
            if (to.getDistanceFromPrevious() > newDistance) {
                to.setDistanceFromPrevious(newDistance);
                to.setPrevious(from);
                resolveDistances(pathSet, to, newDistance);
            }
        }
    }

}
