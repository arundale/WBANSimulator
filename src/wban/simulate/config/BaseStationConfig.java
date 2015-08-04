package wban.simulate.config;

import java.io.Serializable;

public class BaseStationConfig implements Serializable {

    int bsPosX;
    int bsPosY;
    int bsMidX;
    int bsMidY;
    private static final long serialVersionUID = 4712279332004017414L;
    public int getBSPosX() {
        return bsPosX;
    }
    public void setBSPosX(int bsPosX) {
        this.bsPosX = bsPosX;
    }
    public int getBSPosY() {
        return bsPosY;
    }
    public void setBSPosY(int bsPosY) {
        this.bsPosY = bsPosY;
    }
    public int getBSMidX() {
        return bsMidX;
    }
    public void setBSMidX(int bsMidX) {
        this.bsMidX = bsMidX;
    }
    public int getBSMidY() {
        return bsMidY;
    }
    public void setBSMidY(int bsMidY) {
        this.bsMidY = bsMidY;
    }

}
