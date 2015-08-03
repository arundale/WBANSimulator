package wban.simulate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import wban.simulate.config.BaseStationConfig;
import wban.simulate.config.Config;
import wban.simulate.config.SlaveConfig;

public class Simulator {

    static Simulator instance = new Simulator();
    private static final String configFileName = "config.json";

    Config config = new Config();

    public static Simulator getInstance() {
        return instance;
    }

    public void loadConfig() {
        try {
            FileInputStream fis = new FileInputStream(configFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            config = (Config) ois.readObject();
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

    public int addBaseStation(BaseStationConfig bsConfig) {
        return config.addBaseStation(bsConfig);
    }

    public int addSlaveConfig(SlaveConfig slaveConfig) {
        return config.addSlaveConfig(slaveConfig);
    }

    public Config getConfig() {
        return config;
    }

    public List<SlaveConfig> getAllSlaveConfig() {
        return config.getAllSlaveConfig();
    }

    public List<BaseStationConfig> getAllBSConfig() {
        return config.getAllBSConfig();
    }

}
