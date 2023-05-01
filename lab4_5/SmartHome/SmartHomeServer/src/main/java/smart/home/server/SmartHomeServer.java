package smart.home.server;

import SmartHome.CO2LevelSensor;
import com.zeroc.Ice.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SmartHomeServer {
    private static final Logger LOGGER = LogManager.getLogger(SmartHomeServer.class);
    private static final String ADAPTER_NAME = "adapter";
    private static final String ADAPTER_OPTIONS = "default -h localhost -p 10000";
    private static final String SERVANT_LOCATOR_PREFIX = "";
    private final Communicator communicator;
    private final ObjectAdapter adapter;

    public SmartHomeServer(String[] args) {
        this.communicator = Util.initialize(args);
        this.adapter = communicator.createObjectAdapterWithEndpoints(ADAPTER_NAME, ADAPTER_OPTIONS);
        ServantLocator servantLocator = new ServantLocatorImpl();
        adapter.addServantLocator(servantLocator, SERVANT_LOCATOR_PREFIX);
        LOGGER.info("Initialized the server");
    }

    public void run() {
        adapter.activate();
        LOGGER.info("Entering event processing loop...");
        communicator.waitForShutdown();
    }
}
