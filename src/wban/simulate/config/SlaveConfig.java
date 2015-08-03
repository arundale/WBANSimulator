package wban.simulate.config;

import java.io.Serializable;

public class SlaveConfig implements Serializable {

    private static final long serialVersionUID = 1796860855765837767L;
    public static final short ST_DOWN = 0;
    public static final short ST_CHARGING = 1;
    public static final short ST_DISCHARGING = 2;

    int slavePosX;
    int slavePosY;
    double batteryVolt;
    short state;

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
    public short getState() {
        return state;
    }
    public void setState(short s) {
        state = s;
    }

}
