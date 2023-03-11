package chat.common.message;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Message {
    private static final Logger LOGGER = LogManager.getLogger(Message.class);
    private static final String SEPARATOR = "`";
    private MessageType messageType = MessageType.UNKN;
    private String[] arguments = new String[]{};
    private String text = "";

    public Message(String messageRaw) {
        try {
            String[] messageSplit = messageRaw.split(SEPARATOR, 2);
            messageType = MessageType.fromString(messageSplit[0]);
            int nonTextPartsCount = messageType.getArgumentsCount() + 1; // +1 for messageType
            messageSplit = messageRaw.split(SEPARATOR, nonTextPartsCount + 1); // +1 for possible text
            if (messageType.getArgumentsCount() != 0) {
                arguments = Arrays.copyOfRange(messageSplit, 1, messageType.getArgumentsCount() + 1); // Omit messageType, message
            }
            if (messageSplit.length > nonTextPartsCount) {
                text = messageSplit[messageSplit.length - 1];
            }
        } catch (Exception e) {
            LOGGER.warn("Error while parsing the message '{}', error message: '{}'", messageRaw, e.getMessage());
        }
    }

    Message() {
        // Constructor for builder
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(messageType.name());
        for (String argument : arguments) {
            stringBuilder.append(SEPARATOR);
            stringBuilder.append(argument);
        }
        stringBuilder.append(SEPARATOR);
        stringBuilder.append(text);
        return stringBuilder.toString();
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String[] getArguments() {
        return arguments;
    }

    public String getText() {
        return text;
    }

    void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    void setText(String text) {
        this.text = text;
    }

    public boolean checkMessageType(MessageType messageType) {
        return this.messageType == messageType;
    }
}
