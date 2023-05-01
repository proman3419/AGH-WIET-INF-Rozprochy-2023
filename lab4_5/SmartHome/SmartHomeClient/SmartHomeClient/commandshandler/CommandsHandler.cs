using Ice;

namespace SmartHomeClient.commandshandler
{
    internal abstract class CommandsHandler
    {
        public abstract bool HandleCommand(Command command);
    }
}
