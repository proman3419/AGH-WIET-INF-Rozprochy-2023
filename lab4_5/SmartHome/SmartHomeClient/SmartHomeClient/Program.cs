using SmartHomeClient.commandshandler;
using SmartHomeClient.commandshandler.local;
using SmartHomeClient.commandshandler.remote;

namespace SmartHomeClient
{
    public class Program
    {
        public static bool terminate = false;

        static string[] AddConfigToArgs(string[] args)
        {
            string[] result = new string[args.Length + 1];
            int i = 0;
            for (; i < args.Length; i++)
            {
                result[i] = args[i];
            }
            result[i] = "--Ice.Config=client.config";
            return result;
        }

        public static int Main(string[] args)
        {
            string[] argsWithConfig = AddConfigToArgs(args);
            try
            {
                using (Ice.Communicator communicator = Ice.Util.initialize(ref argsWithConfig))
                {
                    CommandsHandler[] commandsHandlers = new CommandsHandler[] {
                        new LocalCommandsHandler(),
                        new SmartDeviceCommandsHandler(communicator),
                        new CO2LevelSensorCommandsHandler(communicator),
                        new FridgeCommandsHandler(communicator),
                        new FridgeWithIceCubeMakerCommandsHandler(communicator),
                        new FridgeWithShoppingListCommandsHandler(communicator)
                    };
                    while (!terminate)
                    {
                        Command command = new Command(Console.ReadLine());
                        foreach (CommandsHandler commandsHandler in commandsHandlers)
                        {
                            bool handled = commandsHandler.HandleCommand(command);
                            if (handled)
                            {
                                break;
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.Error.WriteLine(e);
                return 1;
            }
            return 0;
        }
    }
}
