using Ice;
using SmartHome;

namespace SmartHomeClient.commandshandler.remote
{
    internal class FridgeWithShoppingListCommandsHandler : FridgeCommandsHandler
    {
        public FridgeWithShoppingListCommandsHandler(Communicator communicator) : base(communicator)
        {
        }

        public override bool HandleCommand(Command command)
        {
            FridgeWithShoppingListPrx proxy = FridgeWithShoppingListPrxHelper.checkedCast(GetProxy(command.command));
            if (proxy != null)
            {
                switch (command.action)
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
                        return true;
                    case "addShoppingListRecord":
                        try
                        {
                            string name = command.arguments[0];
                            if (!int.TryParse(command.arguments[1], out int quantity))
                            {
                                Console.Error.WriteLine("Invalid quantity value");
                            }
                            else
                            {
                                if (!Enum.TryParse(command.arguments[2], out Unit unit))
                                {
                                    Console.Error.WriteLine("Invalid unit value");
                                }
                                else
                                {
                                    ShoppingListRecord record = new ShoppingListRecord(name, quantity, unit);

                                    ShoppingListRecord addedRecord = proxy.addShoppingListRecord(record);
                                    Console.WriteLine("Added the record to the shopping list");
                                    Console.WriteLine($"{addedRecord.ToPrettyString()}");
                                }
                            }
                        }
                        catch (IndexOutOfRangeException e)
                        {
                            Console.Error.WriteLine("Not enough arguments to create a ShoppingListRecord");
                        }
                        return true;
                    case "removeShoppingListRecord":
                        if (!int.TryParse(command.arguments[0], out int id))
                        {
                            Console.Error.WriteLine("Invalid id value");
                        }
                        else
                        {
                            try
                            {
                                ShoppingListRecord removedRecord = proxy.removeShoppingListRecord(id);
                                Console.WriteLine("Removed the record from the shopping list");
                                Console.WriteLine($"{removedRecord.ToPrettyString()}");
                            }
                            catch (IndexOutOfListRangeError e)
                            {
                                Console.Error.WriteLine("Index out of shopping list range");
                            }
                        }
                        return true;
                }
            }
            base.HandleCommand(command);
            return false;
        }
    }
}
