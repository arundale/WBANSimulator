package wban.simulate;

import java.util.List;

import wban.simulate.config.SensorNodeConfig;

public class SensorNode {

    static final long serialVersionUID = -2949132871595431204L;
    SensorNodeConfig sensorNodeConfig = null;
    List<Sensor> sensors;

    Battery battery = null;

    public SensorNode(SensorNodeConfig config) {
        sensorNodeConfig = config;
    }

    public SensorNodeConfig getSensorNodeConfig() {
        return sensorNodeConfig;
    }

    public void sendData() {
    }

}
