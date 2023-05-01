namespace SmartHomeClient
{
    internal class Command
    {
        private readonly char[] DELIMITERS = new char[] { ' ', '\'' };
        public string command { get; }
        public string action { get; }
        public string[] arguments { get; }

        public Command(string commandRaw) 
        {
            string[] temp = commandRaw.Split(DELIMITERS, 3);
            command = temp[0].Trim();
            action = temp[1].Trim();
            arguments = commandRaw
                .Split(DELIMITERS, System.StringSplitOptions.RemoveEmptyEntries)
                .Skip(2)
                .Select(x => x.Trim())
                .ToArray();
        }
    }
}
