namespace SmartHomeClient
{
    internal class Command
    {
        private readonly char[] DELIMITERS = new char[] { ' ', '\'' };
        public string command { get; }
        public string[] arguments { get; }

        public Command(string commandRaw) 
        {
            string[] temp = commandRaw.Split(DELIMITERS, 2);
            command = temp[0].Trim();
            arguments = commandRaw
                .Split(DELIMITERS, System.StringSplitOptions.RemoveEmptyEntries)
                .Skip(1)
                .Select(x => x.Trim())
                .ToArray();
        }

        public string GetProxyName()
        {
            return command + ".Proxy";
        }
    }
}
