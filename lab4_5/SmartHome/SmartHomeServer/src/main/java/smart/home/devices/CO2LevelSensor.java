package smart.home.devices;

import com.zeroc.Ice.Current;

public class CO2LevelSensor extends SmartDevice implements SmartHome.CO2LevelSensor {
    private static final int SAFE_LEVEL_THRESHOLD = 5000;
    private int co2Level;

    public CO2LevelSensor(int co2Level) {
        this.co2Level = co2Level;
    }

    @Override
    public int getCO2LevelInPPM(Current current) throws SmartHome.InStandbyModeError {
        return co2Level;
    }

    @Override
    public boolean isCO2LevelSafe(Current current) throws SmartHome.InStandbyModeError {
        return SAFE_LEVEL_THRESHOLD >= getCO2LevelInPPM(current);
    }
}
