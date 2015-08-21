package wban.simulate.path;

import java.util.LinkedList;
import java.util.List;

import wban.simulate.config.BaseStationConfig;
import wban.simulate.config.Config;
import wban.simulate.config.SlaveConfig;
import wban.simulate.util.Util;

public class PathFinder {

    public static final double MAX_HOP_DISTANCE = 300;

    public PathSet buildPathFor(SlaveConfig sc, Config config, PathSet pathSet) {

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
            LineSegment path = new LineSegment(sc, bsConfig, String.valueOf(Math
                    .round(distanceToBS)));
            lstLS.add(path);
            return pathSet;
        }
        double angleToBS = Util.angle180(sc, bsConfig);
        for (SlaveConfig otherSlave : config.getAllSlaveConfig()) {
            otherSlave.resetPrevious();
            if (otherSlave.equals(sc))
                continue;
            if (otherSlave.getState() == SlaveConfig.ST_DOWN)
                continue;
            double distanceToOtherSlave = Util.distanceBetween(sc,
                    otherSlave);
            if (distanceToOtherSlave > MAX_HOP_DISTANCE)
                continue;
            double angleToOtherSlave = Util.angle180(sc, otherSlave);
            double angleDiff = Math.abs(angleToOtherSlave - angleToBS);
            if (angleDiff > 60)
                continue;
            LineSegment ls = new LineSegment(sc, otherSlave,
                    String.valueOf(Math.round(distanceToOtherSlave)));
            lstLS.add(ls);
            buildPathFor(otherSlave, config, pathSet);
        }
        return pathSet;

    }

}
