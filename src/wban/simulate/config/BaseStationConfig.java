package wban.simulate.config;

import java.io.Serializable;

public class BaseStationConfig implements Serializable {

    private static final long serialVersionUID = 4712279332004017414L;
    int bsPosX;
    int bsPosY;
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

}
