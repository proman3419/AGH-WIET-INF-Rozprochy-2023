using SmartHome;

namespace SmartHomeClient
{
    public class Program
    {
        static string[] AddConfigToArgs(string[] args)
        {
            string[] result = new string[args.Length + 1];
            int i = 0;
            for (; i < args.Length; i++)
            {
                result[i] = args[i];
            }
            result[i] = "--Ice.Config=client.config";
            return result;
        }

        public static int Main(string[] args)
        {
            string[] argsWithConfig = AddConfigToArgs(args);
            try
            {
                using (Ice.Communicator communicator = Ice.Util.initialize(ref argsWithConfig))
                {
                    for (; ; )
                    {
                        Command command = new Command(Console.ReadLine());

                        if (command.command.StartsWith("CO2LevelSensor"))
                        {
                            CO2LevelSensorPrx proxy = CO2LevelSensorPrxHelper.checkedCast(communicator.propertyToProxy(command.GetProxyName()));
                            if (proxy != null)
                            {
                                switch (command.arguments[0])
                                {
                                    case "getCO2LevelInPPM":
                                        int co2LevelInPPM = proxy.getCO2LevelInPPM();
                                        Console.WriteLine($"CO2 level: {co2LevelInPPM}[PPM]");
                                        break;
                                    case "isCO2LevelSafe":
                                        bool isCO2LevelSafe = proxy.isCO2LevelSafe();
                                        Console.WriteLine($"CO2 level is safe: {isCO2LevelSafe}");
                                        break;
                                }
                            }
                        }
                        else if (command.command.StartsWith("FridgeWithShoppingList"))
                        {
                            FridgeWithShoppingListPrx proxy = FridgeWithShoppingListPrxHelper.checkedCast(communicator.propertyToProxy(command.GetProxyName()));
                            if (proxy != null)
                            {
                                switch (command.arguments[0])
                                {
                                    case "getShoppingList":
                                        OrderedShoppingListRecord[] shoppingList = proxy.getShoppingList();
                                        Console.WriteLine("BEGIN Shopping list");
                                        foreach (OrderedShoppingListRecord oshr in shoppingList)
                                        {
                                            ShoppingListRecord shr = oshr.shoppingListRecord;
                                            Console.WriteLine($"Id: {oshr.id} | {shr.ToPrettyString()}");
                                        }
                                        Console.WriteLine("END Shopping list");
                                        break;
                                    case "addShoppingListRecord":
                                        string name = command.arguments[1];
                                        Int32.TryParse(command.arguments[2], out int quantity);
                                        Enum.TryParse("Active", out Unit unit);
                                        ShoppingListRecord record = new ShoppingListRecord(name, quantity, unit);

                                        ShoppingListRecord addedRecord = proxy.addShoppingListRecord(record);
                                        Console.WriteLine("Added the record to the shopping list");
                                        Console.WriteLine($"{addedRecord.ToPrettyString()}");
                                        break;
                                    case "removeShoppingListRecord":
                                        Int32.TryParse(command.arguments[1], out int id);

                                        ShoppingListRecord removedRecord = proxy.removeShoppingListRecord(id);
                                        Console.WriteLine("Removed the record to the shopping list");
                                        Console.WriteLine($"{removedRecord.ToPrettyString()}");
                                        break;
                                }
                            }
                        }
                        else if (command.command.StartsWith("FridgeWithIceCubeMaker"))
                        {
                            FridgeWithIceCubeMakerPrx proxy = FridgeWithIceCubeMakerPrxHelper.checkedCast(communicator.propertyToProxy(command.GetProxyName()));
                            if (proxy != null)
                            {
                                switch (command.arguments[0])
                                {
                                    case "getIceCubesMakerCapacity":
                                        int iceCubesMakerCapacity = proxy.getIceCubesMakerCapacity();
                                        Console.WriteLine($"Ice cubes maker capacity: {iceCubesMakerCapacity}");
                                        break;
                                    case "getIceCubes":
                                        Int32.TryParse(command.arguments[1], out int count);

                                        int receivedIceCubes = proxy.getIceCubes(count);
                                        Console.WriteLine($"Received {receivedIceCubes} ice cubes");
                                        break;
                                    case "getIceCubesCount":
                                        int iceCubesCount = proxy.getIceCubesCount();
                                        Console.WriteLine($"There is {iceCubesCount} ice cubes in the ice cubes maker");
                                        break;
                                }
                            }
                        }
                        else if (command.command.StartsWith("Fridge"))
                        {
                            FridgePrx proxy = FridgePrxHelper.checkedCast(communicator.propertyToProxy(command.GetProxyName()));
                            if (proxy != null)
                            {
                                switch (command.arguments[0])
                                {
                                    case "setTargetTemperature":
                                        float.TryParse(command.arguments[1], out float temperature);

                                        float setTargetTemperature = proxy.setTargetTemperature(temperature);
                                        Console.WriteLine($"Set target temperature to: {setTargetTemperature}[C]");
                                        break;
                                    case "getTargetTemperature":
                                        float targetTemperature = proxy.getTargetTemperature();
                                        Console.WriteLine($"Target temperature: {targetTemperature}[C]");
                                        break;
                                    case "getCurrentTemperature":
                                        float currentTemperature = proxy.getCurrentTemperature();
                                        Console.WriteLine($"Current temperature: {currentTemperature}[C]");
                                        break;
                                }
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.Error.WriteLine(e);
                return 1;
            }
            return 0;
        }
    }
}
