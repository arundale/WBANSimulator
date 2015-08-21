package wban.simulate.config;

import java.io.Serializable;

import wban.simulate.path.Point;

public class SlaveConfig extends Point implements Serializable {

    private static final long serialVersionUID = 1796860855765837767L;
    public static final short ST_DOWN = 0;
    public static final short ST_CHARGING = 1;
    public static final short ST_DISCHARGING = 2;

    double batteryVolt;
    short state;

    public int getSlavePosX() {
        return posX;
    }
    public void setSlavePosX(int slavePosX) {
        this.posX = slavePosX;
    }
    public int getSlavePosY() {
        return posY;
    }
    public void setSlavePosY(int slavePosY) {
        this.posY = slavePosY;
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
    public int getSlaveMidX() {
        return midX;
    }
    public void setSlaveMidX(int slaveMidX) {
        this.midX = slaveMidX;
    }
    public int getSlaveMidY() {
        return midY;
    }
    public void setSlaveMidY(int slaveMidY) {
        this.midY = slaveMidY;
    }

}
