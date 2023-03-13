package chat.main;

import chat.common.instance.ChatInstanceType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentsParser {
    private static final Logger LOGGER = LogManager.getLogger(ArgumentsParser.class);

    public Configuration parse(String[] args) {
        Configuration configuration = null;
        try {
            ChatInstanceType chatInstanceType = ChatInstanceType.fromChar(args[0].charAt(0));
            int portNumber = Integer.parseInt(args[1]);
            Level logLevel = parseLogLevel(args[2]);
            String nick = null;
            if (chatInstanceType == ChatInstanceType.CLIENT) {
                nick = args[3];
            }
            configuration = new Configuration(chatInstanceType, portNumber, logLevel, nick);
        } catch (Exception e) {
            LOGGER.error("Failed to parse the passed arguments, error message: {}", e.getMessage());
        }
        return configuration;
    }

    private Level parseLogLevel(String logLevelRaw) {
        switch (logLevelRaw.charAt(0)) {
            case 'o':
                return Level.OFF;
            case 'e':
                return Level.ERROR;
            case 'w':
                return Level.WARN;
            case 'i':
                return Level.INFO;
            case 'd':
                return Level.DEBUG;
            default:
                return Level.ERROR;
        }
    }
}
