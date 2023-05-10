using Ice;
using SmartHomeIce;
using SmartHomeClient.SmartHome;

namespace SmartHomeClient.commandshandler.remote
{
    internal class SmartDeviceCommandsHandler : RemoteCommandsHandler
    {
        public SmartDeviceCommandsHandler(Communicator communicator) : base(communicator)
        {
        }

        public override bool HandleCommand(Command command)
        {
            SmartDevicePrx proxy = SmartDevicePrxHelper.checkedCast(GetProxy(command.command));
            if (proxy != null)
            {
                switch (command.action)
                {
                    case "getMode":
                        Mode mode = proxy.getMode();
                        Console.WriteLine($"The device is currently in the {mode} mode");
                        return true;
                    case "setMode":
                        Enum.TryParse(command.arguments[0], out mode);
                        try
                        {
                            Mode setMode = proxy.setMode(mode);
                            Console.WriteLine($"Set the device's mode to {setMode}");
                        }
                        catch (ModeNotChangedError e)
                        {
                            Console.WriteLine("The new mode is the same as the current one, not changing");
                        }
                        return true;
                }
            }
            return false;
        }
    }
}
