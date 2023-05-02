package smart.home.server;

import SmartHome.CO2LevelSensor;
import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import smart.home.devices.CO2LevelSensorImpl;
import smart.home.devices.FridgeImpl;
import smart.home.devices.FridgeWithIceCubeMakerImpl;
import smart.home.devices.FridgeWithShoppingListImpl;

import java.util.ArrayList;
import java.util.List;

public class ServantLocatorImpl implements ServantLocator {
    private static final Logger LOGGER = LogManager.getLogger(ServantLocatorImpl.class);
    private final List<String> servantsNames = new ArrayList<>();
    private final String serverId;

    public ServantLocatorImpl(String serverId) {
        this.serverId = serverId;
    }

    @Override
    public LocateResult locate(Current current) throws UserException {
        String servantName = current.id.name;
        LOGGER.info("Locate '{}'", servantName);
        ObjectAdapter adapter = current.adapter;

        if (getServantId(servantName).equals(serverId)) {
            String servantBaseName = getServantBaseName(servantName);
            servantsNames.add(servantName);
            switch (servantBaseName) {
                case "CO2LevelSensor":
                    CO2LevelSensor co2LevelSensor = new CO2LevelSensorImpl(237);
                    adapter.add(co2LevelSensor, new Identity(servantName, "CO2LevelSensor"));
                    return new ServantLocator.LocateResult(co2LevelSensor, null);
                case "Fridge":
                    FridgeImpl fridge = new FridgeImpl(5, 8);
                    adapter.add(fridge, new Identity(servantName, "Fridge"));
                    return new ServantLocator.LocateResult(fridge, null);
                case "FridgeWithIceCubeMaker":
                    FridgeWithIceCubeMakerImpl fridgeWithIceCubeMaker = new FridgeWithIceCubeMakerImpl(5, 8, 42);
                    adapter.add(fridgeWithIceCubeMaker, new Identity(servantName, "FridgeWithIceCubeMaker"));
                    return new ServantLocator.LocateResult(fridgeWithIceCubeMaker, null);
                case "FridgeWithShoppingList":
                    FridgeWithShoppingListImpl fridgeWithShoppingList = new FridgeWithShoppingListImpl(5, 8);
                    adapter.add(fridgeWithShoppingList, new Identity(servantName, "FridgeWithShoppingList"));
                    return new ServantLocator.LocateResult(fridgeWithShoppingList, null);
            }
        }
        throw new RuntimeException("Invalid servant name");
    }

    @Override
    public void deactivate(String s) {

    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) throws UserException {

    }

    public void printServants() {
        LOGGER.info("Devices connected to the server:");
        for (String name : servantsNames) {
            LOGGER.info("* {}", name);
        }
    }

    private int getFirstServantIdIndex(String servantName) {
        int i = servantName.length();
        while (i > 0 && Character.isDigit(servantName.charAt(i - 1))) {
            i--;
        }
        return i;
    }

    private String getServantBaseName(String servantName) {
        return servantName.substring(0, getFirstServantIdIndex(servantName));
    }

    private String getServantId(String servantName) {
        return servantName.substring(getFirstServantIdIndex(servantName));
    }
}
