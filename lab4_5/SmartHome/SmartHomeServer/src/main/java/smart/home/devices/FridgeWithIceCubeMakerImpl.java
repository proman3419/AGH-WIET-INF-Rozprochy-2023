package smart.home.devices;

import SmartHome.FridgeWithIceCubeMaker;
import SmartHome.InStandbyModeError;
import SmartHome.NotEnoughIceCubesError;
import com.zeroc.Ice.Current;
import smart.home.server.Helper;

public class FridgeWithIceCubeMakerImpl extends FridgeImpl implements FridgeWithIceCubeMaker {
    private static final int ICE_CUBES_MAKER_CAPACITY = 100;
    private int iceCubesCount;

    public FridgeWithIceCubeMakerImpl(float targetTemperature, float currentTemperature, int iceCubesCount) {
        super(targetTemperature, currentTemperature);
        this.iceCubesCount = checkIfValidIceCubesCount(iceCubesCount) ? iceCubesCount : 0;
    }

    @Override
    public int getIceCubesMakerCapacity(Current current) {
        return ICE_CUBES_MAKER_CAPACITY;
    }

    @Override
    public int getIceCubes(int count, Current current) throws InStandbyModeError, NotEnoughIceCubesError {
        notifyIfInStandbyMode(current);
        if (!Helper.checkIfValueInRange(count, 1, iceCubesCount)) {
            throw new NotEnoughIceCubesError();
        }
        iceCubesCount -= count;
        return count;
    }

    @Override
    public int getIceCubesCount(Current current) throws InStandbyModeError {
        notifyIfInStandbyMode(current);
        return iceCubesCount;
    }

    private boolean checkIfValidIceCubesCount(int iceCubesCount) {
        return Helper.checkIfValueInRange(iceCubesCount, 0, ICE_CUBES_MAKER_CAPACITY);
    }
}
