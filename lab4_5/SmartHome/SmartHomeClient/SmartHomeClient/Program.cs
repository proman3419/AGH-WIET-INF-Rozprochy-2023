using SmartHome;

namespace SmartHomeClient
{
    public class Program
    {
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
                    for (; ; )
                    {
                        Command command = new Command(Console.ReadLine());
                        if (command.command.StartsWith("CO2LevelSensor"))
                        {
                            CO2LevelSensorPrx proxy = CO2LevelSensorPrxHelper.checkedCast(communicator.propertyToProxy(command.GetProxyName()));
                            if (proxy != null)
                            {
                                if (command.arguments[0] == "getCO2LevelInPPM")
                                {
                                    int co2LevelInPPM = proxy.getCO2LevelInPPM();
                                    Console.WriteLine($"CO2 level: {co2LevelInPPM}[PPM]");
                                }
                                else if (command.arguments[0] == "isCO2LevelSafe")
                                {
                                    bool isCO2LevelSafe = proxy.isCO2LevelSafe();
                                    Console.WriteLine($"CO2 level is safe: {isCO2LevelSafe}");
                                }
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
