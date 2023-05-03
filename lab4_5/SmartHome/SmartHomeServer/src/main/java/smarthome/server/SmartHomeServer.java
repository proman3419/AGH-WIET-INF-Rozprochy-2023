package smarthome.server;

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
    private static final String SERVANT_LOCATOR_PREFIX = "";
    private final Communicator communicator;
    private final ObjectAdapter adapter;
    private final ServantLocatorImpl servantLocator;

    public SmartHomeServer(int serverId, String[] args) {
        this.communicator = Util.initialize(args);
        String adapterEndpoints = getAdapterEndpoints(serverId);
        this.adapter = communicator.createObjectAdapterWithEndpoints(ADAPTER_NAME, adapterEndpoints);
        LOGGER.info("Added adapter with endpoints '{}'", adapterEndpoints);
        this.servantLocator = new ServantLocatorImpl(String.valueOf(serverId));
        adapter.addServantLocator(servantLocator, SERVANT_LOCATOR_PREFIX);
        LOGGER.info("Initialized the server");
    }

    private String getAdapterEndpoints(int serverId) {
        int offset = 10;
        int offsetServerId = offset + serverId;
        return "tcp -h 127.0.0.!! -p 100!! : udp -h 127.0.0.!! -p 100!!"
                .replace("!!", String.valueOf(offsetServerId));
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
