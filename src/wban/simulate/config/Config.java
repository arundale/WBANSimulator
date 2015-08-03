package wban.simulate.config;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Config implements Serializable {

    private static final long serialVersionUID = -8987840463031558730L;

    final double batteryVoltMax = 4.2f;
    final double batteryVoltThreshold = 2.5f;

    double batteryChargeRateVoltPerSec;
    double batteryDischargeRateVoltPerSec;
    List<SlaveConfig> slaveConfig = new LinkedList<SlaveConfig>();
    List<BaseStationConfig> bsConfig = new LinkedList<BaseStationConfig>();

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
        return bsConfig.size();
    }

    public int addSlaveConfig(SlaveConfig sc) {
        slaveConfig.add(sc);
        return slaveConfig.size();
    }

    public BaseStationConfig getBSConfig(int idx) {
        return bsConfig.get(idx);
    }
 
    public SlaveConfig getSlaveConfig(int idx) {
        return slaveConfig.get(idx);
    }

    public List<SlaveConfig> getAllSlaveConfig() {
        return slaveConfig;
    }

    public List<BaseStationConfig> getAllBSConfig() {
        return bsConfig;
    }

}
