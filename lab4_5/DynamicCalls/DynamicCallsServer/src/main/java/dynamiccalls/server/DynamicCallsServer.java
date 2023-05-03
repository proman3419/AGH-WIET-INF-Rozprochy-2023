package dynamiccalls.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class DynamicCallsServer {
    private static final Logger LOGGER = LogManager.getLogger(DynamicCallsServer.class);
    private static final int PORT = 13172;

    public void start() {
        try {
            io.grpc.Server server = io.grpc.ServerBuilder.forPort(PORT)
                    .addService(new ExecutionServiceImpl())
                    .build();
            server.start();
            LOGGER.info("Started on port {}", PORT);
            server.awaitTermination();
        } catch (IOException e) {
            LOGGER.error("Failed to start on port {}, error message: '{}'", PORT, e.getMessage());
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException has been caught, error message: '{}'", e.getMessage());
        } finally {
            LOGGER.info("Shutting down");
        }
    }
}
