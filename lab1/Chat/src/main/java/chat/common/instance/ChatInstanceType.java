package chat.common.instance;

public enum ChatInstanceType {
    CLIENT,
    SERVER;

    public static ChatInstanceType fromChar(char ch) {
        ChatInstanceType chatInstanceType = null;
        if (ch == 'c') {
            chatInstanceType = CLIENT;
        } else if (ch == 's') {
            chatInstanceType = SERVER;
        }
        return chatInstanceType;
    }
}
