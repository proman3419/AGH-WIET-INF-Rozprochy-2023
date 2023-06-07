package spacemarket.agency;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spacemarket.common.Service;
import spacemarket.common.SpaceMarketUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Agency extends SpaceMarketUser {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String name;
    private int serviceRequestNumber = 0;

    public Agency(String name) {
        this.name = name;
    }

    private String nextServiceRequestId() {
        return String.format("%s%s%d", name, SpaceMarketUser.MESSAGE_SEPARATOR, serviceRequestNumber++);
    }

    @Override
    protected void connect(Service[] services) {
        super.connect(services);
        try {
            channel.queueDeclare(getResponseQueueName(name), true, false, false, null);
            channel.queueBind(getResponseQueueName(name), SERVICE_RESPONSE_EXCHANGE_NAME, name);
            channel.basicConsume(getResponseQueueName(name), false, createConsumer());
        } catch (IOException e) {
            LOGGER.error("Failed to connect, details: {}", e.getMessage());
        }
    }

    private void requestService(Service service) {
        try {
            String requestId = nextServiceRequestId();
            LOGGER.info("Service request id: '{}'", requestId);
            channel.basicPublish(SERVICE_REQUEST_EXCHANGE_NAME, service.toString(), null, requestId.getBytes());
        } catch (IOException e) {
            LOGGER.error("Failed to request a service, details: {}", e.getMessage());
        }
    }

    @Override
    protected void enterInputLoop() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String input = br.readLine();
                if (input.equals("quit")) {
                    end();
                    break;
                }
                if (input.equals("help")) {
                    LOGGER.info("Available services:");
                    for (Service service : Service.values()) {
                        LOGGER.info("> {}", service);
                    }
                    continue;
                }
                Service service = Service.fromString(input);
                if (service != null) {
                    requestService(service);
                }
            } catch (IOException ignored) {
                // ignored
            }
        }
    }

    @Override
    protected Consumer createConsumer() {
        return new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, CHARSET_NAME);
                LOGGER.info("Completed service with id '{}'", message);
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
    }

    @Override
    public void start() {
        LOGGER.info("Starting agency '{}'", name);
        connect(Service.values());
        enterInputLoop();
    }
}
