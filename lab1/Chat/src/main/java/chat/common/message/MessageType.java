package chat.common.message;

public enum MessageType {
    INIT(1),
    INIT_ACK(1),
    INIT_NACK(0),
    HELLO(0),
    TEXT_TCP(0),
    TEXT_TCP_RESP(1),
    TEXT_UDP(1),
    TEXT_UDP_RESP(1),
    QUIT(0),
    UNKN(0);

    private final int argumentsCount;

    MessageType(int argumentsCount) {
        this.argumentsCount = argumentsCount;
    }

    public static MessageType fromString(String string) {
        MessageType result = null;
        try {
            result = MessageType.valueOf(string);
        } catch (Exception e) {
            result = UNKN;
        }
        return result;
    }

    public int getArgumentsCount() {
        return argumentsCount;
    }
}
