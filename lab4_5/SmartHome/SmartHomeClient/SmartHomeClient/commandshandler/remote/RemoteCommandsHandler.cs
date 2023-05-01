using Ice;

namespace SmartHomeClient.commandshandler.remote
{
    internal abstract class RemoteCommandsHandler : CommandsHandler
    {
        private const string PROXY_NAME_SUFFIX = ".Proxy";
        protected readonly Ice.Communicator _communicator;

        public RemoteCommandsHandler(Communicator communicator)
        {
            _communicator = communicator;
        }

        public override abstract bool HandleCommand(Command command);

        private string GetFullProxyName(string proxyName)
        {
            return proxyName + PROXY_NAME_SUFFIX;
        }

        protected ObjectPrx GetProxy(string proxyName)
        {
            return _communicator.propertyToProxy(GetFullProxyName(proxyName));
        }
    }
}
