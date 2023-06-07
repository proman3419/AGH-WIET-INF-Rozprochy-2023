package spacemarket.common;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class SpaceMarketUser {
    private static final String HOST = "localhost";
    protected static final String MESSAGE_SEPARATOR = "@";
    protected static final String CHARSET_NAME = "UTF-8";
    protected static final String SERVICE_REQUEST_QUEUE_NAME_PREFIX = "service-request-queue-";
    protected static final String SERVICE_REQUEST_EXCHANGE_NAME = "service-request-exchange";
    protected static final String SERVICE_RESPONSE_QUEUE_NAME_PREFIX = "service-response-queue-";
    protected static final String SERVICE_RESPONSE_EXCHANGE_NAME = "service-response-exchange";
    protected Connection connection;
    protected Channel channel;

    public abstract void start();
    protected abstract Consumer createConsumer();

    protected void connect(Service[] services) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(SERVICE_REQUEST_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.exchangeDeclare(SERVICE_RESPONSE_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
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

    protected String getServiceRequestQueueName(String serviceName) {
        return SERVICE_REQUEST_QUEUE_NAME_PREFIX + serviceName;
    }

    protected String getResponseQueueName(String agencyName) {
        return SERVICE_RESPONSE_QUEUE_NAME_PREFIX + agencyName;
    }
}
