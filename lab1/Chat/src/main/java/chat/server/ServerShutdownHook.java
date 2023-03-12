package chat.server;

public class ServerShutdownHook implements Runnable {
    private final Server server;

    public ServerShutdownHook(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        server.quitSequence();
    }
}
