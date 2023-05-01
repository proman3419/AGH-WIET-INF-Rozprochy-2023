package smart.home.devices;

import SmartHome.*;
import com.zeroc.Ice.Current;
import smart.home.server.Helper;

public class FridgeImpl extends SmartDeviceImpl implements Fridge {
    private static double MIN_SUPPORTED_TEMPERATURE = -20;
    private static double MAX_SUPPORTED_TEMPERATURE = 20;
    private static final double CHANGE_TEMPERATURE_MIN_VALUE = 0.1;
    private static final double CHANGE_TEMPERATURE_MAX_VALUE = 1.0;
    private static final double CHANGE_TEMPERATURE_PROBABILITY = 0.3;
    private float targetTemperature;
    private float currentTemperature;

    public FridgeImpl(float targetTemperature, float currentTemperature) {
        this.targetTemperature = targetTemperature;
        this.currentTemperature = currentTemperature;
    }

    @Override
    public float setTargetTemperature(float temperature, Current current) throws TemperatureOutOfSupportedRangeError {
        if (!checkIfTemperatureInSupportedRange(temperature)) {
            throw new TemperatureOutOfSupportedRangeError();
        }
        targetTemperature = temperature;
        return targetTemperature;
    }

    @Override
    public float getTargetTemperature(Current current) {
        return targetTemperature;
    }

    @Override
    public float getCurrentTemperature(Current current) throws InStandbyModeError {
        notifyIfInStandbyMode(current);
        // Simulate changing value
        double temperatureDelta = Math.random() <= CHANGE_TEMPERATURE_PROBABILITY ?
                Helper.getRandomDoubleFromRange(CHANGE_TEMPERATURE_MIN_VALUE, CHANGE_TEMPERATURE_MAX_VALUE) : 0;
        double missingTemperature = Math.abs(currentTemperature - targetTemperature);
        if (missingTemperature < temperatureDelta) {
            temperatureDelta = missingTemperature;
        }
        if (currentTemperature > targetTemperature) {
            temperatureDelta *= -1;
        }
        double newTemperature = currentTemperature + temperatureDelta;
        if (checkIfTemperatureInSupportedRange(newTemperature)) {
            currentTemperature = (float) newTemperature;
        }
        return currentTemperature;
    }

    private boolean checkIfTemperatureInSupportedRange(double temperature) {
        return Helper.checkIfValueInRange(temperature, MIN_SUPPORTED_TEMPERATURE, MAX_SUPPORTED_TEMPERATURE);
    }
}
