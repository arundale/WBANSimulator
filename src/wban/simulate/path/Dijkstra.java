package wban.simulate.path;

import java.util.LinkedList;
import java.util.List;

import wban.simulate.config.SensorNodeConfig;
import wban.simulate.util.Util;

public class Dijkstra {

    public static double milliAmpPerTransmit = 10;

    public double findShortestPath(SensorNodeConfig from, PathSet pathSet) {
        resolveDistances(pathSet, from, 0);
        List<Point[]> lstShortest = new LinkedList<Point[]>();
        Point to = pathSet.getTo();
        Point previous = to.getPrevious();
        double consumption = 0;
        while (previous != null) {
            lstShortest.add(new Point[] { previous, to });
            double distance = Util.distanceBetween(previous, to);
            double current = (distance * distance) * Dijkstra.milliAmpPerTransmit / 1000;
            consumption += current;
            to = previous;
            previous = to.getPrevious();
        }
        pathSet.setShortest(lstShortest);
        return consumption;
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
