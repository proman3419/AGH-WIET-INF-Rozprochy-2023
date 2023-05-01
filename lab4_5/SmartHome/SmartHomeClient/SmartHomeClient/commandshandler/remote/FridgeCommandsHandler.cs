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
                        float.TryParse(command.arguments[0], out float temperature);

                        float setTargetTemperature = proxy.setTargetTemperature(temperature);
                        Console.WriteLine($"Set target temperature to: {setTargetTemperature}[C]");
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
