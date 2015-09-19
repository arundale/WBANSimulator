package wban.simulate;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import wban.simulate.config.SensorNodeConfig;
import wban.simulate.log.Logger;

public class SensorNode {

    static final long serialVersionUID = -2949132871595431204L;

    int sensorId = 0;
    SensorNodeConfig sensorNodeConfig = null;
    List<Sensor> sensors = new LinkedList<Sensor>();

    Battery battery = new Battery();

    public SensorNode(SensorNodeConfig config, int id) {
        sensorNodeConfig = config;
        sensorId = id;
        sensors.add(new Temperature());
        sensors.add(new ECG());
        sensors.add(new BPMonitor());
    }

    public SensorNodeConfig getSensorNodeConfig() {
        return sensorNodeConfig;
    }

    public void sendData() {
        Logger.log("Data: " + "{id:" + sensorId + ", time: \"" + DateFormat.getInstance().format(Calendar.getInstance().getTime()) + "\", ");
        for (Sensor s : sensors) {
            Logger.log("Data:   " + s.acquireData() + ",");
        }
        Logger.log("Data: }");
    }

}
