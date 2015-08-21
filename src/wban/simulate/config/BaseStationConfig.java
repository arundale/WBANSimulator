package wban.simulate.config;

import java.io.Serializable;

import wban.simulate.path.Point;

public class BaseStationConfig extends Point implements Serializable {

    public BaseStationConfig() {
        super();
    }

    private static final long serialVersionUID = 4712279332004017414L;

    public int getBSPosX() {
        return posX;
    }

    public void setBSPosX(int bsPosX) {
        this.posX = bsPosX;
    }

    public int getBSPosY() {
        return posY;
    }

    public void setBSPosY(int bsPosY) {
        this.posY = bsPosY;
    }

    public int getBSMidX() {
        return midX;
    }

    public void setBSMidX(int bsMidX) {
        this.midX = bsMidX;
    }

    public int getBSMidY() {
        return midY;
    }

    public void setBSMidY(int bsMidY) {
        this.midY = bsMidY;
    }

}
