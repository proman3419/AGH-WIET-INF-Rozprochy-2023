package chat.client.handler.writer;

import chat.client.Client;
import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

import static chat.client.Client.MULTICAST_PORT;
import static chat.common.Constants.SERVER_HOSTNAME;

public class ClientWriteHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientWriteHandler.class);
    private static final String SEPARATOR = "`";
    private static final String ASCII_ART = "                            _\n" +
            "                          .' `'.__\n" +
            "                         /      \\ `'\"-,\n" +
            "        .-''''--...__..-/ .     |      \\\n" +
            "      .'               ; :'     '.  a   |\n" +
            "     /                 | :.       \\     =\\\n" +
            "    ;                   \\':.      /  ,-.__;.-;`\n" +
            "   /|     .              '--._   /-.7`._..-;`\n" +
            "  ; |       '                |`-'      \\  =|\n" +
            "  |/\\        .   -' /     /  ;         |  =/\n" +
            "  (( ;.       ,_  .:|     | /     /\\   | =|\n" +
            "   ) / `\\     | `\"\"`;     / |    | /   / =/\n" +
            "     | ::|    |      \\    \\ \\    \\ `--' =/\n" +
            "    /  '/\\    /       )    |/     `-...-`\n" +
            "   /    | |  `\\    /-'    /;\n" +
            "   \\  ,,/ |    \\   D    .'  \\\n" +
            "jgs `\"\"`   \\  nnh  D_.-'L__nnh";
    // from https://www.asciiart.eu/animals/elephants
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
                            client.getOut().writeMessage(MessageBuilder.getInstance()
                                    .setMessageType(MessageType.TEXT_TCP)
                                    .setText(input[1])
                                    .build()
                            );
                            break;
                        case 'U':
                            client.getUdpWriteReader().writeMessage(MessageBuilder.getInstance()
                                            .setMessageType(MessageType.TEXT_UDP)
                                            .setArguments(client.getNick())
                                            .setText(ASCII_ART)
                                            .build(),
                                    client.getInetAddressByName(SERVER_HOSTNAME),
                                    client.getTcpSocket().getPort()
                            );
                            break;
                        case 'M':
                            client.getMulticastWriteReader().writeMessage(MessageBuilder.getInstance()
                                            .setMessageType(MessageType.TEXT_UDP_RESP)
                                            .setArguments(client.getNick())
                                            .setText(ASCII_ART)
                                            .build(),
                                    client.getMulticastGroup(),
                                    MULTICAST_PORT
                            );
                            break;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    LOGGER.info("Incomplete/incorrect command");
                }
            }
        }
    }
}
