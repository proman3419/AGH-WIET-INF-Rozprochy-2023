package chat.main;

import chat.common.instance.ChatInstanceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentsParser {
    private static final Logger LOGGER = LogManager.getLogger(ArgumentsParser.class);

    public Configuration parse(String[] args) {
        Configuration configuration = null;
        try {
            ChatInstanceType chatInstanceType = ChatInstanceType.fromChar(args[0].charAt(0));
            int portNumber = Integer.parseInt(args[1]);
            String nick = null;
            if (chatInstanceType == ChatInstanceType.CLIENT) {
                nick = args[2];
            }
            configuration = new Configuration(chatInstanceType, portNumber, nick);
        } catch (Exception e) {
            LOGGER.error("Failed to parse the passed arguments, error message: " + e.getMessage());
        }
        return configuration;
    }
}
