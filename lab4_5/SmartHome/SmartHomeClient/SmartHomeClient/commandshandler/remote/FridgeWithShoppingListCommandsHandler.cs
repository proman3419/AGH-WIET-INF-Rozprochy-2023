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
                        string name = command.arguments[0];
                        int.TryParse(command.arguments[1], out int quantity);
                        Enum.TryParse("Active", out Unit unit);
                        ShoppingListRecord record = new ShoppingListRecord(name, quantity, unit);

                        ShoppingListRecord addedRecord = proxy.addShoppingListRecord(record);
                        Console.WriteLine("Added the record to the shopping list");
                        Console.WriteLine($"{addedRecord.ToPrettyString()}");
                        return true;
                    case "removeShoppingListRecord":
                        int.TryParse(command.arguments[0], out int id);

                        ShoppingListRecord removedRecord = proxy.removeShoppingListRecord(id);
                        Console.WriteLine("Removed the record to the shopping list");
                        Console.WriteLine($"{removedRecord.ToPrettyString()}");
                        return true;
                }
            }
            base.HandleCommand(command);
            return false;
        }
    }
}
