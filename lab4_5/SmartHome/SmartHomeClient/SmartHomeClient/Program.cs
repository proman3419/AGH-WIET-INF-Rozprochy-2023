using SmartHome;
using SmartHomeClient.commandshandler;
using SmartHomeClient.commandshandler.local;
using SmartHomeClient.commandshandler.remote;

namespace SmartHomeClient
{
    public class Program
    {
        public static bool quit = false;

        private static string[] AddConfigToArgs(string[] args)
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

        private static void MainLoop(CommandsHandler[] commandsHandlers)
        {
            while (!quit)
            {
                bool handled = false;
                Command command;
                Console.Write("> ");
                try
                {
                    command = new Command(Console.ReadLine());
                }
                catch (IndexOutOfRangeException e)
                {
                    Console.Error.WriteLine("Invalid command");
                    continue;
                }
                foreach (CommandsHandler commandsHandler in commandsHandlers)
                {
                    try
                    {
                        handled = commandsHandler.HandleCommand(command);
                    }
                    catch (InStandbyModeError e)
                    {
                        // All SmartDevices can throw the Exception
                        Console.Error.WriteLine("The device is in standby mode");
                        break;
                    }
                    if (handled)
                    {
                        break;
                    }
                }
                if (!handled)
                {
                    Console.Error.WriteLine("Couldn't find a handler to handle the command");
                }
            }
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
                    MainLoop(commandsHandlers);
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
