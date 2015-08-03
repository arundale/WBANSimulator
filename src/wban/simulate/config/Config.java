package wban.simulate.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config implements Serializable {

    private static final long serialVersionUID = -8987840463031558730L;

    final double batteryVoltMax = 4.2f;
    final double batteryVoltThreshold = 2.5f;

    int baseStationPosX;
    int baseStationPosY;
    double batteryChargeRateVoltPerSec;
    double batteryDischargeRateVoltPerSec;
    List<SlaveConfig> slaveConfig;
    List<BaseStationConfig> bsConfig;

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        JSONObject jso = new JSONObject();
        jso.put("baseStationPosX", String.valueOf(baseStationPosX));
        jso.put("baseStationPosY", String.valueOf(baseStationPosY));
        jso.put("batteryChargeRateVoltPerSec", String.valueOf(batteryChargeRateVoltPerSec));
        jso.put("batteryDischargeRateVoltPerSec", String.valueOf(batteryDischargeRateVoltPerSec));
        JSONArray jsaChildren = new JSONArray();
        for (SlaveConfig config : slaveConfig) {
            JSONObject jsoSlave = new JSONObject();
            jsoSlave.put("slavePosX", String.valueOf(config.getSlavePosX()));
            jsoSlave.put("slavePosY", String.valueOf(config.getSlavePosY()));
            jsoSlave.put("batteryVolt", String.valueOf(config.getBatteryVolt()));
        }
        jso.put("slaveConfig", jsaChildren);
        jsaChildren = new JSONArray();
        for (BaseStationConfig config : bsConfig) {
            JSONObject jsoBS = new JSONObject();
            jsoBS.put("bsPosX", String.valueOf(config.getBSPosX()));
            jsoBS.put("bsPosY", String.valueOf(config.getBSPosY()));
        }
        jso.put("bsConfig", jsaChildren);
        out.writeChars(jso.toJSONString());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        JSONParser jsop = new JSONParser();
        try {
            JSONObject jso = (JSONObject) jsop.parse(new InputStreamReader(
                    (InputStream) in));
            baseStationPosX = Integer.parseInt((String) jso
                    .get("baseStationPosX"));
            baseStationPosY = Integer.parseInt((String) jso
                    .get("baseStationPosY"));
            batteryChargeRateVoltPerSec = Double.parseDouble((String) jso
                    .get("batteryChargeRateVoltPerSec"));
            batteryDischargeRateVoltPerSec = Double.parseDouble((String) jso
                    .get("batteryDischargeRateVoltPerSec"));
            JSONArray jsaChildren = (JSONArray) jso.get("slaveConfig");
            slaveConfig = new LinkedList<SlaveConfig>();
            for (int i = 0; i < jsaChildren.size(); i++) {
                JSONObject jsoSlave = (JSONObject) jsaChildren.get(i);
                SlaveConfig slave = new SlaveConfig();
                slave.setBatteryVolt(Double
                        .parseDouble((String) jsoSlave.get("batteryVolt")));
                slave.setSlavePosX(Integer.parseInt((String) jsoSlave
                        .get("slavePosX")));
                slave.setSlavePosY(Integer.parseInt((String) jsoSlave
                        .get("slavePosY")));
                slaveConfig.add(slave);
            }
            jsaChildren = (JSONArray) jso.get("bsConfig");
            bsConfig = new LinkedList<BaseStationConfig>();
            for (int i = 0; i < jsaChildren.size(); i++) {
                JSONObject jsoSlave = (JSONObject) jsaChildren.get(i);
                BaseStationConfig bs = new BaseStationConfig();
                bs.setBSPosX(Integer.parseInt((String) jsoSlave
                        .get("bsPosX")));
                bs.setBSPosY(Integer.parseInt((String) jsoSlave
                        .get("bsPosY")));
                bsConfig.add(bs);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getBaseStationPosX() {
        return baseStationPosX;
    }

    public void setBaseStationPosX(int baseStationPosX) {
        this.baseStationPosX = baseStationPosX;
    }

    public int getBaseStationPosY() {
        return baseStationPosY;
    }

    public void setBaseStationPosY(int baseStationPosY) {
        this.baseStationPosY = baseStationPosY;
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

}
