package chat.common.message;

import java.util.Arrays;

public class MessageBuilder {
    private Message message;

    private MessageBuilder(Message message) {
        this.message = message;
    }

    public static MessageBuilder getInstance() {
        return new MessageBuilder(new Message());
    }

    public static MessageBuilder modifyMessage(Message message) {
        return new MessageBuilder(message);
    }

    public MessageBuilder setMessageType(MessageType messageType) {
        message.setMessageType(messageType);
        return this;
    }

    public MessageBuilder setArguments(Object... arguments) {
        message.setArguments(Arrays.stream(arguments)
                .map(Object::toString)
                .toArray(String[]::new));
        return this;
    }

    public MessageBuilder setText(String text) {
        message.setText(text);
        return this;
    }

    public Message build() {
        return message;
    }
}
