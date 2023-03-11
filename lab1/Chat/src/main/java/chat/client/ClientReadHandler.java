package chat.client;

import chat.common.message.Message;
import chat.common.wrapper.BufferedReaderWrapper;

public class ClientReadHandler implements Runnable {
    private final Client client;
    private final BufferedReaderWrapper in;
    private final String nick;

    public ClientReadHandler(Client client, BufferedReaderWrapper in, String nick) {
        this.client = client;
        this.in = in;
        this.nick = nick;
    }

    @Override
    public void run() {
        while (!client.getQuit().get()) {
            Message message = in.readMessage();
            if (message != null) {
                switch (message.getMessageType()) {
                    case QUIT:
                        client.getQuit().set(true);
                        System.out.println("Server disconnected, press ENTER to exit");
                        return;
                    case TEXT_RESP:
                        printUserMessage(message, "ALL");
                        break;
                }
            }
        }
    }

    private void printUserMessage(Message message, String receiver) {
        System.out.println();
        System.out.println("### From: " + message.getArguments()[0]);
        System.out.println("### To: " + receiver);
        System.out.println("### Message: " + message.getText());
    }
}
