package chat.client;

public class ClientShutdownHook implements Runnable {
    private final Client client;

    public ClientShutdownHook(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        client.quitSequence();
    }
}
