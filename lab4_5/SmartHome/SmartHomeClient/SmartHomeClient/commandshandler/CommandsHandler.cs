using Ice;

namespace SmartHomeClient.commandshandler
{
    internal abstract class CommandsHandler
    {
        private const string PROXY_NAME_SUFFIX = ".Proxy";
        protected readonly Ice.Communicator _communicator;
        //protected readonly string _proxyNameBase;

        //protected CommandsHandler(Communicator communicator, string proxyNameBase)
        protected CommandsHandler(Communicator communicator)
        {
            _communicator = communicator;
            //_proxyNameBase = proxyNameBase;
        }

        public abstract bool HandleCommand(Command command);

        //private string GetProxyId(string proxyName)
        //{
        //    string id = "";
        //    for (int i = proxyName.Length - 1; i >= 0; i--)
        //    {
        //        if (Char.IsDigit(proxyName[i]))
        //        {
        //            id += proxyName[i];
        //        }
        //        else
        //        {
        //            break;
        //        }
        //    }
        //    char[] charArray = id.ToCharArray();
        //    Array.Reverse(charArray);
        //    return new string(charArray);
        //}

        //protected string GetFullProxyNameDerivedCallAware(string proxyName, bool isDerivedCall)
        //{
        //    proxyName = isDerivedCall ? _proxyNameBase + GetProxyId(proxyName) : proxyName;
        //    return proxyName + PROXY_NAME_SUFFIX;
        //}
    }
}
