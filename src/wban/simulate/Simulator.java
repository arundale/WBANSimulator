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

import wban.simulate.config.SerializableConfig;
import wban.simulate.config.SensorNodeConfig;
import wban.simulate.path.Dijkstra;
import wban.simulate.path.PathFinder;
import wban.simulate.path.PathSet;

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
                sensorNodes.add(new SensorNode(sc));
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
        pathFinder.findShortestPath(sc, pathSet);
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

    public PathSet simulateNext() {
        PathSet ret = null;
        if (currentSensor < sensorNodes.size()) {
            ret = simulateDataTransmission(sensorNodes.get(currentSensor).getSensorNodeConfig());
            currentSensor++;
            if (currentSensor == sensorNodes.size())
                currentSensor = 0;
        }
        return ret;
    }

    public PathSet getCurrentPathSet() {
        return currentPathSet;
    }

    public void simulateBatteryDischarge() {
    }
}
