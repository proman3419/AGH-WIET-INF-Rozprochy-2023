package smart.home.devices;

import SmartHome.InStandbyModeError;
import SmartHome.Mode;
import SmartHome.ModeNotChangedError;
import SmartHome.SmartDevice;
import com.zeroc.Ice.Current;

public class SmartDeviceImpl implements SmartDevice {
    private Mode mode = Mode.On;

    @Override
    public Mode setMode(Mode mode, Current current) throws ModeNotChangedError {
        this.mode = mode;
        return mode;
    }

    @Override
    public Mode getMode(Current current) {
        return mode;
    }

    @Override
    public void notifyIfInStandbyMode(Current current) throws InStandbyModeError {
        if (Mode.Standby == mode) {
            throw new InStandbyModeError();
        }
    }
}
