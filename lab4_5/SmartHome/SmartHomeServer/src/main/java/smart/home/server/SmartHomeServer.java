package smart.home.server;

import SmartHome.CO2LevelSensor;
import com.zeroc.Ice.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Exception;

public class SmartHomeServer {
    private static final Logger LOGGER = LogManager.getLogger(SmartHomeServer.class);
    private static final String ADAPTER_NAME = "adapter";
    private static final String ADAPTER_OPTIONS = "default -h localhost -p 10000";
    private static final String SERVANT_LOCATOR_PREFIX = "";
    private final Communicator communicator;
    private final ObjectAdapter adapter;
    private final ServantLocatorImpl servantLocator;

    public SmartHomeServer(String[] args) {
        this.communicator = Util.initialize(args);
        this.adapter = communicator.createObjectAdapterWithEndpoints(ADAPTER_NAME, ADAPTER_OPTIONS);
        this.servantLocator = new ServantLocatorImpl();
        adapter.addServantLocator(servantLocator, SERVANT_LOCATOR_PREFIX);
        LOGGER.info("Initialized the server");
    }

    public void run() {
        adapter.activate();
        LOGGER.info("Entering event processing loop...");

        int status = 0;
        boolean quit = false;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (!quit) {
            String line;
            try {
                line = in.readLine();
            } catch (IOException e) {
                break;
            }

            switch (line) {
                case "devices":
                    servantLocator.printServants();
                    break;
                case "quit":
                    quit = true;
                    try {
                        adapter.deactivate();
                        communicator.shutdown();
                        communicator.destroy();
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage());
                        status = 1;
                    }
                    break;
                default:
                    LOGGER.info("Invalid command");
            }
        }
        System.exit(status);
    }
}
