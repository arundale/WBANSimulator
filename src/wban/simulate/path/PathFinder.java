package wban.simulate.path;

import java.util.LinkedList;
import java.util.List;

import wban.simulate.config.BaseStationConfig;
import wban.simulate.config.SerializableConfig;
import wban.simulate.config.SensorNodeConfig;
import wban.simulate.util.Util;

public class PathFinder {

    public static final double MAX_HOP_DISTANCE = 300;

    Dijkstra dijkstra = new Dijkstra();

    public PathSet buildPath(SensorNodeConfig sc, SerializableConfig config, PathSet pathSet) {

        sc.resetPrevious();
        BaseStationConfig bsConfig = config.getBSConfig(0);
        bsConfig.resetPrevious();
        if (pathSet == null) {
            pathSet = new PathSet();
            pathSet.setFrom(sc);
            pathSet.setTo(bsConfig);
        }
        double distanceToBS = Util.distanceBetween(sc, bsConfig);
        List<LineSegment> lstLS = pathSet.getPaths(sc);
        if (lstLS == null) {
            lstLS = new LinkedList<LineSegment>();
            pathSet.setPath(sc, lstLS);
        }
        if (distanceToBS < MAX_HOP_DISTANCE) {
            LineSegment path = new LineSegment(sc, bsConfig, String.valueOf(Math.round(distanceToBS)));
            lstLS.add(path);
            return pathSet;
        }
        double angleToBS = Util.angle180(sc, bsConfig);
        for (SensorNodeConfig otherSlave : config.getAllSlaveConfig()) {
            otherSlave.resetPrevious();
            if (otherSlave.equals(sc))
                continue;
            if (otherSlave.getState() == SensorNodeConfig.ST_DOWN)
                continue;
            double distanceToOtherSlave = Util.distanceBetween(sc, otherSlave);
            if (distanceToOtherSlave > MAX_HOP_DISTANCE)
                continue;
            double angleToOtherSlave = Util.angle180(sc, otherSlave);
            double angleDiff = Math.abs(angleToOtherSlave - angleToBS);
            if (angleDiff > 60)
                continue;
            LineSegment ls = new LineSegment(sc, otherSlave, String.valueOf(Math.round(distanceToOtherSlave)));
            lstLS.add(ls);
            buildPath(otherSlave, config, pathSet);
        }
        return pathSet;

    }

    public double findShortestPath(SensorNodeConfig sc, PathSet pathSet) {
        return dijkstra.findShortestPath(sc, pathSet);
    }

}
