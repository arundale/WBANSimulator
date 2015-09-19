package wban.simulate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import wban.simulate.config.SensorNodeConfig;
import wban.simulate.config.SerializableConfig;
import wban.simulate.log.Logger;
import wban.simulate.path.Dijkstra;
import wban.simulate.path.PathFinder;
import wban.simulate.path.PathSet;
import wban.simulate.util.Util;

public class Simulator {

    private static Simulator instance = new Simulator();
    private static final String configFileName = "config.ser";

    SerializableConfig config = new SerializableConfig();
    PathFinder pathFinder = new PathFinder();
    List<SensorNode> sensorNodes = new LinkedList<SensorNode>();
    BaseStation baseStation;
    int currentSensor = 0;
    private PathSet currentPathSet;

    public static Simulator getInstance() {
        return instance;
    }

    public void loadConfig() {
        try {
            FileInputStream fis = new FileInputStream(configFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            config = (SerializableConfig) ois.readObject();
            List<SensorNodeConfig> lstSC = config.getAllSlaveConfig();
            for (SensorNodeConfig sc : lstSC) {
                if (sc.getBatteryVolt() < .2)
                    sc.setBatteryVolt(4.2);
                sensorNodes.add(new SensorNode(sc, sensorNodes.size()));
            }
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            File file = new File(configFileName);
            file.delete();
            FileOutputStream fos = new FileOutputStream(configFileName);
            ObjectOutputStream ois = new ObjectOutputStream(fos);
            ois.writeObject(config);
            ois.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SerializableConfig getConfig() {
        return config;
    }

    public PathSet simulateDataTransmission(SensorNodeConfig sc) {
        PathSet pathSet = pathFinder.buildPath(sc, config, null);
        double currentByShortestPath = pathFinder.findShortestPath(sc, pathSet);
        double distanceToBS = Util.distanceBetween(sc, config.getBSConfig(0));
        double currentToBS = (distanceToBS * distanceToBS) * Dijkstra.milliAmpPerTransmit / 1000;
        Logger.log("Consumption (mA): Direct to Base Station:" + Math.round(currentToBS)
                + ", Shortest:" + Math.round(currentByShortestPath));
        currentPathSet = pathSet;
        return pathSet;
    }

    public BaseStation getBaseStation() {
        return baseStation;
    }

    public void setBaseStation(BaseStation baseStation) {
        this.baseStation = baseStation;
    }

    public List<SensorNode> getSensorNodes() {
        return sensorNodes;
    }

    public void addSensorNode(SensorNode sensorNode) {
        this.sensorNodes.add(sensorNode);
    }

    public SensorNode simulateNext() {
        PathSet ps = null;
        if (currentSensor < sensorNodes.size()) {
            SensorNode sensorNode = sensorNodes.get(currentSensor);
            SensorNodeConfig sensorConfig = sensorNode.getSensorNodeConfig();
            simulateBatteryDischarge(sensorConfig);
            ps = simulateDataTransmission(sensorConfig);
            sensorNode.sendData();
            currentSensor++;
            if (currentSensor == sensorNodes.size())
                currentSensor = 0;
        }
        return sensorNodes.get(currentSensor);
    }

    public PathSet getCurrentPathSet() {
        return currentPathSet;
    }

    public void simulateBatteryDischarge(SensorNodeConfig sensorConfig) {
        double batteryVolt = sensorConfig.getBatteryVolt();
        batteryVolt -= (config.getBatteryDischargeRateVoltPerSec() * config.getDataSendFrequencyMillis() / 1000);
        sensorConfig.setBatteryVolt(batteryVolt);
        if (batteryVolt < Battery.batteryDownThreshold)
            sensorConfig.setState(SensorNodeConfig.ST_DOWN);
        else
            sensorConfig.setState(SensorNodeConfig.ST_DISCHARGING);
    }
}
