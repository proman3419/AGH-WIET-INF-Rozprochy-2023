package smart.home.devices;

import SmartHome.CO2LevelSensor;
import com.zeroc.Ice.Current;
import smart.home.server.Helper;

import java.util.concurrent.ThreadLocalRandom;

public class CO2LevelSensorImpl extends SmartDeviceImpl implements CO2LevelSensor {
    private static final int SAFE_LEVEL_THRESHOLD = 5000;
    private static final int CHANGE_LEVEL_MIN_VALUE = -5;
    private static final int CHANGE_LEVEL_MAX_VALUE = 5;
    private static final double CHANGE_LEVEL_PROBABILITY = 0.8;
    private int co2Level;

    public CO2LevelSensorImpl(int co2Level) {
        this.co2Level = co2Level;
    }

    @Override
    public int getCO2LevelInPPM(Current current) throws SmartHome.InStandbyModeError {
        notifyIfInStandbyMode(current);
        // Simulate changing value
        int co2LevelDelta = Math.random() <= CHANGE_LEVEL_PROBABILITY ?
                Helper.getRandomIntFromRange(CHANGE_LEVEL_MIN_VALUE, CHANGE_LEVEL_MAX_VALUE) : 0;
        int newCO2Level = co2Level + co2LevelDelta;
        if (newCO2Level >= 0) {
            co2Level = newCO2Level;
        }
        return co2Level;
    }

    @Override
    public boolean isCO2LevelSafe(Current current) throws SmartHome.InStandbyModeError {
        return SAFE_LEVEL_THRESHOLD >= getCO2LevelInPPM(current);
    }
}
