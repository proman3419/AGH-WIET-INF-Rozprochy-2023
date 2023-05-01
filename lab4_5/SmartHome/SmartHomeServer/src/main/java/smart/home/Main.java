package smart.home;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import smart.home.server.SmartHomeServer;

public class Main {
    public static void main(String[] args) {
        Configurator.initialize(new DefaultConfiguration());
        Configurator.setRootLevel(Level.INFO);

        SmartHomeServer server = new SmartHomeServer(args);
        server.run();
    }
}