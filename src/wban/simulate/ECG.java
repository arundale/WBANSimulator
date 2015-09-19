package wban.simulate;

public class ECG implements Sensor {

    public String acquireData() {
        return "{sensor_id: 2, type: \"ecg\", status: \"normal\"}";
    }

}
