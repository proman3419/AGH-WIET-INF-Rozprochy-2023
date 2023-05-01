﻿using Ice;
using SmartHome;

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
                        int.TryParse(command.arguments[0], out int count);

                        int receivedIceCubes = proxy.getIceCubes(count);
                        Console.WriteLine($"Received {receivedIceCubes} ice cubes");
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
