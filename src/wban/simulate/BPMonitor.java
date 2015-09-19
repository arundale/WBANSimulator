package wban.simulate;

public class BPMonitor implements Sensor {

    public String acquireData() {
        return "{sensor_id: 3, type: \"bp\", systolic: " + Math.round(110 + Math.random() * 30) + ", diastolic: "
                + Math.round(70 + Math.random() * 20) + "}";
    }

}
