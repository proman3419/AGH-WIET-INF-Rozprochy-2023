package smart.home.devices;

import SmartHome.*;
import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class FridgeWithShoppingListImpl extends FridgeImpl implements FridgeWithShoppingList {
    private final List<ShoppingListRecord> shoppingList = new ArrayList<>();

    public FridgeWithShoppingListImpl(float targetTemperature, float currentTemperature) {
        super(targetTemperature, currentTemperature);
    }

    @Override
    public OrderedShoppingListRecord[] getShoppingList(Current current) throws InStandbyModeError {
        notifyIfInStandbyMode(current);
        return getOrderedShoppingListRecords();
    }

    @Override
    public ShoppingListRecord addShoppingListRecord(ShoppingListRecord record, Current current) throws InStandbyModeError {
        notifyIfInStandbyMode(current);
        shoppingList.add(record);
        return record;
    }

    @Override
    public ShoppingListRecord removeShoppingListRecord(int id, Current current) throws InStandbyModeError, InvalidIndexError {
        notifyIfInStandbyMode(current);
        if (id < 0 || shoppingList.size() <= id) {
            throw new InvalidIndexError();
        }
        return shoppingList.remove(id);
    }

    private OrderedShoppingListRecord[] getOrderedShoppingListRecords() {
        return IntStream.range(0, shoppingList.size())
                .mapToObj(i -> new OrderedShoppingListRecord(i, shoppingList.get(i)))
                .toArray(OrderedShoppingListRecord[]::new);
    }
}
