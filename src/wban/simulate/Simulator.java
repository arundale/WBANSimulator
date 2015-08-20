package wban.simulate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.List;

import wban.simulate.config.Config;
import wban.simulate.config.SlaveConfig;
import wban.simulate.path.LineSegment;
import wban.simulate.path.PathFinder;
import wban.simulate.path.PathSet;

public class Simulator {

    private static Simulator instance = new Simulator();
    private static final String configFileName = "config.ser";

    Config config = new Config();
    PathFinder pathFinder = new PathFinder();

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

    public Config getConfig() {
        return config;
    }

    public PathSet buildPathFor(SlaveConfig sc) {
        return pathFinder.buildPathFor(sc, config, null);
    }

}
