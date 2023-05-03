package dynamiccalls;

import dynamiccalls.server.DynamicCallsServer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

public class Main {
    public static void main(String[] args) {
        Configurator.initialize(new DefaultConfiguration());
        Configurator.setRootLevel(Level.INFO);

        DynamicCallsServer server = new DynamicCallsServer();
        server.start();
    }
}