using Ice;

namespace SmartHomeClient.commandshandler.local
{
    internal class LocalCommandsHandler : CommandsHandler
    {
        public override bool HandleCommand(Command command)
        {
            if (command.command == "!")
            {
                switch (command.action)
                {
                    case "quit":
                        Program.terminate = true;
                        return true;
                }
            }
            return false;
        }
    }
}
