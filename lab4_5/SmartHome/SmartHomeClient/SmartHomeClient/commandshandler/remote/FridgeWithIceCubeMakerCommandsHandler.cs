using Ice;
using SmartHomeIce;
using SmartHomeClient.SmartHome;

namespace SmartHomeClient.commandshandler.remote
{
    internal class FridgeWithIceCubeMakerCommandsHandler : FridgeCommandsHandler
    {
        public FridgeWithIceCubeMakerCommandsHandler(Communicator communicator) : base(communicator)
        {
        }

        public override bool HandleCommand(Command command)
        {
            FridgeWithIceCubeMakerPrx proxy = FridgeWithIceCubeMakerPrxHelper.checkedCast(GetProxy(command.command));
            if (proxy != null)
            {
                switch (command.action)
                {
                    case "getIceCubesMakerCapacity":
                        int iceCubesMakerCapacity = proxy.getIceCubesMakerCapacity();
                        Console.WriteLine($"Ice cubes maker capacity: {iceCubesMakerCapacity}");
                        return true;
                    case "getIceCubes":
                        if (!int.TryParse(command.arguments[0], out int count))
                        {
                            Console.Error.WriteLine("Invalid ice cubes count value");
                        }
                        else
                        {
                            try
                            {
                                int receivedIceCubes = proxy.getIceCubes(count);
                                Console.WriteLine($"Received {receivedIceCubes} ice cubes");
                            }
                            catch (NotEnoughIceCubesError e)
                            {
                                Console.Error.WriteLine("There is not enough ice cubes in the ice maker");
                            }
                        }
                        return true;
                    case "getIceCubesCount":
                        int iceCubesCount = proxy.getIceCubesCount();
                        Console.WriteLine($"There is {iceCubesCount} ice cubes in the ice cubes maker");
                        return true;
                }
                return base.HandleCommand(command);
            }
            return false;
        }
    }
}
