using Ice;

namespace SmartHomeClient.commandshandler.remote
{
    internal class SmartDeviceCommandsHandler : RemoteCommandsHandler
    {
        public SmartDeviceCommandsHandler(Communicator communicator) : base(communicator)
        {
        }

        public override bool HandleCommand(Command command)
        {
            throw new NotImplementedException();
        }
    }
}
