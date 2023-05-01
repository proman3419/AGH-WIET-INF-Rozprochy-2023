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

public class ServantLocatorImpl implements ServantLocator {
    private static final Logger LOGGER = LogManager.getLogger(ServantLocatorImpl.class);

    @Override
    public LocateResult locate(Current current) throws UserException {
        String name = current.id.name;
        LOGGER.info("locate '{}'", name);
        ObjectAdapter adapter = current.adapter;

        switch (name) {
            case "CO2LevelSensor1":
                CO2LevelSensor co2LevelSensor = new CO2LevelSensorImpl(237);
                adapter.add(co2LevelSensor, new Identity(name, "CO2LevelSensor"));
                return new ServantLocator.LocateResult(co2LevelSensor, null);
            case "Fridge1":
                FridgeImpl fridge = new FridgeImpl(5, 8);
                adapter.add(fridge, new Identity(name, "Fridge"));
                return new ServantLocator.LocateResult(fridge, null);
            case "FridgeWithIceCubeMaker1":
                FridgeWithIceCubeMakerImpl fridgeWithIceCubeMaker = new FridgeWithIceCubeMakerImpl(5, 8, 42);
                adapter.add(fridgeWithIceCubeMaker, new Identity(name, "FridgeWithIceCubeMaker"));
                return new ServantLocator.LocateResult(fridgeWithIceCubeMaker, null);
            case "FridgeWithShoppingList1":
                FridgeWithShoppingListImpl fridgeWithShoppingList = new FridgeWithShoppingListImpl(5, 8);
                adapter.add(fridgeWithShoppingList, new Identity(name, "FridgeWithShoppingList"));
                return new ServantLocator.LocateResult(fridgeWithShoppingList, null);
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
