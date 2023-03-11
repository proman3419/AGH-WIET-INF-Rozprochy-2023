package chat.common;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class ClientServerUtils {
    public static void closeSocket(Socket socket, Logger logger) {
        try {
            int localPort = socket.getLocalPort();
            int port = socket.getPort();
            socket.close();
            logger.info("Closed socket (localPort: {}, port: {})", localPort, port);
        } catch (IOException e) {
            logger.error("Failed to close the socket, error message: '{}'", e.getMessage());
        }
    }
}
