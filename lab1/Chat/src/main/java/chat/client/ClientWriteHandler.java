package chat.client;

import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class ClientWriteHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientWriteHandler.class);
    private static final String SEPARATOR = "`";
    private final Client client;

    public ClientWriteHandler(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (!client.getQuit().get()) {
            System.out.print("[" + client.getNick() + "]: ");
            if (scanner.hasNextLine()) {
                String[] input = scanner.nextLine().split(SEPARATOR, 2);
                char command = input[0].isEmpty() ? ' ' : input[0].charAt(0);
                try {
                    switch (command) {
                        case 'Q':
                            client.getQuit().set(true);
                            break;
                        case 'T':
                            client.getOut().sendMessage(MessageBuilder.getInstance()
                                    .setMessageType(MessageType.TEXT)
                                    .setText(input[1])
                                    .build());
                            break;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    LOGGER.info("Incomplete/incorrect command");
                }
            }
        }
    }
}
