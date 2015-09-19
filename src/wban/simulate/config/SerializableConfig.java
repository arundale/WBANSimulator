package wban.simulate.config;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import wban.simulate.BaseStation;
import wban.simulate.SensorNode;
import wban.simulate.Simulator;

public class SerializableConfig implements Serializable {

    private static final long serialVersionUID = -8987840463031558730L;

    int dataSendFrequencyMillis = 1000;
    double batteryChargeRateVoltPerSec;
    double batteryDischargeRateVoltPerSec;
    List<SensorNodeConfig> sensorNodeConfig = new LinkedList<SensorNodeConfig>();
    List<BaseStationConfig> bsConfig = new LinkedList<BaseStationConfig>();

    public SerializableConfig() {
        batteryDischargeRateVoltPerSec = .0002;
    }

    public double getBatteryChargeRateVoltPerSec() {
        return batteryChargeRateVoltPerSec;
    }

    public void setBatteryChargeRateVoltPerSec(double batteryChargeRateVoltPerSec) {
        this.batteryChargeRateVoltPerSec = batteryChargeRateVoltPerSec;
    }

    public double getBatteryDischargeRateVoltPerSec() {
        return batteryDischargeRateVoltPerSec;
    }

    public void setBatteryDischargeRateVoltPerSec(
            double batteryDischargeRateVoltPerSec) {
        this.batteryDischargeRateVoltPerSec = batteryDischargeRateVoltPerSec;
    }

    public int addBaseStation(BaseStationConfig bsc) {
        bsConfig.add(bsc);
        int bsCount = bsConfig.size();
        Simulator.getInstance().setBaseStation(new BaseStation(bsc));
        return bsCount-1;
    }

    public int addSensorNodeConfig(SensorNodeConfig sc) {
        sensorNodeConfig.add(sc);
        Simulator.getInstance().addSensorNode(new SensorNode(sc, sensorNodeConfig.size()));
        return sensorNodeConfig.size()-1;
    }

    public BaseStationConfig getBSConfig(int idx) {
        return bsConfig.get(idx);
    }
 
    public SensorNodeConfig getSensorNodeConfig(int idx) {
        return sensorNodeConfig.get(idx);
    }

    public List<SensorNodeConfig> getAllSlaveConfig() {
        return sensorNodeConfig;
    }

    public List<BaseStationConfig> getAllBSConfig() {
        return bsConfig;
    }

    public int getDataSendFrequencyMillis() {
        return dataSendFrequencyMillis;
    }

    public void setDataSendFrequencySeconds(int dataSendFrequencySeconds) {
        this.dataSendFrequencyMillis = dataSendFrequencySeconds;
    }

}
