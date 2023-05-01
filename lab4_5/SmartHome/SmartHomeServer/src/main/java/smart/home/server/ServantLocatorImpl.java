package smart.home.server;

import SmartHome.CO2LevelSensor;
import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServantLocatorImpl implements ServantLocator {
    private static final Logger LOGGER = LogManager.getLogger(ServantLocatorImpl.class);

    @Override
    public LocateResult locate(Current current) throws UserException {
        String name = current.id.name;
        LOGGER.info("locate '{}'", name);
        ObjectAdapter adapter = current.adapter;

        switch (name) {
            case "CO2LevelSensor1":
                CO2LevelSensor co2LevelSensor = new smart.home.devices.CO2LevelSensor(237);
                adapter.add(co2LevelSensor, new Identity(name, "CO2LevelSensor"));
                return new ServantLocator.LocateResult(co2LevelSensor, null);
            default:
                throw new RuntimeException("Invalid name");
        }
    }

    @Override
    public void deactivate(String s) {

    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) throws UserException {

    }
}
