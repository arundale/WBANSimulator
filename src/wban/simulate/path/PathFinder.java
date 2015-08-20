package wban.simulate.path;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import wban.simulate.config.BaseStationConfig;
import wban.simulate.config.Config;
import wban.simulate.config.SlaveConfig;
import wban.simulate.util.Util;

public class PathFinder {

    public static final double MAX_HOP_DISTANCE = 300;

    public PathSet buildPathFor(SlaveConfig sc, Config config, PathSet pathSet) {

        if (pathSet == null)
            pathSet = new PathSet();
        Point from = new Point(sc.getSlaveMidX(), sc.getSlaveMidY());
        pathSet.setFrom(from);
        BaseStationConfig bsConfig = config.getBSConfig(0);
        Point to = new Point(bsConfig.getBSMidX(), bsConfig.getBSMidY());
        pathSet.setTo(to);
        double distanceToBS = Util.distanceBetween(from, to);
        List<LineSegment> lstLS = pathSet.getPath(sc);
        if (lstLS == null) {
            lstLS = new LinkedList<LineSegment>();
            pathSet.setPath(sc, lstLS);
        }
        if (distanceToBS < MAX_HOP_DISTANCE) {
            LineSegment path = new LineSegment(from, to, String.valueOf(Math
                    .round(distanceToBS)), sc, bsConfig);
            lstLS.add(path);
            return pathSet;
        }
        double angleToBS = Util.angle180(from, to);
        for (SlaveConfig otherSlave : config.getAllSlaveConfig()) {
            if (otherSlave.equals(sc))
                continue;
            if (otherSlave.getState() == SlaveConfig.ST_DOWN)
                continue;
            Point otherSlavePt = new Point(otherSlave.getSlaveMidX(),
                    otherSlave.getSlaveMidY());
            double distanceToOtherSlave = Util.distanceBetween(from,
                    otherSlavePt);
            if (distanceToOtherSlave > MAX_HOP_DISTANCE)
                continue;
            double angleToOtherSlave = Util.angle180(from, otherSlavePt);
            double angleDiff = Math.abs(angleToOtherSlave - angleToBS);
            if (angleDiff > 60)
                continue;
            LineSegment ls = new LineSegment(from, otherSlavePt,
                    String.valueOf(Math.round(distanceToOtherSlave)),
                    otherSlave, bsConfig);
            lstLS.add(ls);
            buildPathFor(otherSlave, config, pathSet);
        }
        return pathSet;

    }

}
