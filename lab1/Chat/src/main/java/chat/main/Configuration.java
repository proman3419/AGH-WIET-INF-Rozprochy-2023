package chat.main;

import chat.common.instance.ChatInstanceType;

public class Configuration {
    private final ChatInstanceType chatInstanceType;
    private final int portNumber;
    private final String nick;

    public Configuration(ChatInstanceType chatInstanceType, int portNumber, String nick) {
        this.chatInstanceType = chatInstanceType;
        this.portNumber = portNumber;
        this.nick = nick;
    }

    public ChatInstanceType getChatInstanceType() {
        return chatInstanceType;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public String getNick() {
        return nick;
    }
}
