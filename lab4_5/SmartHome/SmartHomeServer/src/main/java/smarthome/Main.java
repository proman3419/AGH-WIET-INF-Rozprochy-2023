package smarthome;

import smarthome.server.SmartHomeServer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import java.util.Arrays;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Configurator.initialize(new DefaultConfiguration());
        Configurator.setRootLevel(Level.INFO);

        if (args.length < 1) {
            LOGGER.error("Expected at least 1 argument: serverId");
        } else {
            try {
                int serverId = Integer.parseInt(args[0]);
                if (serverId < 0 || serverId > 89) {
                    LOGGER.error("serverId out of supported range [0, 89]");
                } else {
                    String[] iceArgs = Arrays.copyOfRange(args, 1, args.length);

                    SmartHomeServer server = new SmartHomeServer(serverId, iceArgs);
                    server.run();
                }
            } catch (NumberFormatException e) {
                LOGGER.error("Invalid serverId format");
            }
        }
    }
}
