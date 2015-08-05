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

    public PointSet buildPathFor(SlaveConfig sc, Config config,
            Hashtable<SlaveConfig, List<LineSegment>> pathMap) {
        PointSet pointSet = new PointSet();
        Pt from = new Pt(sc.getSlaveMidX(), sc.getSlaveMidY());
        pointSet.setFrom(from);
        BaseStationConfig bsConfig = config.getBSConfig(0);
        Pt to = new Pt(bsConfig.getBSMidX(), bsConfig.getBSMidY());
        pointSet.setTo(to);
        pointSet.setBoundingSquare(from.x, from.y, to.x, to.y);
        double distanceToBS = Util.distanceBetween(from, to);
        if (pathMap == null) {
            pathMap = new Hashtable<SlaveConfig, List<LineSegment>>();
            pointSet.addPath(pathMap);
        }
        List<LineSegment> lstLS = pathMap.get(sc);
        if (lstLS == null) {
           lstLS = new LinkedList<LineSegment>();
           pathMap.put(sc, lstLS);
        }
        if (distanceToBS < MAX_HOP_DISTANCE) {
            lstLS.add(new LineSegment(from, to, String.valueOf(Math
                    .round(distanceToBS))));
            return pointSet;
        }
        double angleToBS = Util.angle360(from, to);
        for (SlaveConfig otherSlave : config.getAllSlaveConfig()) {
            if (otherSlave.equals(sc))
                continue;
            Pt otherSlavePt = new Pt(otherSlave.getSlaveMidX(),
                    otherSlave.getSlaveMidY());
            double distanceToOtherSlave = Util.distanceBetween(from,
                    otherSlavePt);
            if (distanceToOtherSlave > MAX_HOP_DISTANCE)
                continue;
            double angleToOtherSlave = Util.angle360(from, otherSlavePt);
            double angleDiff = Math.abs(angleToOtherSlave - angleToBS);
            if (angleDiff > 60)
                continue;
            lstLS.add(new LineSegment(from, otherSlavePt, String.valueOf(Math
                            .round(distanceToOtherSlave))));
            buildPathFor(otherSlave, config, pathMap);
        }
        return pointSet;
    }

}
