package chat.main;

import chat.common.instance.ChatInstanceType;
import org.apache.logging.log4j.Level;

public class Configuration {
    private final ChatInstanceType chatInstanceType;
    private final int portNumber;
    private final Level logLevel;
    private final String nick;

    public Configuration(ChatInstanceType chatInstanceType, int portNumber, Level logLevel, String nick) {
        this.chatInstanceType = chatInstanceType;
        this.portNumber = portNumber;
        this.logLevel = logLevel;
        this.nick = nick;
    }

    public ChatInstanceType getChatInstanceType() {
        return chatInstanceType;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public String getNick() {
        return nick;
    }
}
