﻿
using Ice;
using SmartHome;

namespace SmartHomeClient.commandshandler
{
    internal class CO2LevelSensorCommandsHandler : SmartDeviceCommandsHandler
    {
        public CO2LevelSensorCommandsHandler(Communicator communicator) : base(communicator)
        {
        }

        public override bool HandleCommand(Command command)
        {
            CO2LevelSensorPrx proxy = CO2LevelSensorPrxHelper.checkedCast(_communicator.propertyToProxy(command.GetProxyName()));
            if (proxy != null)
            {
                switch (command.action)
                {
                    case "getCO2LevelInPPM":
                        int co2LevelInPPM = proxy.getCO2LevelInPPM();
                        Console.WriteLine($"CO2 level: {co2LevelInPPM}[PPM]");
                        return true;
                    case "isCO2LevelSafe":
                        bool isCO2LevelSafe = proxy.isCO2LevelSafe();
                        Console.WriteLine($"CO2 level is safe: {isCO2LevelSafe}");
                        return true;
                }
            }
            return false;
        }
    }
}
