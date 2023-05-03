using Ice;
using SmartHomeClient.SmartHome;

namespace SmartHomeClient.commandshandler
{
    internal abstract class CommandsHandler
    {
        public abstract bool HandleCommand(Command command);
    }
}
