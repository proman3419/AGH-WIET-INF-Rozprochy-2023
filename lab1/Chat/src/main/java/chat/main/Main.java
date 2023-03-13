package chat.main;

import chat.client.Client;
import chat.common.instance.ChatInstance;
import chat.common.instance.ChatInstanceType;
import chat.server.Server;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

public class Main {
    public static void main(String[] args) {
        Configurator.initialize(new DefaultConfiguration());
        Configurator.setRootLevel(Level.ERROR);

        ArgumentsParser argumentsParser = new ArgumentsParser();
        Configuration configuration = argumentsParser.parse(args);
        if (configuration == null) {
            System.exit(1);
        }

        Configurator.setRootLevel(configuration.getLogLevel());
        ChatInstance chatInstance = configuration.getChatInstanceType() == ChatInstanceType.CLIENT ?
                new Client(configuration.getPortNumber(), configuration.getNick()) :
                new Server(configuration.getPortNumber());
        chatInstance.start();
    }
}
