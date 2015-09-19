package wban.simulate;

public class Temperature implements Sensor {

    public String acquireData() {
        return "{sensor_id: 1, type: \"temperature\", temperature: \"" + Math.round(95 + Math.random() * 6) + "\"}";
    }

}
