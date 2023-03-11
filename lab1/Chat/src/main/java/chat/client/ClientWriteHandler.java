package chat.client;

import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import chat.common.wrapper.PrintWriterWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class ClientWriteHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientWriteHandler.class);
    private static final String SEPARATOR = "`";
    private final Client client;
    private final Scanner scanner;
    private final PrintWriterWrapper out;
    private final String nick;

    public ClientWriteHandler(Client client, Scanner scanner, PrintWriterWrapper out, String nick) {
        this.client = client;
        this.scanner = scanner;
        this.out = out;
        this.nick = nick;
    }

    @Override
    public void run() {
        while (!client.getQuit().get()) {
            System.out.print("[" + nick + "]: ");
            if (scanner.hasNextLine()) {
                String[] input = scanner.nextLine().split(SEPARATOR, 2);
                char command = input[0].isEmpty() ? ' ' : input[0].charAt(0);
                try {
                    switch (command) {
                        case 'Q':
                            client.getQuit().set(true);
                            break;
                        case 'T':
                            out.sendMessage(MessageBuilder.getInstance()
                                    .setMessageType(MessageType.TEXT)
                                    .setText(input[1])
                                    .build());
                            break;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Incomplete command");
                }
            }
        }
    }
}
