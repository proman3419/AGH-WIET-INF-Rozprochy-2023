using Ice;

namespace SmartHomeClient.commandshandler
{
    internal class SmartDeviceCommandsHandler : CommandsHandler
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
