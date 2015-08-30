package wban.simulate;

import wban.simulate.config.BaseStationConfig;

public class BaseStation {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1858482441313029174L;
    @SuppressWarnings("unused")
    private BaseStationConfig baseStationConfig = null;
    public BaseStation(BaseStationConfig config) {
        baseStationConfig = config;
    }

}
