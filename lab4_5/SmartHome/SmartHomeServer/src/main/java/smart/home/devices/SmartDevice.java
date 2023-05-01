package smart.home.devices;

import SmartHome.Mode;
import SmartHome.ModeNotChangedError;
import com.zeroc.Ice.Current;

public class SmartDevice implements SmartHome.SmartDevice {
    private Mode mode;

    @Override
    public void setMode(Mode mode, Current current) throws ModeNotChangedError {
        this.mode = mode;
    }

    @Override
    public Mode getMode(Current current) {
        return mode;
    }

    @Override
    public boolean isInStandbyMode(Current current) {
        return Mode.Standby == mode;
    }
}
