using Ice;
using SmartHome;

namespace SmartHomeClient.commandshandler.remote
{
    internal class FridgeCommandsHandler : SmartDeviceCommandsHandler
    {
        public FridgeCommandsHandler(Communicator communicator) : base(communicator)
        {
        }

        public override bool HandleCommand(Command command)
        {
            FridgePrx proxy = FridgePrxHelper.checkedCast(GetProxy(command.command));
            if (proxy != null)
            {
                switch (command.action)
                {
                    case "setTargetTemperature":
                        if (!float.TryParse(command.arguments[0], out float temperature))
                        {
                            Console.Error.WriteLine("Invalid temperature value");
                        }
                        else
                        {
                            try
                            {
                                float setTargetTemperature = proxy.setTargetTemperature(temperature);
                                Console.WriteLine($"Set target temperature to: {setTargetTemperature}[C]");
                            }
                            catch (TemperatureOutOfSupportedRangeError e)
                            {
                                Console.Error.WriteLine("Temperature out of supported range");
                            }
                        }
                        return true;
                    case "getTargetTemperature":
                        float targetTemperature = proxy.getTargetTemperature();
                        Console.WriteLine($"Target temperature: {targetTemperature}[C]");
                        return true;
                    case "getCurrentTemperature":
                        float currentTemperature = proxy.getCurrentTemperature();
                        Console.WriteLine($"Current temperature: {currentTemperature}[C]");
                        return true;
                }
            }
            return false;
        }
    }
}
