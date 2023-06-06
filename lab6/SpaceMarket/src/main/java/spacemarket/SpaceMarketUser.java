package spacemarket;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class SpaceMarketUser {
    private static final String HOST = "localhost";
    protected static final String SERVICE_REQUEST_ID_SEPARATOR = "@";
    protected static final String QUEUE_NAME = "service-queue";
    protected static final String EXCHANGE_NAME = "service-direct-exchange";
    protected Connection connection;
    protected Channel channel;

    public abstract void start();

    protected void connect(Service[] services) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            for (Service service : services) {
                channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, service.toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    protected void end() {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
