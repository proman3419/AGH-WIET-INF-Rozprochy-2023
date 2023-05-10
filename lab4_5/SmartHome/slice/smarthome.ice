#ifndef SMARTHOME_ICE
#define SMARTHOME_ICE

module SmartHomeIce
{
    enum Mode
    {
        On, // Permitted: all actions
        Standby // Permitted: setting, getting device's parameters
                // Not permitted: performing device's activities
    };
    exception ModeNotChangedError {}; // The new mode is the same as the current one
    exception InStandbyModeError {}; // Attempted to perform a not permitted action

    interface SmartDevice
    {
        // Parameters
        idempotent Mode setMode(Mode mode) throws ModeNotChangedError;
        idempotent Mode getMode();
        idempotent void notifyIfInStandbyMode() throws InStandbyModeError;
    };

    interface CO2LevelSensor extends SmartDevice
    {
        // Activities
        idempotent int getCO2LevelInPPM() throws InStandbyModeError; // parts-per-million
        idempotent bool isCO2LevelSafe() throws InStandbyModeError;
    };

    exception TemperatureOutOfSupportedRangeError {};
    interface Fridge extends SmartDevice
    {
        // Parameters
        idempotent float setTargetTemperature(float temperature) throws TemperatureOutOfSupportedRangeError;
        idempotent float getTargetTemperature();

        // Activities
        idempotent float getCurrentTemperature() throws InStandbyModeError;
    };

    exception NotEnoughIceCubesError {};
    interface FridgeWithIceCubeMaker extends Fridge
    {
        // Parameters
        idempotent int getIceCubesMakerCapacity();

        // Activities
        int getIceCubes(int count) throws InStandbyModeError, NotEnoughIceCubesError;
        idempotent int getIceCubesCount() throws InStandbyModeError;
    };

    enum Unit { Unspecified, Gram, Millilitre };
    interface ShoppingListRecord
    {
        string name;
        int quantity;
        Unit unit;
    };

    interface OrderedShoppingListRecord
    {
        int id;
        ShoppingListRecord shoppingListRecord;
    };
    sequence<OrderedShoppingListRecord> orderedShoppingList;

    exception IndexOutOfListRangeError {};
    interface FridgeWithShoppingList extends Fridge
    {
        // Activities
        idempotent orderedShoppingList getShoppingList() throws InStandbyModeError;
        ShoppingListRecord addShoppingListRecord(ShoppingListRecord record) throws InStandbyModeError;
        ShoppingListRecord removeShoppingListRecord(int id) throws InStandbyModeError, IndexOutOfListRangeError;
    };
};

#endif
