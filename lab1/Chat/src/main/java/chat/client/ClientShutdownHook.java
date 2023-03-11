package chat.client;

public class ClientShutdownHook implements Runnable {
    private final Client client;

    public ClientShutdownHook(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
//        client.getOut().sendMessage(MessageBuilder.getInstance()
//                .setMessageType(MessageType.QUIT)
//                .build());
//        try {
//            client.getServerSocket().close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        client.getQuit().set(true);
        client.quitSequence();
    }
}
