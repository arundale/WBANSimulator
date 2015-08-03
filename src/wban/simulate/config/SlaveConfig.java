package wban.simulate.config;

public class SlaveConfig {

    int slavePosX;
    int slavePosY;
    double batteryVolt;

    public int getSlavePosX() {
        return slavePosX;
    }
    public void setSlavePosX(int slavePosX) {
        this.slavePosX = slavePosX;
    }
    public int getSlavePosY() {
        return slavePosY;
    }
    public void setSlavePosY(int slavePosY) {
        this.slavePosY = slavePosY;
    }
    public double getBatteryVolt() {
        return batteryVolt;
    }
    public void setBatteryVolt(double batteryVolt) {
        this.batteryVolt = batteryVolt;
    }

}
